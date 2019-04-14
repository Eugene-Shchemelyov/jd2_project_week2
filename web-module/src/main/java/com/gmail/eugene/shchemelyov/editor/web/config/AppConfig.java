package com.gmail.eugene.shchemelyov.editor.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "com.gmail.eugene.shchemelyov.editor.web",
        "com.gmail.eugene.shchemelyov.editor.service",
        "com.gmail.eugene.shchemelyov.editor.repository"
})
@PropertySource("classpath:jdbc.properties")
@EnableAspectJAutoProxy
public class AppConfig {
}
