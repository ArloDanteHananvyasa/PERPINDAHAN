package com.example.datas;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeuanganData {
    private String jenis;
    private double nominal;
    private Date tanggal;
}
