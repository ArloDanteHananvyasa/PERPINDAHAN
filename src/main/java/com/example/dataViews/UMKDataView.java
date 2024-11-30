package com.example.dataViews;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UMKDataView {
    private String hp;
    private String namaUMK;
    private String deskripsi;
    private String logo;
    private String alamat;
    private int idPendaftaran;
    private String status;
    private Date tanggal;
    private String saldo;
    private String namaPem;
    private String email;
}
