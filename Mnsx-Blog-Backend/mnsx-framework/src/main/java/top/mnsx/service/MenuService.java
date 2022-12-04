package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import top.mnsx.domain.vo.MenuVo;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-12-01 11:51:31
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult listAll(String status, String menuName);

    ResponseResult add(Menu menu);

    ResponseResult getInfoById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);
}
