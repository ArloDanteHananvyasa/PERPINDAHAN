package com.example.datas;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UMKData {
    private String hp;
    private String namaUMK;
    private String deskripsi;
    private String logo;
    private String alamat;
    private int idPendaftaran;
    private String status;
    private Date tanggal;
    private double saldo;
    private String namaPem;
    private String email;
}
