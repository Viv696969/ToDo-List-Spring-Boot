package com.vivekempire.TodoList.config;


import com.vivekempire.TodoList.interceptors.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.concurrent.Executor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

//    @Bean
//    public Executor createExecutor(){
//        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setQueueCapacity(3);
//        executor.setMaxPoolSize(4);
//        executor.setThreadNamePrefix("My Thread -");
//        executor.initialize();
//        return executor;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/todo/*")
                .excludePathPatterns(Arrays.asList("/show_completed","/add_todo","/get_all_todos"))
                ;
    }
}
