package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.AddArticleDto;
import top.mnsx.domain.entity.Article;
import top.mnsx.service.ArticleService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto articleDto) {
        return articleService.add(articleDto);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary) {
        return articleService.list(pageNum, pageSize, title, summary);
    }

    @GetMapping("/{id}")
    public ResponseResult getInfoById(@PathVariable("id") Integer id) {
        return articleService.getInfoById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody Article article) {
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delelteArticle(@PathVariable("id") Long id) {
        return articleService.deleteArticle(id);
    }
}
