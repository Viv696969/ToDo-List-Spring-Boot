package com.vivekempire.TodoList.services;

import com.vivekempire.TodoList.dto.req.RegisterUserReqDTO;
import com.vivekempire.TodoList.dto.resp.ErrorResponse;
import com.vivekempire.TodoList.dto.resp.MssgRespDTO;
import com.vivekempire.TodoList.entities.CustomUser;
import com.vivekempire.TodoList.exceptions.TodoCustomException;
import com.vivekempire.TodoList.repositories.CustomUserRepository;
import com.vivekempire.TodoList.utils.GoogleOAuthUtility;
import com.vivekempire.TodoList.utils.JWTHelper;
import com.vivekempire.TodoList.utils.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GoogleOAuthUtility googleOAuthUtility;


    private boolean userExists(String email){
        return customUserRepository.findByEmail(email).isPresent();
    }

    public ResponseEntity<?> registerUser(RegisterUserReqDTO registerUserReqDTO) throws Exception {
        String password=registerUserReqDTO.getPassword();
        String confirmPassword=registerUserReqDTO.getConfirmPassword();
        if (!confirmPassword.equals(password)){
            throw new TodoCustomException("Passwords Dont Match...",HttpStatus.BAD_REQUEST);
        }

        if(userExists(registerUserReqDTO.getEmail())){
            throw new TodoCustomException("User Already Exists..with email : "+registerUserReqDTO.getEmail(),HttpStatus.BAD_REQUEST);
        }
        else{
            CustomUser user=new CustomUser();
            user.setEmail(registerUserReqDTO.getEmail());
            user.setHashedPassword(encoder.encode(password));
            user=customUserRepository.save(user);
            String token=jwtHelper.createToken(user.getId(), user.getEmail());
            Map<String, Object> data=new HashMap<>();
            data.put("email",user.getEmail());
            mailUtility.sendTemplateMail(user.getEmail(),"Welcome to spring boot todo" ,data);
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
            throw new TodoCustomException("No Such User exists...",HttpStatus.BAD_REQUEST);
        if (!encoder.matches(password,user.get().getHashedPassword()))
            throw new TodoCustomException("Incorrect Password...",HttpStatus.BAD_REQUEST);

        String token=jwtHelper.createToken(user.get().getId(),user.get().getEmail());
        return ResponseEntity.status(200).body(new MssgRespDTO("Login Successfull...",true,token));

    }

    public ResponseEntity<MssgRespDTO> handleGoogleLogIn(String code) {
        try {
//        create a hashmap with all required values
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id",googleOAuthUtility.getGoogleClientId() );
            params.add("client_secret", googleOAuthUtility.getGoogleSecret());
            params.add("redirect_uri", "http://localhost:5500/test.html");
            params.add("grant_type", "authorization_code");
//        create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        create request with params and headers
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        send request
            ResponseEntity<Map> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                    request, Map.class
            );
//        get token
            String idToken = (String) response.getBody().get("id_token");
//        get user info
            String tokenUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userDataResponse = restTemplate.getForEntity(tokenUrl, Map.class);
            Map<String, String> userData = userDataResponse.getBody();
            Optional<CustomUser> optionUser = customUserRepository.findByEmail((String) userData.get("email"));
            CustomUser customUser;
            if (optionUser.isEmpty()) {
                customUser = new CustomUser();
                customUser.setEmail((String) userData.get("email"));
                customUser.setHashedPassword(encoder.encode(UUID.randomUUID().toString()));
                customUserRepository.save(customUser);
                Map<String, Object> data=new HashMap<>();
                data.put("email",customUser.getEmail());
                mailUtility.sendTemplateMail(customUser.getEmail(),"welcome to signing by google",data);
            } else {
                customUser = optionUser.get();

            }
            String token = jwtHelper.createToken(customUser.getId(), customUser.getEmail());
            return ResponseEntity.status(200).body(new MssgRespDTO("Login Successfull...", true, token));
        } catch (Exception e){

            throw new TodoCustomException("Login Unsuccessfull...",HttpStatus.BAD_REQUEST);
        }
    }




    }

