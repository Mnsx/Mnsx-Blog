package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.constants.SystemConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Category;
import top.mnsx.domain.vo.CategoryVo;
import top.mnsx.service.CategoryService;
import top.mnsx.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import top.mnsx.utils.BeanCopyUtil;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【blog_category(分类表)】的数据库操作Service实现
* @createDate 2022-12-03 10:31:07
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtil.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}