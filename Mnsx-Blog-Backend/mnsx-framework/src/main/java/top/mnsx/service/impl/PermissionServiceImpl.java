package top.mnsx.service.impl;

import org.springframework.stereotype.Service;
import top.mnsx.utils.SecurityUtil;

import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service("permissionService")
public class PermissionServiceImpl {
    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return 返回是否具有权限
     */
    public boolean hasPermissions(String permission) {
        // 如果是超级管理员 直接返回true
        if (SecurityUtil.isAdmin()) {
            return true;
        }

        // 否则获取当前登录用户所具有的权限列表，然后判断是否存在
        List<String> perms = SecurityUtil.getLoginUser().getPerms();
        return perms.contains(permission);
    }
}
