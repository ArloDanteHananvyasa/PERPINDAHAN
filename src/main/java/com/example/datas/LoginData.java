package com.example.datas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginData {
    private String NoHp;
    private String Pass;
    private String role;
}
