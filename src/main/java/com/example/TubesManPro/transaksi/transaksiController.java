package com.example.TubesManPro.transaksi;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dataViews.UMKDataView;
import com.example.datas.CartItem;
import com.example.datas.LoginData;
import com.example.datas.ProdukData;
import com.example.datas.UMKData;
import com.example.repositories.AdminRepository;
import com.example.repositories.TransaksiRepository;
import com.example.repositories.umkRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/umk")
public class transaksiController {

    @Autowired
    private AdminRepository admin;

    @Autowired
    private umkRepository umk;

    @Autowired
    private TransaksiRepository repo;

    @GetMapping("/transaksi")

    public String showTransaksi(HttpSession session) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        return "umk/transaksi";
    }

    @GetMapping("/transaksi/setor-modal")
    public String showSetorModal(HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        System.out.println("pindah ke setor");

        UMKDataView userView = admin.findViewByNoHp(login.getNoHp());

        model.addAttribute("saldo", userView.getSaldo());

        return "umk/transaksi/setorModal";
    }

    @PostMapping("/transaksi/setor-modal/tambah")
    public String SetorModal(@RequestParam(value = "tanggal") String tanggal,
            @RequestParam(value = "nominal") double nominal, HttpSession session, Model model) {

        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        System.out.println("Tanggal: " + tanggal);
        System.out.println("Nominal: " + nominal);

        Date date = Date.valueOf(LocalDate.parse(tanggal));

        repo.setorModal(date, nominal, login.getNoHp());

        UMKDataView userView = admin.findViewByNoHp(login.getNoHp());

        model.addAttribute("saldo", userView.getSaldo());
        model.addAttribute("nominal", "");
        model.addAttribute("success", true);

        return "umk/transaksi";
    }

    @GetMapping("/transaksi/tarik-modal")
    public String showTarikModal(HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        UMKDataView userView = admin.findViewByNoHp(login.getNoHp());

        model.addAttribute("saldo", userView.getSaldo());

        return "umk/transaksi/tarikModal";
    }

    @PostMapping("/transaksi/tarik-modal/tambah")
    public String TarikModal(@RequestParam(value = "tanggal") String tanggal,
            @RequestParam(value = "nominal") double nominal, @RequestParam(value = "rekening") String rekening,
            HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        System.out.println("Tanggal: " + tanggal);
        System.out.println("Nominal: " + nominal);

        UMKDataView userView = admin.findViewByNoHp(login.getNoHp());
        UMKData user = admin.findByNoHp(login.getNoHp());

        Date date = Date.valueOf(LocalDate.parse(tanggal));

        if (nominal > user.getSaldo()) {
            model.addAttribute("error", "Nominal melebihi saldo saat ini");
            model.addAttribute("saldo", userView.getSaldo());
            return "umk/transaksi/tarikModal";
        }

        repo.tarikModal(date, nominal, rekening, login.getNoHp());

        userView = admin.findViewByNoHp(login.getNoHp());

        model.addAttribute("saldo", userView.getSaldo());
        model.addAttribute("nominal", "");
        model.addAttribute("success", true);

        return "umk/transaksi";
    }

    @GetMapping("/transaksi/penjualan-produk")
    public String showPenjualanProduk(HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        List<ProdukData> produk = umk.findProduk(login.getNoHp());

        model.addAttribute("products", produk);
        model.addAttribute("saldo", admin.findViewByNoHp(login.getNoHp()).getSaldo());

        return "umk/transaksi/penjualan";
    }

    @PostMapping("/transaksi/penjualan-produk/tambah")
    public String PenjualanProduk(@RequestParam("pilih-produk") String namaProduk,
            @RequestParam("jumlah") int jumlah,
            @RequestParam("tanggal") String tanggal, @RequestParam("hargaTotal") double total,
            HttpSession session, Model model) {

        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        CartItem item = new CartItem(namaProduk, jumlah, total, tanggal);

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(item);

        System.out.println(namaProduk);
        System.out.println(jumlah);
        System.out.println(tanggal);
        System.out.println(total);
        if (!cart.isEmpty()) {
            System.out.println("YES");
        }

        session.setAttribute("cart", cart);

        List<ProdukData> produk = umk.findProduk(login.getNoHp());
        model.addAttribute("products", produk);

        // Add updated cart to model
        model.addAttribute("results", cart);
        model.addAttribute("tanggal", tanggal);
        model.addAttribute("saldo", admin.findViewByNoHp(login.getNoHp()).getSaldo());

        return "umk/transaksi/penjualan";
    }

    @GetMapping("/transaksi/penjualan-produk/submit")
    public String submitPenjualanProduk(HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        System.out.println("Cart contents:" + cart);
        repo.penjualanProduk(cart, login.getNoHp());

        UMKDataView userView = admin.findViewByNoHp(login.getNoHp());

        model.addAttribute("saldo", userView.getSaldo());
        model.addAttribute("success", true);

        return "umk/transaksi";
    }

    @GetMapping("/transaksi/biaya-operasional")
    public String showBiayaOperasional(HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        UMKDataView userView = admin.findViewByNoHp(login.getNoHp());

        model.addAttribute("saldo", userView.getSaldo());

        return "umk/transaksi/biayaOps";
    }

    @PostMapping("/transaksi/biaya-operasional/tambah")
    public String BiayaOperasional(@RequestParam(value = "tanggal") String tanggal,
            @RequestParam(value = "nominal") double nominal, HttpSession session, Model model) {
        LoginData login = (LoginData) session.getAttribute("loggedInUser");

        if (login == null) {
            return "redirect:/login/";
        }

        System.out.println("Tanggal: " + tanggal);
        System.out.println("Nominal: " + nominal);

        UMKDataView userView = admin.findViewByNoHp(login.getNoHp());
        UMKData user = admin.findByNoHp(login.getNoHp());

        Date date = Date.valueOf(LocalDate.parse(tanggal));

        if (nominal > user.getSaldo()) {
            model.addAttribute("error", "Nominal melebihi saldo saat ini");
            model.addAttribute("saldo", userView.getSaldo());
            return "umk/transaksi/biayaOps";
        }

        repo.biayaOperasional(date, nominal, login.getNoHp());

        userView = admin.findViewByNoHp(login.getNoHp());

        model.addAttribute("saldo", userView.getSaldo());
        model.addAttribute("nominal", "");
        model.addAttribute("success", true);

        return "umk/transaksi";
    }
}
