package com.example.TubesManPro.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dataViews.UMKDataView;
import com.example.datas.LoginData;
import com.example.datas.PenjualanDataAdmin;
import com.example.datas.ProdukTerjualData;
import com.example.repositories.AdminRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class adminController {

        @Autowired
        private AdminRepository repo;
        private LoginData login;

        @GetMapping("/home")
        private String homepage(HttpSession session, Model model) {
                List<UMKDataView> umkTerdaftar = repo.findAll();
                List<UMKDataView> umkTidakTerdaftar = repo.findVerif();
                List<ProdukTerjualData> terlaku = repo.findTerlaku();
                List<PenjualanDataAdmin> penjualan = repo.findPenjualan();

                this.login = (LoginData) session.getAttribute("loggedInUser");

                if (login == null) {
                        return "redirect:/login/";
                }

                model.addAttribute("name", login.getNoHp());

                model.addAttribute("totalUmkTerdaftar", umkTerdaftar.size() + " UMK");
                model.addAttribute("totalUmkTidakTerdaftar", umkTidakTerdaftar.size() + " UMK");
                model.addAttribute("totalUmkTidakTerdaftarBadge", umkTidakTerdaftar.size());

                if (umkTerdaftar.size() >= 3) {
                        model.addAttribute("UMK1",
                                        "UMK '" + umkTerdaftar.get(0).getNamaUMK() + "'");
                        model.addAttribute("UMK2",
                                        "UMK '" + umkTerdaftar.get(1).getNamaUMK() + "'");
                        model.addAttribute("UMK3",
                                        "UMK '" + umkTerdaftar.get(2).getNamaUMK() + "'");
                }

                if (terlaku.size() >= 3) {
                        model.addAttribute("terlaku1",
                                        terlaku.get(0).getIdproduk() + " - " + terlaku.get(0).getKuantitas()
                                                        + " Terjual");
                        model.addAttribute("terlaku2",
                                        terlaku.get(1).getIdproduk() + " - " + terlaku.get(1).getKuantitas()
                                                        + " Terjual");
                        model.addAttribute("terlaku3",
                                        terlaku.get(2).getIdproduk() + " - " + terlaku.get(2).getKuantitas()
                                                        + " Terjual");
                }

                if (penjualan.size() >= 3) {
                        model.addAttribute("topUMK1", "UMK " +
                                        penjualan.get(0).getNamaUMK() + " - " + penjualan.get(0).getJumlahTransaksi()
                                        + " Terjual");
                        model.addAttribute("topUMK2", "UMK " +
                                        penjualan.get(1).getNamaUMK() + " - " + penjualan.get(1).getJumlahTransaksi()
                                        + " Terjual");
                        model.addAttribute("topUMK3", "UMK " +
                                        penjualan.get(2).getNamaUMK() + " - " + penjualan.get(2).getJumlahTransaksi()
                                        + " Terjual");

                }

                return "admin/index";
        }

        @GetMapping("/umk")
        public String daWftarUMK(HttpSession session, Model model) {

                this.login = (LoginData) session.getAttribute("loggedInUser");

                if (login == null) {
                        return "redirect:/login/";
                }

                List<UMKDataView> umk = repo.findAll();

                List<UMKDataView> umkTidakTerdaftar = repo.findVerif();
                model.addAttribute("totalUmkTidakTerdaftarBadge", umkTidakTerdaftar.size());

                model.addAttribute("results", umk);

                return "admin/DaftarUMK";

        }

        @GetMapping("/umk/detail")
        public String getUMKDetail(@RequestParam("hp") String hp, Model model) {
                UMKDataView umkDetail = repo.findViewByNoHp(hp);
                model.addAttribute("umkDetail", umkDetail);
                List<UMKDataView> umkList = repo.findAll();
                model.addAttribute("results", umkList);
                return "admin/DaftarUMK";
        }

        @GetMapping("/verifikasi")
        public String daftarVerif(HttpSession session, Model model) {

                this.login = (LoginData) session.getAttribute("loggedInUser");

                if (login == null) {
                        return "redirect:/login/";
                }

                List<UMKDataView> umk = repo.findVerif();

                List<UMKDataView> umkTidakTerdaftar = repo.findVerif();
                model.addAttribute("totalUmkTidakTerdaftarBadge", umkTidakTerdaftar.size());

                model.addAttribute("results", umk);

                return "admin/VerifikasiUMK";

        }

        @GetMapping("/verifikasi/detail")
        public String getUMKDetailVerif(@RequestParam(value = "hp") String hp, Model model) {
                UMKDataView umkDetail = repo.findViewByNoHp(hp);
                model.addAttribute("umkDetail", umkDetail);
                List<UMKDataView> umkList = repo.findVerif();
                model.addAttribute("results", umkList);
                return "admin/VerifikasiUMK";
        }

        @GetMapping("/verifikasi/verify")
        public String verifUMK(@RequestParam(value = "hp") String hp, Model model) {
                UMKDataView umkDetail = repo.findViewByNoHp(hp);
                model.addAttribute("umkDetail", umkDetail);

                repo.verifUMK(1, umkDetail.getIdPendaftaran(), login.getNoHp());

                List<UMKDataView> umkList = repo.findVerif();
                model.addAttribute("results", umkList);
                return "admin/VerifikasiUMK";
        }

        @GetMapping("/verifikasi/reject")
        public String rejectUMK(@RequestParam(value = "hp") String hp, Model model) {
                UMKDataView umkDetail = repo.findViewByNoHp(hp);
                model.addAttribute("umkDetail", umkDetail);

                repo.verifUMK(2, umkDetail.getIdPendaftaran(), login.getNoHp());

                List<UMKDataView> umkList = repo.findVerif();
                model.addAttribute("results", umkList);
                return "admin/VerifikasiUMK";
        }

        @GetMapping("/penjualan")
        public String mainPenjualan(HttpSession session, Model model) {

                this.login = (LoginData) session.getAttribute("loggedInUser");

                if (login == null) {
                        return "redirect:/login/";
                }

                List<UMKDataView> umkTidakTerdaftar = repo.findVerif();
                model.addAttribute("totalUmkTidakTerdaftarBadge", umkTidakTerdaftar.size());
                return "admin/Main_Penjualan_Admin";

        }

        @GetMapping("/penjualan/terlaris")
        public String umkTerlarisWithFilter(@RequestParam(value = "filter", required = false) Integer filter,
                        Model model) {

                List<PenjualanDataAdmin> terlaris;
                if (filter != null) {
                        terlaris = repo.findPenjualan(filter);
                } else {
                        terlaris = repo.findPenjualan();
                }

                List<UMKDataView> umkTidakTerdaftar = repo.findVerif();
                model.addAttribute("totalUmkTidakTerdaftarBadge", umkTidakTerdaftar.size());

                model.addAttribute("results", terlaris);

                return "admin/umkTerlaris";

        }

        @GetMapping("/penjualan/terlaku")
        public String produkTerlaku(@RequestParam(value = "filter", required = false) Integer filter, Model model) {

                List<ProdukTerjualData> terlaku;
                if (filter != null) {
                        terlaku = repo.findTerlaku(filter);
                } else {
                        terlaku = repo.findTerlaku();
                }

                List<UMKDataView> umkTidakTerdaftar = repo.findVerif();
                model.addAttribute("totalUmkTidakTerdaftarBadge", umkTidakTerdaftar.size());

                model.addAttribute("results", terlaku);

                return "admin/produkTerlaku";

        }

}
