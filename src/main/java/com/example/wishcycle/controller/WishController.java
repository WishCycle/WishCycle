package com.example.wishcycle.controller;

import com.example.wishcycle.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wichcycle")
public class WishController {

    private final WishService service;

    public WishController(WishService service) {
        this.service = service;
    }

}
