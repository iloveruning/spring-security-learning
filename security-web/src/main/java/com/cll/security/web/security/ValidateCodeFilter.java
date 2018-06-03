package com.cll.security.web.security;


import com.cll.security.web.common.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author chenliangliang
 * @date 2018/6/3
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {


    private AuthenticationFailureHandler failureHandler;

    public ValidateCodeFilter(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        if (StringUtils.equals("/user/login", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {

            System.out.println("validate code start...");
            try {
                validate(httpServletRequest);
            } catch (ValidateCodeException e) {
                failureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validate(HttpServletRequest request) throws ValidateCodeException {
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)) {
            throw new ValidateCodeException("验证码不存在");
        }
        HttpSession session = request.getSession();
        String vCode = (String) session.getAttribute("v_code");
        if (vCode == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        String[] strs = StringUtils.split(vCode, "#");
        long expired = Long.parseLong(strs[1]);
        if (System.currentTimeMillis() > expired) {
            session.removeAttribute("v_code");
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(code, strs[0])) {
            throw new ValidateCodeException("验证码错误");
        }
        session.removeAttribute("v_code");
    }
}
