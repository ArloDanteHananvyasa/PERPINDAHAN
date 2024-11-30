package com.example.TubesManPro.umk;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.datas.KeuanganData;
import com.example.datas.LoginData;
import com.example.datas.PenjualanDataUMK;
import com.example.datas.ProdukData;
import com.example.datas.UMKData;
import com.example.repositories.AdminRepository;
import com.example.repositories.registerRepo;
import com.example.repositories.umkRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/umk")
public class umkController {

    @Autowired
    private registerRepo regis;
    @Autowired
    private umkRepository repo;
    @Autowired
    private AdminRepository admin;

    @PostMapping("/submit")
    public String login(@RequestParam(value = "namaUMK") String namaUMK,
            @RequestParam(value = "namaPemilik") String namaPemilik, @RequestParam(value = "email") String email,
            @RequestParam(value = "noHp") String noHp, @RequestParam(value = "password") String password,
            @RequestParam(value = "ulangPassword") String ulangPassword, Model model) {

        if (noHp.length() > 0 && namaUMK.length() > 0 && namaPemilik.length() > 0 && email.length() > 0
                && password.length() > 0) {
            regis.register(noHp, namaUMK, namaPemilik, email, password);
            return "redirect:/login/";
        } else {
            return "umk/register";
        }
    }

    @GetMapping("/registrasi")
    public String registerUMK() {
        return "umk/register";

    }

    @GetMapping("/home")
    public String showHomepage(HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        UMKData user = admin.findByNoHp(login.getNoHp());

        List<PenjualanDataUMK> penjualan = repo.findPenjualan(login.getNoHp(), Date.valueOf("1800-01-01"),
                Date.valueOf("2500-12-31"));
        List<ProdukData> produk = repo.findProduk(login.getNoHp());

        long totalBarangTerjual = 0;
        double totalPenjualan = 0;

        for (PenjualanDataUMK p : penjualan) {
            totalBarangTerjual += p.getJumlah();
            totalPenjualan += p.getTotal();
        }

        model.addAttribute("user", user.getNamaUMK());
        model.addAttribute("saldo", user.getSaldo());
        model.addAttribute("totalPenjualan", totalPenjualan);
        model.addAttribute("totalBarangTerjual", totalBarangTerjual);
        model.addAttribute("jumlahProduk", produk.size());

        return "umk/homepage";

    }

    @GetMapping("/produk")
    public String daftarProduk(@RequestParam(value = "filter", required = false) String filter, HttpSession session,
            Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        List<ProdukData> produk;

        if (filter == null) {
            produk = repo.findProduk(login.getNoHp());
        } else {
            produk = repo.findProduk(login.getNoHp(), filter);
        }

        model.addAttribute("results", produk);

        UMKData user = (UMKData) session.getAttribute("umkData");
        model.addAttribute("profilePic", user.getLogo());

        return "umk/daftarProduk";
    }

    @GetMapping("/penjualan")
    public String penjualan(
            @RequestParam(value = "transaksiStart", required = false, defaultValue = "1800-01-01") String start,
            @RequestParam(value = "transaksiEnd", required = false, defaultValue = "2500-12-31") String end,
            HttpSession session,
            Model model) {

        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        Date startDate = Date.valueOf(LocalDate.parse(start));
        Date endDate = Date.valueOf(LocalDate.parse(end));

        List<PenjualanDataUMK> penjualan = repo.findPenjualan(login.getNoHp(), startDate, endDate);

        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        UMKData user = (UMKData) session.getAttribute("umkData");
        model.addAttribute("profilePic", user.getLogo());

        model.addAttribute("results", penjualan);
        model.addAttribute("transaksiStart", start);
        model.addAttribute("transaksiEnd", end);

        return "umk/penjualan";
    }

    @GetMapping("/keuangan")
    public String keuangan(
            @RequestParam(value = "transaksiStart", required = false, defaultValue = "1800-01-01") String start,
            @RequestParam(value = "transaksiEnd", required = false, defaultValue = "2500-12-31") String end,
            HttpSession session,
            Model model) {

        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        Date startDate = Date.valueOf(LocalDate.parse(start));
        Date endDate = Date.valueOf(LocalDate.parse(end));

        List<KeuanganData> keuangan = repo.findKeuangan(login.getNoHp(), startDate, endDate);

        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        UMKData user = (UMKData) session.getAttribute("umkData");
        model.addAttribute("profilePic", user.getLogo());
        model.addAttribute("saldo", user.getSaldo());

        model.addAttribute("results", keuangan);
        model.addAttribute("transaksiStart", start);
        model.addAttribute("transaksiEnd", end);

        return "umk/keuangan";
    }
}
