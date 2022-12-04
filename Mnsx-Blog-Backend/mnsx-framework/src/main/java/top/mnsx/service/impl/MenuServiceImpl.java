package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.constants.SystemConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Menu;
import top.mnsx.domain.vo.MenuVo;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.exception.SystemException;
import top.mnsx.service.MenuService;
import top.mnsx.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import top.mnsx.utils.BeanCopyUtil;
import top.mnsx.utils.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Mnsx_x
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2022-12-01 11:51:31
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员返回所有的权限
        if (SecurityUtil.isAdmin()) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE, SystemConstants.BUTTON_TYPE);
            wrapper.eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL);
            List<Menu> list = list(wrapper);
            List<String> perms = list.stream().map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        // 否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 判断是否为管理员，返回所有符合要求的Menu
        if (SecurityUtil.isAdmin()) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 返回用户具有的menu
            menus =  menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建tree返回
        return buildMenuTree(menus, 0L);
    }

    @Override
    public ResponseResult listAll(String status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, Menu::getStatus, status);
        wrapper.eq(menuName != null, Menu::getMenuName, menuName);

        List<Menu> list = list(wrapper);

        List<MenuVo> menuVos = BeanCopyUtil.copyBeanList(list, MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult add(Menu menu) {
        save(menu);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        Menu byId = getById(id);

        MenuVo menuVo = BeanCopyUtil.copyBean(byId, MenuVo.class);

        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        Menu byId = getById(menu.getId());
        if (byId == null) {
            throw new SystemException(AppHttpCodeEnum.MENU_NOT_EXIST);
        }

        updateById(menu);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        Menu byId = getById(id);
        if (byId == null) {
            throw new SystemException(AppHttpCodeEnum.MENU_NOT_EXIST);
        }

        removeById(byId);

        return ResponseResult.okResult();
    }

    private List<MenuVo> buildMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    MenuVo menuVo = BeanCopyUtil.copyBean(menu, MenuVo.class);
                    menuVo.setChildren(getChildren(menu, menus));
                    return menuVo;
                })
                .collect(Collectors.toList());
    }

    private List<MenuVo> getChildren(Menu menu, List<Menu> menus) {
        return menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> {
                    MenuVo menuVo = BeanCopyUtil.copyBean(m, MenuVo.class);
                    menuVo.setChildren(getChildren(m, menus));
                    return menuVo;
                })
                .collect(Collectors.toList());
    }
}




