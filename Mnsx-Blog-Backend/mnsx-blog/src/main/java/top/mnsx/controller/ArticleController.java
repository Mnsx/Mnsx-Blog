package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.annotation.SystemLog;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.ArticleDto;
import top.mnsx.domain.entity.Article;
import top.mnsx.service.ArticleService;

import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @SystemLog
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装成ResponseResult
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @SystemLog
    @GetMapping("/articleList")
    public ResponseResult articleList(ArticleDto articleDto) {
        return articleService.articleList(articleDto);
    }

    @SystemLog
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetails(id);
    }

    @SystemLog
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id) {
        return articleService.updateViewCount(id);
    }
}
