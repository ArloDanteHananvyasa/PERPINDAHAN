package com.example.TubesManPro.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.datas.PenjualanData;
import com.example.datas.ProdukData;
import com.example.datas.UMKData;
import com.example.repositories.AdminRepository;

@Repository
public class JDBCAdmin implements AdminRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<UMKData> findAll() {
        List<UMKData> umk = jdbc.query(
                "SELECT * FROM umk WHERE status = 'Valid'",
                this::mapRowToUmkData);
        return umk;
    }

    @Override
    public List<UMKData> findVerif() {
        List<UMKData> umk = jdbc.query(
                "SELECT * FROM umk WHERE status = 'Belum Diverifikasi' ORDER BY IDPendaftaran",
                this::mapRowToUmkData);
        return umk;
    }

    @Override
    public List<ProdukData> findTerlaku() {
        List<ProdukData> terlaku = jdbc.query(
                "SELECT UMK.NoHp, UMK.NamaUMK, Produk.Nama as IdProduk, himpProdukLaku.Kuantitas as Kuantitas FROM (SELECT IdProduk, SUM(Kuantitas) as Kuantitas FROM Nota GROUP BY IdProduk ORDER BY SUM(Kuantitas) DESC LIMIT 3) as himpProdukLaku INNER JOIN Produk ON Produk.IdProduk = himpProdukLaku.IdProduk INNER JOIN ProdukUMK ON ProdukUMK.IdProduk = himpProdukLaku.IdProduk INNER JOIN UMK ON UMK.NoHp = ProdukUMK.NoHpUMK ORDER BY himpProdukLaku.Kuantitas DESC;",
                this::mapRowToProdukData);

        return terlaku;
    }

    @Override
    public List<PenjualanData> findPenjualan() {
        List<PenjualanData> penjualan = jdbc.query(
                "SELECT umk.namaumk, COUNT(idtransaksi) AS \"Jumlah Transaksi\", SUM(nominal::numeric) AS \"Total Transaksi\" FROM transaksi INNER JOIN jenistransaksi ON jenistransaksi.idjenis = transaksi.idjenis INNER JOIN umk ON transaksi.nohpumk = umk.nohp WHERE jenistransaksi.jenis ILIKE 'Penjualan Produk' GROUP BY umk.namaumk ORDER BY COUNT(idtransaksi) DESC;",
                this::mapRowToPenjualanData);

        return penjualan;
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
                resultSet.getString("email"));
    }

    private ProdukData mapRowToProdukData(ResultSet resultSet, int rowNum) throws SQLException {
        return new ProdukData(
                resultSet.getString("NoHp"), // Exact alias from query
                resultSet.getString("NamaUMK"), // Exact alias from query
                resultSet.getString("IdProduk"), // Exact alias from query
                resultSet.getInt("Kuantitas")); // Exact alias from query
    }

    private PenjualanData mapRowToPenjualanData(ResultSet resultSet, int rowNum) throws SQLException {
        return new PenjualanData(
                resultSet.getString("NamaUMK"), // Exact alias from query
                resultSet.getInt("Jumlah Transaksi"), // Exact alias from query
                resultSet.getDouble("Total Transaksi")); // Exact alias from query

    }

}
