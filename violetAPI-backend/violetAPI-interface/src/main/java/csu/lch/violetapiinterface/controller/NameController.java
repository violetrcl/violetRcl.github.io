package csu.lch.violetapiinterface.controller;

import csu.lch.violetapiclientsdk.model.User;
import csu.lch.violetapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name){
        return "你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name){
        return "你的名字是" + name;
    }

    @PostMapping("/user")
    public String getNameByPost(@RequestBody User user, HttpServletRequest httpServletRequest){
        String accessKey = httpServletRequest.getHeader("accessKey");
        String sign = httpServletRequest.getHeader("sign");
        String nonce = httpServletRequest.getHeader("nonce");
        String timestamp = httpServletRequest.getHeader("timestamp");
        String body = httpServletRequest.getHeader("body");

        if (!accessKey.equals("fangyi01")){
            throw new RuntimeException("无权限调用");
        }
        // TODO: 2023/4/3 临时数可以用HashMap或Redis存储
        if (Long.parseLong(nonce) > 10000){
            throw new RuntimeException("无权限");
        }
        //临时数的时间不能超过十分钟(600s)，否则失效
        if ((System.currentTimeMillis() / 1000 - Long.parseLong(timestamp)) > 600){
            throw new RuntimeException("无权限");
        }
        // TODO: 2023/4/3 实际情况是根据accessKey从数据库中查出secretKey
        String serverSign = SignUtils.getSign(body, "asdfghjkl");
        if (!sign.equals(serverSign)){
            throw new RuntimeException("无权限");
        }
        return "用户名是" + user.getName();
    }
}
