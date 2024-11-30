package com.example.repositories;

import java.sql.Date;
import java.util.List;

import com.example.dataViews.KeuanganDataView;
import com.example.dataViews.PenjualanDataUMKView;
import com.example.datas.KeuanganData;
import com.example.datas.PenjualanDataUMK;
import com.example.datas.ProdukData;

public interface umkRepository {
    List<ProdukData> findProduk(String umk);

    List<ProdukData> findProduk(String umk, String filter);

    List<PenjualanDataUMK> findPenjualan(String umk, Date start, Date end);

    List<KeuanganData> findKeuangan(String umk, Date start, Date end);

    List<PenjualanDataUMKView> findPenjualanView(String umk, Date start, Date end);

    List<KeuanganDataView> findKeuanganView(String umk, Date start, Date end);
}