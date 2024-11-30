package com.example.dataViews;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PenjualanDataAdminView {
    private String namaUMK;
    private int jumlahTransaksi;
    private String totalTransaksi;
}
