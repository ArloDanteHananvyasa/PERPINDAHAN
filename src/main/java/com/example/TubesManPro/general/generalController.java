package com.example.TubesManPro.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.datas.LoginData;
import com.example.datas.UMKData;
import com.example.repositories.generalRepository;

import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/out")
    public String logout(HttpSession session) {

        session.setAttribute("loggedInUser", null);

        return "general/LoginPage";
    }

    @PostMapping("/verify")
    public String verifyLogin(
            @RequestParam(value = "noHp", required = false) String noHp,
            @RequestParam(value = "password", required = false) String password,
            HttpSession session,
            Model model) {

        // Perform login
        LoginData loggedInUser = repo.login(noHp, password);

        if (loggedInUser != null) {
            // Store user in session
            session.setAttribute("loggedInUser", loggedInUser);

            if (loggedInUser.getRole().equalsIgnoreCase("umk")) {
                UMKData user = repo.getUMK(noHp);

                if (user.getStatus().equalsIgnoreCase("Ditolak")) {
                    model.addAttribute("error", "Pendaftaran Anda ditolak oleh admin");
                    return "general/LoginPage";
                } else if (user.getStatus().equalsIgnoreCase("Belum Diverifikasi")) {
                    model.addAttribute("error", "Akun anda belum diverifikasi oleh admin");
                    return "general/LoginPage";
                }

                session.setAttribute("umkData", user);
            }

            // Redirect based on user role
            if ("UMK".equalsIgnoreCase(loggedInUser.getRole())) {
                return "redirect:/umk/home";
            } else {
                return "redirect:/admin/home";
            }
        } else {
            model.addAttribute("noHp", "");
            model.addAttribute("password", "");
            model.addAttribute("error", "Akun anda tidak terdaftar");
            return "general/LoginPage";
        }
    }

}
