package com.example.datas;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransaksiData {
    private int idTransaksi;
    private double nominal;
    private int idJenis;
    private Date tanggal;
    private String nohpumk;
}
