package com.example.TubesManPro.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class landingController {

    @GetMapping("")
    public String loginPage() {
        return "general/LandingPage";
    }

}
