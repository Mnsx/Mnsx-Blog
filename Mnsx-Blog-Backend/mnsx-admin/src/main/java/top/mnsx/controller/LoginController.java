package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.mnsx.annotation.SystemLog;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.LoginUser;
import top.mnsx.domain.entity.User;
import top.mnsx.domain.vo.AdminUserInfoVo;
import top.mnsx.domain.vo.RouterVo;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.exception.SystemException;
import top.mnsx.service.LoginService;
import top.mnsx.utils.SecurityUtil;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @SystemLog
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        return loginService.getInfo();
    }

    @GetMapping("/getRouters")
    public ResponseResult<RouterVo> getRouters() {
        return loginService.getRouters();
    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }
}
