package top.mnsx.handler.security;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.mnsx.domain.ResponseResult;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.utils.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        // 响应给前端
        ResponseResult responseResult = null;
        if (authException instanceof InsufficientAuthenticationException) {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else if (authException instanceof BadCredentialsException) {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), authException.getMessage());
        } else {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "认证或授权失败");
        }
        WebUtil.renderString(response, JSON.toJSONString(responseResult));
    }
}
