package com.example.TubesManPro.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.datas.UMKData;
import com.example.repositories.AdminRepository;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private AdminRepository repo;

    @GetMapping("/umk")
    public String daftarUMK(Model model) {
        List<UMKData> umk = repo.findAll();

        model.addAttribute("results", umk);

        return "admin/DaftarUMK";

    }

    @GetMapping("/verifikasi")
    public String daftarVerif(Model model) {
        List<UMKData> umk = repo.findVerif();

        model.addAttribute("results", umk);

        return "admin/VerifikasiUMK";

    }
}
