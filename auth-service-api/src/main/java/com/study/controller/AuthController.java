package com.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AuthController {

    @GetMapping("/api/v1/auth/token/{series}")
    String getToken(@PathVariable("series") String series);
}
