package com.example.TubesManPro.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.datas.LoginData;
import com.example.datas.UMKData;
import com.example.repositories.generalRepository;

@Repository
public class JDBCGeneral implements generalRepository {

    @Autowired
    private JdbcTemplate jdbc;

    private String role;

    @Override
    public LoginData login(String noHp, String password) {

        this.role = this.cekAdminOrUMK(noHp);

        if (role == null) {
            return null;
        }

        String query = "SELECT * FROM " + role + " WHERE NoHP = ? AND Pass = ?";
        List<LoginData> res = jdbc.query(query, this::mapRowToLoginData, noHp, password);

        return res.isEmpty() ? null : res.get(0);
    }

    private String cekAdminOrUMK(String noHp) {

        String adminQuery = "SELECT COUNT(*) FROM Administrator WHERE NoHP = ?";
        Integer adminCount = jdbc.queryForObject(adminQuery, Integer.class, noHp);

        if (adminCount != null && adminCount > 0) {
            return "Administrator";
        }

        String umkQuery = "SELECT COUNT(*) FROM UMK WHERE NoHP = ?";
        Integer umkCount = jdbc.queryForObject(umkQuery, Integer.class, noHp);

        if (umkCount != null && umkCount > 0) {
            return "UMK";
        }

        return null;
    }

    @Override
    public UMKData getUMK(String noHp) {
        List<UMKData> user = jdbc.query("SELECT * FROM umk WHERE NoHP = ?", this::mapRowToUmkData, noHp);

        return user.get(0);
    }

    private LoginData mapRowToLoginData(ResultSet resultSet, int rowNum) throws SQLException {
        return new LoginData(
                resultSet.getString("NoHP"),
                resultSet.getString("Pass"),
                this.role);
    }

    private UMKData mapRowToUmkData(ResultSet resultSet, int rowNum) throws SQLException {
        return new UMKData(
                resultSet.getString("nohp"),
                resultSet.getString("namaumk"),
                resultSet.getString("deskripsi"),
                resultSet.getString("logo"),
                resultSet.getString("alamat"),
                resultSet.getInt("idpendaftaran"),
                resultSet.getString("status"),
                resultSet.getDate("tanggal"),
                resultSet.getDouble("saldo"),
                resultSet.getString("namapemilik"),
                resultSet.getString("email"));
    }
}
