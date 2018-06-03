package com.cll.security.web.controller;

import com.cll.security.web.vcode.Captcha;
import com.cll.security.web.vcode.GifCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenliangliang
 * @date 2018/6/3
 */
@Controller
@RequestMapping("auth")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(AuthController.class);


    @RequestMapping(value = "/fail", method = {RequestMethod.GET, RequestMethod.POST})
    public String fail(@RequestAttribute("msg") String msg, Model model) {
        model.addAttribute("msg", msg);
        return "error";
    }

    @GetMapping("/vcode")
    public void vCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            //response.setContentType("image/jpeg");


            /**
             * gif格式动画验证码
             * 宽，高，位数。
             */
            Captcha captcha = new GifCaptcha(146, 33, 4);
            //输出
            captcha.out(response.getOutputStream());
            //存入Session
            long expired = System.currentTimeMillis() + 60000;
            //session.removeAttribute("v_code");
            request.getSession(true).setAttribute("v_code", captcha.text() + "#" + expired);
            //System.out.println(captcha.text() + "#" + expired);

        } catch (Exception e) {
            log.error("获取验证码异常", e);
        }
    }
}
