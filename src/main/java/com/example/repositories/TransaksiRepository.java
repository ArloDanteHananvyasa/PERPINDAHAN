package com.example.repositories;

import java.sql.Date;
import java.util.List;

import com.example.datas.CartItem;

public interface TransaksiRepository {
    void biayaOperasional(Date tanggal, double nominal, String nohp);

    void setorModal(Date tanggal, double nominal, String nohp);

    void tarikModal(Date tanggal, double nominal, String rekening, String nohp);

    void penjualanProduk(List<CartItem> cart, String nohp);
}