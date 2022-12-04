package top.mnsx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.AddArticleDto;
import top.mnsx.domain.dto.ArticleDto;
import top.mnsx.domain.entity.Article;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(ArticleDto articleDto);

    ResponseResult getArticleDetails(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto articleDto);

    ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult getInfoById(Integer id);

    ResponseResult updateArticle(Article article);

    ResponseResult deleteArticle(Long id);
}
