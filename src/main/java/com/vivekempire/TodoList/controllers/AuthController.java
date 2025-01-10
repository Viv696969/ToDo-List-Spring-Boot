package com.vivekempire.TodoList.controllers;


import com.vivekempire.TodoList.dto.req.RegisterUserReqDTO;
import com.vivekempire.TodoList.dto.resp.MssgRespDTO;
import com.vivekempire.TodoList.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/register_user/")
    public ResponseEntity<?> registerUser( @RequestBody RegisterUserReqDTO registerUserReqDTO){

        return authService.registerUser(registerUserReqDTO);
    }
}
