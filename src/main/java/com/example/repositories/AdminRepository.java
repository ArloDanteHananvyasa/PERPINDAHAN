package com.example.repositories;

import java.util.List;

import com.example.datas.PenjualanData;
import com.example.datas.ProdukData;
import com.example.datas.UMKData;

public interface AdminRepository {
    List<UMKData> findAll();

    List<UMKData> findVerif();

    List<ProdukData> findTerlaku();

    List<PenjualanData> findPenjualan();
}