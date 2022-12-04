package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.User;
import top.mnsx.domain.vo.AdminUserInfoVo;
import top.mnsx.domain.vo.RouterVo;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult<AdminUserInfoVo> getInfo();

    ResponseResult<RouterVo> getRouters();

    ResponseResult logout();
}
