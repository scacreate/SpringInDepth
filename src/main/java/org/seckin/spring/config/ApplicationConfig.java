package org.seckin.spring.config;

import org.seckin.spring.service.GreetingService;
import org.seckin.spring.service.OutputService;
import org.seckin.spring.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
    @Value("${app.greeting}")
    private String greeting;

    @Value("${app.name}")
    private String name;

    @Value("#{new Boolean(environment['spring.profiles.active']!='dev')}")
    private boolean is24;

    @Autowired
    private GreetingService greetingsService;

    @Autowired
    private TimeService timeService;


    @Bean
    public TimeService timeService(){
        return new TimeService(is24);
    }



    @Bean
    public OutputService outputService(){
        return new OutputService(greetingsService, timeService, name);
    }

    @Bean
    public GreetingService greetingService(){
        return new GreetingService(greeting);
    }

}
