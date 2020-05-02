package com.bz.blueauth.view;

import java.util.Map;

import com.bz.blueauth.controller.AuthCont;
import com.bz.blueauth.dto.ResponseDTO;
import com.bz.blueauth.dto.pojo.UserPO;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin
@RestController
public class AuthRest {

    private AuthCont authCont;
    
    public AuthRest() {
        try {
            this.authCont = new AuthCont();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }
  
    @PostMapping("/Auth/login")
    //@ExceptionHandler(value = CustomExceptionHandler.class)
    public ResponseDTO<Map<String,?>> authenticate(@RequestBody UserPO userPo) {
       	return this.authCont.authenticate(userPo);
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/Auth/register")
    public Map register(@RequestBody UserPO userPo) {
       	return this.authCont.register(userPo);
    }
    
    //@CrossOrigin(origins = "*")
    @GetMapping("/Auth/isAuthorize")
    public Map isAuthorize(@RequestHeader(value = "Authorization") String authToken) {
       	return this.authCont.isAuthorized(authToken.replaceFirst("Bearer\\s+", ""));
    }
    
    //@CrossOrigin(origins = "*")
    @GetMapping("/Auth/refresh")
    public ResponseDTO<String> refresh(@RequestHeader(value = "Authorization") String authToken) {
       	return this.authCont.refresh(authToken.replaceFirst("Bearer\\s+", ""));
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/Auth/smCallbackRegister")
    public String smCallbackRegister(@RequestBody Map data) {
       	return "We Are Back from Social Media. ready for register " + data.toString();
    }

   // @CrossOrigin(origins = "*")
    @PostMapping("/Auth/smCallbackLogin")
    public String smCallbackLogin(@RequestBody Map data) {
       	return "We Are Back from Social Media. ready for login " + data.toString();
    }
}