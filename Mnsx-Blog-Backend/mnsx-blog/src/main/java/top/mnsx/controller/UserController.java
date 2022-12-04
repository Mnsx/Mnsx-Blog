package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;
import top.mnsx.annotation.SystemLog;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.User;
import top.mnsx.service.UserService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @SystemLog
    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @SystemLog
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @SystemLog
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
