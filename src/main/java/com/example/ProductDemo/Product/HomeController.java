package com.example.ProductDemo.Product;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Component
@Controller
public class HomeController {
    @RequestMapping(value = {"/", "/home", "/index"})
    public String homepage(){
        return "mainpage.html";
    }

}
