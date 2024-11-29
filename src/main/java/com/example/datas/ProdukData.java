package com.example.datas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdukData {
    private String namaProduk;
    private String deskripsi;
    private String foto;
    private String satuan;
    private double harga;
}
