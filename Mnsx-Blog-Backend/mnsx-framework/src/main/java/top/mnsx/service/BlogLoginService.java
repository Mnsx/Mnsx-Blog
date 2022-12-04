package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.User;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
