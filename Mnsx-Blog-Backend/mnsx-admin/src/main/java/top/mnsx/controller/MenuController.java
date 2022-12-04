package top.mnsx.controller;

import io.swagger.models.RefResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Menu;
import top.mnsx.service.MenuService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult list(String status, String menuName) {
        return menuService.listAll(status, menuName);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        return menuService.add(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getInfoById(@PathVariable("id")Long id) {
        return menuService.getInfoById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long id) {
        return menuService.deleteMenu(id);
    }
}
