package com.example.repositories;

import com.example.datas.LoginData;
import com.example.datas.UMKData;

public interface generalRepository {
    LoginData login(String username, String password);

    UMKData getUMK(String noHp);
}
