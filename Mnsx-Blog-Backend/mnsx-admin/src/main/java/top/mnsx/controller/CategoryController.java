package top.mnsx.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Category;
import top.mnsx.domain.vo.ExcelCategoryVo;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.service.CategoryService;
import top.mnsx.utils.BeanCopyUtil;
import top.mnsx.utils.WebUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@permissionService.hasPermissions('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtil.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtil.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtil.renderString(response, JSON.toJSONString(result));
        }
    }
}
