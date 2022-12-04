package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.mnsx.constants.SystemConstants;
import top.mnsx.domain.entity.LoginUser;
import top.mnsx.domain.entity.User;
import top.mnsx.mapper.MenuMapper;
import top.mnsx.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

        // 判断是否查到用户
        if (Objects.isNull(user)) {
            // 未查到，抛出异常
            throw new RuntimeException("用户不存在");
        }

        // 查到，作为方法的返回值返回用户
        // TODO: 2022/11/25 查询权限信息封装
        if (user.getType().equals(SystemConstants.ADMIN)) {
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, perms);
        }

        return new LoginUser(user, null);
    }
}
