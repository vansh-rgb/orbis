package com.strink.orbis.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestHandler {

    @GetMapping("/greetings")
    public ResponseEntity<String> greetings() {
        return new ResponseEntity<>("Hello", HttpStatus.ACCEPTED);
    }
}
