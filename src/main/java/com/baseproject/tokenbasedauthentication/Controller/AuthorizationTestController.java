package com.baseproject.tokenbasedauthentication.Controller;

import com.baseproject.tokenbasedauthentication.BaseResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationTestController {

    @GetMapping("/admin")
    public BaseResponse adminPage(){
        return BaseResponse.OK();
    }

    @GetMapping("/user")
    public BaseResponse userPage(){
        return BaseResponse.OK();
    }

}
