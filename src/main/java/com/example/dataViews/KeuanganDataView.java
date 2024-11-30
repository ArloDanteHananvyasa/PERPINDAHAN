package com.example.dataViews;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeuanganDataView {
    private String jenis;
    private String nominal;
    private Date tanggal;
    private boolean isPositive;
}
