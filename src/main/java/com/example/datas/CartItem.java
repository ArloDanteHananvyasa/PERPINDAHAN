package com.example.datas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private String namaProduk;
    private int jumlah;
    private double total;
    private String tanggal;
}
