package com.vivekempire.TodoList.controllers;


import com.vivekempire.TodoList.dto.req.LoginUserReqDTO;
import com.vivekempire.TodoList.dto.req.RegisterUserReqDTO;
import com.vivekempire.TodoList.dto.resp.ErrorResponse;
import com.vivekempire.TodoList.dto.resp.MssgRespDTO;
import com.vivekempire.TodoList.exceptions.TodoCustomException;
import com.vivekempire.TodoList.services.AuthService;
import com.vivekempire.TodoList.utils.JWTHelper;
import jakarta.mail.MessagingException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobKOctets;
import java.awt.image.RescaleOp;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private JWTHelper jwtHelper;


    @PostMapping("/register_user/")
    public ResponseEntity<?> registerUser( @RequestBody RegisterUserReqDTO registerUserReqDTO) throws Exception {

        return authService.registerUser(registerUserReqDTO);
    }

    @PostMapping("/login_user/")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserReqDTO loginUserReqDTO){
        return authService.loginUser(loginUserReqDTO.getEmail(),loginUserReqDTO.getPassword());
    }

    @GetMapping("/test_authentication/")
    public String check(@RequestBody LoginUserReqDTO loginUserReqDTO,@RequestHeader("Authorization") String token){
        token=token.split(" ")[1];
        Map<String,Object> check=jwtHelper.isValid(token);
        return "OK";
    }

    @PostMapping("/google_login")
    public ResponseEntity<?> googleLogIn(@RequestParam(value = "code",required = true) String code){

        return authService.handleGoogleLogIn(code);

    }

}

