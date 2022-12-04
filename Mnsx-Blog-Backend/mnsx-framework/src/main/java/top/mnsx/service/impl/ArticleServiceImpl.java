package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.constants.RedisCacheConstants;
import top.mnsx.constants.SystemConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.AddArticleDto;
import top.mnsx.domain.dto.ArticleDto;
import top.mnsx.domain.entity.Article;
import top.mnsx.domain.entity.ArticleTag;
import top.mnsx.domain.entity.Category;
import top.mnsx.domain.vo.ArticleDetailVo;
import top.mnsx.domain.vo.HotArticleVo;
import top.mnsx.domain.vo.PageVo;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.exception.SystemException;
import top.mnsx.mapper.ArticleMapper;
import top.mnsx.service.ArticleService;
import top.mnsx.service.ArticleTagService;
import top.mnsx.service.CategoryService;
import top.mnsx.utils.BeanCopyUtil;
import top.mnsx.utils.RedisCache;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装成ResponseResult
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照流量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多只查询十条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        // bean拷贝
        List<HotArticleVo> articleVos = BeanCopyUtil.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(ArticleDto articleDto) {
        // 查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 如果有CategoryId就要查询时要和传入时相同
        Long categoryId = articleDto.getCategoryId();
        wrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态时正式发布的
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop降序排序
        wrapper.orderByDesc(Article::getIsTop);
        // 对事件排序
        wrapper.orderByDesc(Article::getUpdateTime);

        // 分页查询
        Page<Article> page = new Page<>(articleDto.getPageNum(), articleDto.getPageSize());
        page(page, wrapper);
        List<Article> articles = page.getRecords();

        List<ArticleDetailVo> articleListVos = articles.stream()
                .map(article -> {
                    // 从redis中获取viewCount
                    Integer viewCount = redisCache.getCacheMapValue(RedisCacheConstants.ARTICLE_VIEW_COUNT, article.getId().toString());
                    article.setViewCount(Long.valueOf(viewCount));
                    // 查询分类名
                    Category category = categoryService.getById(article.getCategoryId());
                    ArticleDetailVo articleListVo = BeanCopyUtil.copyBean(article, ArticleDetailVo.class);
                    articleListVo.setCategoryName(category.getName());
                    return articleListVo;
                })
                .collect(Collectors.toList());

        // 封装结果
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetails(Long id) {
        // 根据id查询文章
        Article article = getById(id);

        // 从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(RedisCacheConstants.ARTICLE_VIEW_COUNT, id.toString());
        article.setViewCount(Long.valueOf(viewCount));

        // 转换成Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtil.copyBean(article, ArticleDetailVo.class);

        // 根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }

        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(RedisCacheConstants.ARTICLE_VIEW_COUNT, id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(AddArticleDto articleDto) {
        // 添加博客
        Article article = BeanCopyUtil.copyBean(articleDto, Article.class);
        save(article);

        // 添加标签关联
        List<ArticleTag> collect = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        articleTagService.saveBatch(collect);

        // 将viewCount添加到redis种
        getResponseResult();

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary) {
        // 创建wrapper
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(title != null, Article::getTitle, title);
        wrapper.eq(summary != null, Article::getSummary, summary);
        wrapper.orderByDesc(Article::getUpdateTime);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> pageInfo = page(page, wrapper);
        pageInfo.getRecords()
                .forEach(article -> article.setContent(null));
        PageVo pageVo = new PageVo(page.getRecords(), pageInfo.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getInfoById(Integer id) {
        Article article = getById(id);
        return ResponseResult.okResult(article);
    }

    @Override
    public ResponseResult updateArticle(Article article) {
        Article articleData = getById(article.getId());
        if (articleData == null) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }

        save(article);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        Article article = getById(id);
        if (article == null) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NOT_EXIST);
        }

        removeById(article);

        return ResponseResult.okResult();
    }

    private void getResponseResult() {
        List<Article> articles = list();
        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(RedisCacheConstants.ARTICLE_VIEW_COUNT, collect);
    }
}
