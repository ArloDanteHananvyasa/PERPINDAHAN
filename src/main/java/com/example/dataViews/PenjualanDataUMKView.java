package com.example.dataViews;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PenjualanDataUMKView {
    private String namaProduk;
    private int jumlah;
    private String hargaSatuan;
    private String total;
    private Date tanggal;
}
