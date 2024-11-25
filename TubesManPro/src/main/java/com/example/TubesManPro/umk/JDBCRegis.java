package com.example.TubesManPro.umk;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.repositories.registerRepo;

@Repository
public class JDBCRegis implements registerRepo {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void register(String hp, String namaUMK, String namaPem,
            String email, String password) {
        LocalDate today = LocalDate.now();
        Date tanggal = Date.valueOf(today);
        jdbc.execute(
                "INSERT INTO UMK (NoHP, NamaUMK, Deskripsi, Logo, Alamat, NamaPemilik, Email, Status, Tanggal, Saldo, Pass) VALUES ('"
                        + hp + "','" + namaUMK + "',NULL,NULL,NULL,'" + namaPem
                        + "','" + email + "','Belum Diverifikasi','" + tanggal + "', 0,'" + password + "')");
        jdbc.execute("INSERT INTO Pendaftaran (Tanggal, Status)VALUES ('" + tanggal + "','Belum Diverifikasi');");
    }
}
