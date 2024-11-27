package com.example.datas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PenjualanData {
    private String namaUMK;
    private int jumlahTransaksi;
    private double totalTransaksi;
}
