package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.constants.SystemConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.ArticleDto;
import top.mnsx.domain.entity.Article;
import top.mnsx.domain.entity.Category;
import top.mnsx.domain.vo.ArticleDetailVo;
import top.mnsx.domain.vo.HotArticleVo;
import top.mnsx.domain.vo.PageVo;
import top.mnsx.mapper.ArticleMapper;
import top.mnsx.service.ArticleService;
import top.mnsx.service.CategoryService;
import top.mnsx.utils.BeanCopyUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

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
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

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

        // 分页查询
        Page<Article> page = new Page<>(articleDto.getPageNum(), articleDto.getPageSize());
        page(page, wrapper);
        List<Article> articles = page.getRecords();

        // 查询分类名
        List<ArticleDetailVo> articleListVos = articles.stream()
                .map(article -> {
                    Category category = categoryService.getById(article.getCategoryId());
                    ArticleDetailVo articleListVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
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

        // 转换成Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        // 根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }

        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }
}
