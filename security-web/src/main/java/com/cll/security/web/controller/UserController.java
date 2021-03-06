package com.cll.security.web.controller;

import com.cll.security.web.common.annotation.SysLog;
import com.cll.security.web.entity.User;
import com.cll.security.web.repository.UserRepository;
import com.cll.security.web.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/6/2
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public R register(@RequestParam("username")String username,
                      @RequestParam("password") String password){

        User user=new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setSalt("Spring Security");
        user.setState(0);

        User res = userRepository.save(user);
        return R.success(res);

    }

    @GetMapping("/list")
    @SysLog(appid = "#{T(com.cll.security.web.common.Constant).getAppId('userList')}",sevid = "#{#sevid+'_'+#$2}",data = "#{#returnObject.data}")
    public R list(@RequestParam("sevid") String sevid,
                  @RequestParam("test") String test){
        return R.success(userRepository.findAll());
    }



}
