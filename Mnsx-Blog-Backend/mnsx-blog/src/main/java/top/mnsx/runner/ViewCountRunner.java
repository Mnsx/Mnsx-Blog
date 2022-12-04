package top.mnsx.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import top.mnsx.constants.RedisCacheConstants;
import top.mnsx.domain.entity.Article;
import top.mnsx.service.ArticleService;
import top.mnsx.utils.RedisCache;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleService.list();
        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(RedisCacheConstants.ARTICLE_VIEW_COUNT, collect);
    }
}
