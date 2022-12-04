package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mnsx.annotation.SystemLog;
import top.mnsx.domain.ResponseResult;
import top.mnsx.service.CategoryService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @SystemLog
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList() {
        return categoryService.listAllCategory();
    }
}
