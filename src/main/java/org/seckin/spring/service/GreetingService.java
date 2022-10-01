package org.seckin.spring.service;

import org.seckin.spring.aspect.Countable;
import org.seckin.spring.aspect.Loggable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    @Value("${app.greeting}")
    private String greeting;

    @Value("0")
    private Integer greetingCount;

    public GreetingService(){
        super();
    }

    @Loggable
    @Countable
    public String getGreeting(String name, Integer greetingCount){
        this.greetingCount++;
        return greeting + " " + name;
    }


    public Integer getGreetingCount(){
        return this.greetingCount;
    }
}
