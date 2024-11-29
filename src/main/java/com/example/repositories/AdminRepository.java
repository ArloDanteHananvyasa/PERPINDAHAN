package com.example.repositories;

import java.util.List;

import com.example.datas.PenjualanDataAdmin;
import com.example.datas.ProdukTerjualData;
import com.example.datas.UMKData;

public interface AdminRepository {
    List<UMKData> findAll();

    List<UMKData> findVerif();

    List<ProdukTerjualData> findTerlaku();

    List<PenjualanDataAdmin> findPenjualan();

    List<PenjualanDataAdmin> findPenjualan(int filter);

    UMKData findByNoHp(String hp);

    void verifUMK(int pilihan, int idPendaftaran, String NoHp);
}