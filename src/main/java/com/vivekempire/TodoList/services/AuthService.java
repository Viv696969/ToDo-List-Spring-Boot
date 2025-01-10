package com.vivekempire.TodoList.services;

import com.vivekempire.TodoList.dto.req.RegisterUserReqDTO;
import com.vivekempire.TodoList.dto.resp.MssgRespDTO;
import com.vivekempire.TodoList.entities.CustomUser;
import com.vivekempire.TodoList.repositories.CustomUserRepository;
import com.vivekempire.TodoList.utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private JWTHelper jwtHelper;

    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(10);


    private boolean userExists(String email){
        return customUserRepository.findByEmail(email).isPresent();
    }

    public ResponseEntity<?> registerUser(RegisterUserReqDTO registerUserReqDTO){
        String password=registerUserReqDTO.getPassword();
        String confirmPassword=registerUserReqDTO.getConfirmPassword();
        if (!confirmPassword.equals(password)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MssgRespDTO("Passwords Dont Match...",false)
            );
        }

        if(userExists(registerUserReqDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MssgRespDTO("User already exists..",false)
            );
        }
        else{
            CustomUser user=new CustomUser();
            user.setEmail(registerUserReqDTO.getEmail());
            user.setHashedPassword(encoder.encode(password));
            System.out.println(user);
            user=customUserRepository.save(user);
            String token=jwtHelper.createToken(user.getId(), user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new MssgRespDTO("Registeration Successfull...",true,token)
            );
        }
    }
}
