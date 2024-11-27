package com.example.TubesManPro.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.repositories.generalRepository;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/login")
public class generalController {

    @Autowired
    private generalRepository repo;

    @GetMapping("/")
    public String loginPage() {

        return "general/LoginPage";
    }

    @PostMapping("/verify")
    public String verifyLogin(
            @RequestParam(value = "noHp", required = false) String noHp,
            @RequestParam(value = "password", required = false) String password,
            Model model) {

        // String user = repo.getSession();
        boolean masuk = repo.login(noHp, password);

        // if (masuk) {
        // if (user.equals("umk")) {
        // return "redirect:/umk/homepage";
        // } else {
        // return "redirect:/admin/umk";
        // }
        if (masuk) {
            if (noHp.length() > 4) {
                return "redirect:/admin/verifikasi";
            } else {
                return "redirect:/admin/home";
            }
        } else {
            model.addAttribute("noHp", "");
            model.addAttribute("password", "");
            return "redirect:/login/";
        }
    }
}
