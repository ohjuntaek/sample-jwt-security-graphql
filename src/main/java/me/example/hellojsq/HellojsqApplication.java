package me.example.hellojsq;

import lombok.RequiredArgsConstructor;
import me.example.hellojsq.member.SecurityUserDetailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HellojsqApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellojsqApplication.class, args);
    }

}

@RestController
class hello {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
