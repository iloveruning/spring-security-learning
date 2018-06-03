package com.cll.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author chenliangliang
 * @date 2018/6/2
 */
@Controller
public class PageController {

    @GetMapping("/page/{page}")
    public String page(@PathVariable("page") String page){
        return page;
    }
}
