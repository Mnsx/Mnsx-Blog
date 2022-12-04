package top.mnsx.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.mnsx.constants.RedisCacheConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.LoginUser;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.utils.JwtUtil;
import top.mnsx.utils.RedisCache;
import top.mnsx.utils.WebUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获取请求头中的token
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {
            // 说明该接口不需要登录直接放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token，获取userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 超时
            // 非法token
            // 响应前端需要重新登录
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtil.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        String userId = claims.getSubject();

        // 从redis中获取用户信息
        LoginUser user = redisCache.getCacheObject(RedisCacheConstants.ADMIN_LOGIN_CACHE_KEY + userId);
        if (Objects.isNull(user)) {
            // 说明登录过期
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtil.renderString(response, JSON.toJSONString(responseResult));
            return;
        }


        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
