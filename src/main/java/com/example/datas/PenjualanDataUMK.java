package com.example.datas;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PenjualanDataUMK {
    private String namaProduk;
    private int jumlah;
    private double hargaSatuan;
    private double total;
    private Date tanggal;
}
