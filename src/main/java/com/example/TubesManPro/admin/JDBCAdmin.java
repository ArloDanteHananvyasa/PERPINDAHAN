package com.example.TubesManPro.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.datas.PenjualanDataAdmin;
import com.example.datas.ProdukTerjualData;
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
    public UMKData findByNoHp(String hp) {
        return jdbc.queryForObject(
                "SELECT * FROM umk WHERE nohp = ?",
                this::mapRowToUmkData,
                hp);
    }

    @Override
    public List<UMKData> findVerif() {
        List<UMKData> umk = jdbc.query(
                "SELECT * FROM umk WHERE status = 'Belum Diverifikasi' ORDER BY IDPendaftaran",
                this::mapRowToUmkData);
        return umk;
    }

    @Override
    public List<ProdukTerjualData> findTerlaku() {
        List<ProdukTerjualData> terlaku = jdbc.query(
                "SELECT UMK.NoHp, UMK.NamaUMK, Produk.Nama as IdProduk, himpProdukLaku.Kuantitas as Kuantitas FROM (SELECT IdProduk, SUM(Kuantitas) as Kuantitas FROM Nota GROUP BY IdProduk ORDER BY SUM(Kuantitas) DESC LIMIT 3) as himpProdukLaku INNER JOIN Produk ON Produk.IdProduk = himpProdukLaku.IdProduk INNER JOIN ProdukUMK ON ProdukUMK.IdProduk = himpProdukLaku.IdProduk INNER JOIN UMK ON UMK.NoHp = ProdukUMK.NoHpUMK ORDER BY himpProdukLaku.Kuantitas DESC;",
                this::mapRowToProdukData);

        return terlaku;
    }

    @Override
    public List<PenjualanDataAdmin> findPenjualan() {
        List<PenjualanDataAdmin> penjualan = jdbc.query(
                "SELECT umk.namaumk, COUNT(idtransaksi) AS \"Jumlah Transaksi\", SUM(nominal::numeric) AS \"Total Transaksi\" FROM transaksi INNER JOIN jenistransaksi ON jenistransaksi.idjenis = transaksi.idjenis INNER JOIN umk ON transaksi.nohpumk = umk.nohp WHERE jenistransaksi.jenis ILIKE 'Penjualan Produk' GROUP BY umk.namaumk ORDER BY COUNT(idtransaksi) DESC;",
                this::mapRowToPenjualanData);

        return penjualan;
    }

    @Override
    public List<PenjualanDataAdmin> findPenjualan(int filter) {
        List<PenjualanDataAdmin> penjualan = jdbc.query(
                "SELECT umk.namaumk, COUNT(idtransaksi) AS \"Jumlah Transaksi\", SUM(nominal::numeric) AS \"Total Transaksi\" FROM transaksi INNER JOIN jenistransaksi ON jenistransaksi.idjenis = transaksi.idjenis INNER JOIN umk ON transaksi.nohpumk = umk.nohp WHERE jenistransaksi.jenis ILIKE 'Penjualan Produk' GROUP BY umk.namaumk ORDER BY COUNT(idtransaksi) DESC LIMIT ?;",
                this::mapRowToPenjualanData, filter);

        return penjualan;
    }

    @Override
    public void verifUMK(int pilihan, int idPendaftaran, String noHp) {
        if (pilihan == 1) {
            // Accept UMK (validating it)
            String queryValidasiUMK = "UPDATE UMK SET Status = 'Valid' WHERE IdPendaftaran = " + idPendaftaran;
            jdbc.update(queryValidasiUMK);

            String queryValidasiPendaftaran = "UPDATE Pendaftaran SET Status = 'Valid', NoHpAdmin = '" + noHp
                    + "' WHERE IdPendaftaran = " + idPendaftaran;
            jdbc.update(queryValidasiPendaftaran);

        } else if (pilihan == 2) {
            // Reject UMK
            String queryTolakUMK = "UPDATE UMK SET Status = 'Ditolak' WHERE IdPendaftaran = " + idPendaftaran;
            jdbc.update(queryTolakUMK);

            String queryTolakPendaftaran = "UPDATE Pendaftaran SET Status = 'Ditolak', NoHpAdmin = '" + noHp
                    + "' WHERE IdPendaftaran = " + idPendaftaran;
            jdbc.update(queryTolakPendaftaran);

        }
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

    private ProdukTerjualData mapRowToProdukData(ResultSet resultSet, int rowNum) throws SQLException {
        return new ProdukTerjualData(
                resultSet.getString("NoHp"), // Exact alias from query
                resultSet.getString("NamaUMK"), // Exact alias from query
                resultSet.getString("IdProduk"), // Exact alias from query
                resultSet.getInt("Kuantitas")); // Exact alias from query
    }

    private PenjualanDataAdmin mapRowToPenjualanData(ResultSet resultSet, int rowNum) throws SQLException {
        return new PenjualanDataAdmin(
                resultSet.getString("NamaUMK"), // Exact alias from query
                resultSet.getInt("Jumlah Transaksi"), // Exact alias from query
                resultSet.getDouble("Total Transaksi")); // Exact alias from query

    }

}
