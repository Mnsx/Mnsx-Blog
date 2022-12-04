package top.mnsx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.mnsx.constants.RedisCacheConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.LoginUser;
import top.mnsx.domain.entity.User;
import top.mnsx.domain.vo.*;
import top.mnsx.service.BlogLoginService;
import top.mnsx.service.LoginService;
import top.mnsx.service.MenuService;
import top.mnsx.service.RoleService;
import top.mnsx.utils.BeanCopyUtil;
import top.mnsx.utils.JwtUtil;
import top.mnsx.utils.RedisCache;
import top.mnsx.utils.SecurityUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(userId));

        // 将用户信息存储redis
        redisCache.setCacheObject(RedisCacheConstants.ADMIN_LOGIN_CACHE_KEY + userId, loginUser);

        // 将token和userInfo封装返回
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtil.getLoginUser();

        // 通过用户id获取权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());

        // 通过用户id获取角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        // 获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);

        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @Override
    public ResponseResult<RouterVo> getRouters() {
        // 获取用户信息
        Long userId = SecurityUtil.getUserId();
        // 查询menu 结果是tree的
        List<MenuVo> menuVos = menuService.selectRouterMenuTreeByUserId(userId);

        // 封装数据
        RouterVo routerVo = new RouterVo(menuVos);
        return ResponseResult.okResult(routerVo);
    }

    @Override
    public ResponseResult logout() {
        // 获取当前登录的用户id
        Long userId = SecurityUtil.getUserId();
        // 删除redis中对应的值
        redisCache.deleteObject(RedisCacheConstants.ADMIN_LOGIN_CACHE_KEY + userId);

        return ResponseResult.okResult();
    }
}
