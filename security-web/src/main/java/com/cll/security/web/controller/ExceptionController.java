package com.cll.security.web.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author chenliangliang
 * @date 2018/6/3
 */

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(AuthenticationException.class)
    public String authenticationFail(Model model,AuthenticationException e){
        model.addAttribute("msg",e.getMessage());
        return "error";
    }
}
