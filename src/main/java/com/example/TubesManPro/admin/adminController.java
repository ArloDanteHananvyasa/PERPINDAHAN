package com.example.TubesManPro.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.datas.PenjualanData;
import com.example.datas.ProdukData;
import com.example.datas.UMKData;
import com.example.repositories.AdminRepository;

@Controller
@RequestMapping("/admin")
public class adminController {

        @Autowired
        private AdminRepository repo;

        @GetMapping("/home")
        private String homepage(Model model) {
                List<UMKData> umkTerdaftar = repo.findAll();
                List<UMKData> umkTidakTerdaftar = repo.findVerif();
                List<ProdukData> terlaku = repo.findTerlaku();
                List<PenjualanData> penjualan = repo.findPenjualan();

                model.addAttribute("totalUmkTerdaftar", umkTerdaftar.size() + " UMK");
                model.addAttribute("totalUmkTidakTerdaftar", umkTidakTerdaftar.size() + " UMK");
                if (umkTidakTerdaftar.size() > 0) {
                        model.addAttribute("totalUmkTidakTerdaftarBadge", umkTidakTerdaftar.size());
                }

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
        public String daWftarUMK(Model model) {
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

        @GetMapping("/terlaris")
        public String umkTerlaris(Model model) {
                List<PenjualanData> terlaris = repo.findPenjualan();

                model.addAttribute("results", terlaris);

                return "admin/umkTerlaris";

        }

        @GetMapping("/penjualan")
        public String mainPenjualan() {
                return "admin/Main_Penjualan_Admin";

        }
}
