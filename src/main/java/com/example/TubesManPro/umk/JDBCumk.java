package com.example.TubesManPro.umk;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.datas.PenjualanDataUMK;
import com.example.datas.ProdukData;
import com.example.repositories.umkRepository;

@Repository
public class JDBCumk implements umkRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<ProdukData> findProduk(String umk) {

        List<ProdukData> produk = jdbc.query(
                "SELECT * FROM produk INNER JOIN produkumk ON produk.idproduk = produkumk.idproduk WHERE nohpumk = ?",
                this::mapRowToProdukData, umk);

        return produk;
    }

    @Override
    public List<ProdukData> findProduk(String umk, String filter) {

        List<ProdukData> produk = jdbc.query(
                "SELECT * FROM produk INNER JOIN produkumk ON produk.idproduk = produkumk.idproduk WHERE nohpumk = ? AND nama ILIKE ?",
                this::mapRowToProdukData, umk, "%" + filter + "%");

        return produk;
    }

    private ProdukData mapRowToProdukData(ResultSet resultSet, int rowNum) throws SQLException {
        return new ProdukData(
                resultSet.getString("nama"),
                resultSet.getString("deskripsi"),
                resultSet.getString("foto"),
                resultSet.getString("satuan"),
                resultSet.getDouble("harga"));
    }

    @Override
    public List<PenjualanDataUMK> findPenjualan(String umk, Date start, Date end) {
        List<PenjualanDataUMK> penjualan = jdbc.query(
                "SELECT DataProduk.IdProduk, DataProduk.Nama, DataProduk.\"Akumulasi Jumlah\", Produk.Harga, Produk.Harga * DataProduk.\"Akumulasi Jumlah\" AS \"Total\", DataProduk.Tanggal FROM (SELECT Produk.IdProduk, Produk.Nama, SUM(Nota.Kuantitas) AS \"Akumulasi Jumlah\", dataTransaksi.Tanggal FROM (SELECT * FROM Transaksi WHERE NoHpUMK = ? AND IdJenis = 2) AS dataTransaksi INNER JOIN Nota ON Nota.IdTransaksi = dataTransaksi.IdTransaksi INNER JOIN Produk ON Produk.IdProduk = Nota.IdProduk WHERE Tanggal >= ? AND Tanggal <= ? GROUP BY Produk.IdProduk, dataTransaksi.Tanggal, Produk.Nama) AS DataProduk INNER JOIN Produk ON Produk.IdProduk = DataProduk.IdProduk;",
                this::mapRowToPenjualanData, umk, start, end);

        return penjualan;
    }

    private PenjualanDataUMK mapRowToPenjualanData(ResultSet resultSet, int rowNum) throws SQLException {
        return new PenjualanDataUMK(
                resultSet.getString("nama"),
                resultSet.getInt("Akumulasi Jumlah"),
                resultSet.getDouble("harga"),
                resultSet.getInt("Akumulasi Jumlah") * resultSet.getDouble("harga"),
                resultSet.getDate("tanggal"));
    }

}
