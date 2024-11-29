package com.example.datas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdukTerjualData {
    private String nohp;
    private String namaumk;
    private String idproduk;
    private int kuantitas;
}
