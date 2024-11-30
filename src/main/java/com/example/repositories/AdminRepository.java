package com.example.repositories;

import java.util.List;

import com.example.dataViews.UMKDataView;
import com.example.datas.PenjualanDataAdmin;
import com.example.datas.ProdukTerjualData;
import com.example.datas.UMKData;

public interface AdminRepository {
    List<UMKDataView> findAll();

    List<UMKDataView> findVerif();

    List<ProdukTerjualData> findTerlaku();

    List<ProdukTerjualData> findTerlaku(int filter);

    List<PenjualanDataAdmin> findPenjualan();

    List<PenjualanDataAdmin> findPenjualan(int filter);

    UMKData findByNoHp(String hp);

    UMKDataView findViewByNoHp(String hp);

    void verifUMK(int pilihan, int idPendaftaran, String NoHp);
}