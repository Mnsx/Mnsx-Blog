package top.mnsx.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateViewCount() {
        // 查询redis中的浏览量
        Map<String, Integer> viewCount = redisCache.getCacheMap(RedisCacheConstants.ARTICLE_VIEW_COUNT);
        List<Article> articles = viewCount.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());
        // 更新到数据库中
        articleService.updateBatchById(articles);
    }
}
