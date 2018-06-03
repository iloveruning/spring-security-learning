package com.cll.security.web.common;


import org.springframework.security.core.AuthenticationException;

/**
 * @author chenliangliang
 * @date 2018/6/3
 */
public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg){
        super(msg);
    }
}
