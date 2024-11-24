package com.example.repositories;

import java.util.List;

import com.example.datas.UMKData;

public interface AdminRepository {
    List<UMKData> findAll();

    List<UMKData> findVerif();

}