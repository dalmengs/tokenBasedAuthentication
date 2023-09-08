package com.baseproject.tokenbasedauthentication.Controller;

import com.baseproject.tokenbasedauthentication.BaseResponse;
import com.baseproject.tokenbasedauthentication.Exception.BaseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExceptionController {
    @GetMapping("/exception/entrypoint")
    public BaseResponse entryPoint() throws Exception{
        return BaseResponse.FAILED(404, "User Not Found");
    }

    @GetMapping("/exception/access")
    public BaseResponse denied() throws Exception{
        return BaseResponse.FAILED(405, "Access Denied");
    }

}
