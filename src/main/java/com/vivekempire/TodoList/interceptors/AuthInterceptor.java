package com.vivekempire.TodoList.interceptors;


import com.vivekempire.TodoList.utils.JWTHelper;
import com.vivekempire.TodoList.utils.RedisUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private RedisUtility redisUtility;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("Authorization");
        if (token==null || !token.startsWith("Bearer ")){
            response.setStatus(401);
            return false;
        }
        token=token.substring(7);
        String user_id = redisUtility.get(token);
        if (user_id!=null){
            request.setAttribute("user_id",user_id);
            return true;
        }
        Map<String,Object> authHashMap=jwtHelper.isValid(token);
        if ((boolean)authHashMap.get("status")){
            user_id=(String) authHashMap.get("user_id");
            redisUtility.set(token,user_id,30);
            request.setAttribute("user_id",user_id);
            return true;
        }
        response.setStatus(401);
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("In the Post Handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("In the after completion part.. :) ");
    }
}
