package top.mnsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Category;
import top.mnsx.domain.vo.CategoryVo;
import top.mnsx.service.CategoryService;
import top.mnsx.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import top.mnsx.utils.BeanCopyUtils;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【blog_category(分类表)】的数据库操作Service实现
* @createDate 2022-11-23 15:32:36
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表 状态为已发布的文章，对应的去重id，返回对应分类表中的id和名称
        List<Category> categories = categoryMapper.getCategoryListWithActive();

        // 封装Vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}




