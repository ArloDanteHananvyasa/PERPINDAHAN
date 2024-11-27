package com.example.TubesManPro.umk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.repositories.registerRepo;

@Controller
@RequestMapping("/umk")
public class umkController {

    @Autowired
    private registerRepo repo;

    @PostMapping("/submit")
    public String login(@RequestParam(value = "namaUMK") String namaUMK,
            @RequestParam(value = "namaPemilik") String namaPemilik, @RequestParam(value = "email") String email,
            @RequestParam(value = "noHp") String noHp, @RequestParam(value = "password") String password,
            @RequestParam(value = "ulangPassword") String ulangPassword, Model model) {

        if (noHp.length() > 0 && namaUMK.length() > 0 && namaPemilik.length() > 0 && email.length() > 0
                && password.length() > 0) {
            repo.register(noHp, namaUMK, namaPemilik, email, password);
            return "redirect:/login/";
        } else {
            return "umk/register";
        }
    }

    @GetMapping("/registrasi")
    public String daftarUMK() {
        return "umk/register";

    }

}
