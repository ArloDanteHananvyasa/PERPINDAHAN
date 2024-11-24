package com.example.TubesManPro.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.datas.UMKData;
import com.example.repositories.AdminRepository;

@Repository
public class JDBCAdmin implements AdminRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<UMKData> findAll() {
        List<UMKData> umk = jdbc.query(
                "SELECT * FROM umk JOIN kota ON umk.idkota = kota.idkota JOIN provinsi ON kota.idprovinsi = provinsi.idprovinsi WHERE umk.status = 'Valid'",
                this::mapRowToUmkData);
        return umk;
    }

    @Override
    public List<UMKData> findVerif() {
        List<UMKData> umk = jdbc.query(
                "SELECT * FROM umk WHERE status = 'Belum diverifikasi' ORDER BY IDPendaftaran",
                this::mapRowToUmkData);
        return umk;
    }

    private UMKData mapRowToUmkData(ResultSet resultSet, int rowNum) throws SQLException {
        return new UMKData(
                resultSet.getString("nohp"),
                resultSet.getString("namaumk"),
                resultSet.getString("deskripsi"),
                resultSet.getString("logo"),
                resultSet.getString("alamat"),
                resultSet.getString("status"),
                resultSet.getDate("tanggal"),
                resultSet.getDouble("saldo"),
                resultSet.getString("namapemilik"),
                resultSet.getString("namakota"),
                resultSet.getString("namaprovinsi"));
    }

}
