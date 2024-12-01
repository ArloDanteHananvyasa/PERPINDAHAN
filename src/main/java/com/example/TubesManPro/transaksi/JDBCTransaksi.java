package com.example.TubesManPro.transaksi;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.datas.CartItem;
import com.example.datas.ProdukData;
import com.example.datas.TransaksiData;
import com.example.datas.UMKData;
import com.example.repositories.TransaksiRepository;

@Repository
public class JDBCTransaksi implements TransaksiRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void setorModal(Date tanggal, double nominal, String nohp) {
        int idtransaksi = insertTransaksiAndGetId(1, nohp);

        updateTransaksi(nominal, tanggal, idtransaksi);

        updateSaldo(1, nominal, nohp);

    }

    @Override
    public void penjualanProduk(List<CartItem> cart, String nohp) {
        int idtransaksi = insertTransaksiAndGetId(2, nohp);

        double nominal = 0;

        for (CartItem c : cart) {
            nominal += c.getTotal();
            insertIntoNota(c, nohp, idtransaksi);
        }

        Date tanggal = Date.valueOf(LocalDate.parse(cart.get(0).getTanggal()));

        updateTransaksi(nominal, tanggal, idtransaksi);

        updateSaldo(2, nominal, nohp);
    }

    private void insertIntoNota(CartItem c, String nohp, int idTransaksi) {

        List<ProdukData> produk = jdbc.query("SELECT * from Produk where Nama = ?", this::mapRowToProdukData,
                c.getNamaProduk());

        int idProduk = produk.get(0).getIdProduk();

        jdbc.update("INSERT INTO Nota (idtransaksi, idproduk, kuantitas)values (?,?,?);", idTransaksi, idProduk,
                c.getJumlah());

    }

    @Override
    public void biayaOperasional(Date tanggal, double nominal, String nohp) {
        int idtransaksi = insertTransaksiAndGetId(3, nohp);

        updateTransaksi(nominal, tanggal, idtransaksi);

        updateSaldo(3, nominal, nohp);

    }

    @Override
    public void tarikModal(Date tanggal, double nominal, String rekening, String nohp) {
        int idtransaksi = insertTransaksiAndGetId(4, nohp);

        updateTransaksi(nominal, tanggal, idtransaksi);

        updateSaldo(4, nominal, nohp);

    }

    private int insertTransaksiAndGetId(int idjenis, String nohp) {
        jdbc.update("INSERT INTO transaksi (idjenis, nohpumk) VALUES (? , ?)", idjenis, nohp);

        List<TransaksiData> transaksi = jdbc.query("SELECT * from transaksi order by idtransaksi desc LIMIT 1",
                this::mapRowToTransaksiData);

        return transaksi.get(0).getIdTransaksi();
    }

    private void updateTransaksi(double nominal, Date tanggal, int idtransaksi) {
        jdbc.update("UPDATE Transaksi SET Nominal = ?, Tanggal = ?  WHERE IdTransaksi = ?", nominal, tanggal,
                idtransaksi);
    }

    private void updateSaldo(int idjenis, double nominal, String nohp) {
        double nominalSaldo = 0;
        if (idjenis == 3 || idjenis == 4) {
            nominal *= -1;
        }

        List<UMKData> user = jdbc.query("SELECT * FROM umk WHERE nohp = ?", this::mapRowToUmkData, nohp);

        nominalSaldo = user.get(0).getSaldo() + nominal;

        jdbc.update("UPDATE umk set saldo = ? WHERE nohp = ?", nominalSaldo, nohp);
    }

    private TransaksiData mapRowToTransaksiData(ResultSet resultSet, int rowNum) throws SQLException {
        return new TransaksiData(
                resultSet.getInt("idtransaksi"),
                resultSet.getDouble("nominal"),
                resultSet.getInt("idjenis"),
                resultSet.getDate("tanggal"),
                resultSet.getString("nohpumk"));
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

    private ProdukData mapRowToProdukData(ResultSet resultSet, int rowNum) throws SQLException {
        return new ProdukData(
                resultSet.getInt("idproduk"),
                resultSet.getString("nama"),
                resultSet.getString("deskripsi"),
                resultSet.getString("foto"),
                resultSet.getString("satuan"),
                resultSet.getDouble("harga"));
    }
}
