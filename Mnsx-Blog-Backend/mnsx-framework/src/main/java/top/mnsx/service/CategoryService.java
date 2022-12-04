package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Mnsx_x
* @description 针对表【blog_category(分类表)】的数据库操作Service
* @createDate 2022-12-03 10:31:07
*/
public interface CategoryService extends IService<Category> {

    ResponseResult listAllCategory();
}
