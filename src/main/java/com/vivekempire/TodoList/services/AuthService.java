package com.vivekempire.TodoList.services;

import com.vivekempire.TodoList.dto.req.RegisterUserReqDTO;
import com.vivekempire.TodoList.dto.resp.MssgRespDTO;
import com.vivekempire.TodoList.entities.CustomUser;
import com.vivekempire.TodoList.repositories.CustomUserRepository;
import com.vivekempire.TodoList.utils.JWTHelper;
import com.vivekempire.TodoList.utils.MailUtility;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MailUtility mailUtility;


    private boolean userExists(String email){
        return customUserRepository.findByEmail(email).isPresent();
    }

    public ResponseEntity<?> registerUser(RegisterUserReqDTO registerUserReqDTO) throws MessagingException {
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
            user=customUserRepository.save(user);
            String token=jwtHelper.createToken(user.getId(), user.getEmail());
            mailUtility.sendHtmlMail(user.getEmail(),"Welcome to spring boot todo" , """
                    <h1>Welcome to Our Service</h1>
                    <p>We are glad to have you!</p>
                    <p>Click <a href="https://example.com">here</a> to explore more.</p>
                    """ );
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new MssgRespDTO("Registeration Successfull...",true,token)
            );
        }
    }


    public ResponseEntity<?> loginUser(String email,String password) {
//        1. check if user exits
//        2. if exists check password
//        3. create token
        Optional<CustomUser> user=customUserRepository.findByEmail(email);
        if (user.isEmpty())
            return ResponseEntity.status(400).body(new MssgRespDTO("No Such User with "+email+" exists...",false));
        if (!encoder.matches(password,user.get().getHashedPassword()))
            return ResponseEntity.status(400).body(new MssgRespDTO("Incorrect password...",false));

        String token=jwtHelper.createToken(user.get().getId(),user.get().getEmail());
        return ResponseEntity.status(200).body(new MssgRespDTO("Login Successfull...",true,token));

    }
}
