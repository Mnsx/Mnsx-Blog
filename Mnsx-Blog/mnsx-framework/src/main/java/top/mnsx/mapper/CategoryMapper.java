package top.mnsx.mapper;

import top.mnsx.domain.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【blog_category(分类表)】的数据库操作Mapper
* @createDate 2022-11-23 15:32:36
* @Entity top.mnsx.domain.entity.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> getCategoryListWithActive();
}




