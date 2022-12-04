package top.mnsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.domain.entity.ArticleTag;
import top.mnsx.service.ArticleTagService;
import top.mnsx.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author Mnsx_x
* @description 针对表【blog_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2022-12-03 10:52:36
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




