package top.mnsx.service.impl;

import com.alibaba.fastjson.JSON;
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
import top.mnsx.domain.vo.BlogUserLoginVo;
import top.mnsx.domain.vo.UserInfoVo;
import top.mnsx.service.BlogLoginService;
import top.mnsx.utils.BeanCopyUtil;
import top.mnsx.utils.JwtUtil;
import top.mnsx.utils.RedisCache;

import java.util.Objects;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

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
        redisCache.setCacheObject(RedisCacheConstants.BLOG_LOGIN_CACHE_KEY + userId, loginUser);

        // 将token和userInfo封装返回
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        // 获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 解析获取用户id
        Long userId = loginUser.getUser().getId();
        // 删除redis中用户信息
        redisCache.deleteObject(RedisCacheConstants.BLOG_LOGIN_CACHE_KEY + userId);
        return ResponseResult.okResult();
    }
}
