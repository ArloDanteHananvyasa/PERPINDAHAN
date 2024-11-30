package com.example.dataViews;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdukDataView {
    private String namaProduk;
    private String deskripsi;
    private String foto;
    private String satuan;
    private String harga;
}
