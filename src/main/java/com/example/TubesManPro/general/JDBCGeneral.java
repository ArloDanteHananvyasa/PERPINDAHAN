package com.example.TubesManPro.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.datas.LoginData;
import com.example.repositories.generalRepository;

@Repository
public class JDBCGeneral implements generalRepository {

    @Autowired
    private JdbcTemplate jdbc;

    private String session;

    @Override
    public boolean login(String noHp, String password) {
        String user = this.cekAdminOrUMK(noHp);
        List<LoginData> res = jdbc.query(
                "select * from " + user + " where NoHP = '" + noHp + "' AND Pass = '" + password + "'",
                this::mapRowToLoginData);

        if (!res.isEmpty()) {
            return true;
        }
        return false;
    }

    private String cekAdminOrUMK(String noHp) {
        // String queryCekDaftar = "select * from Administrator where NoHP = '" + noHp +
        // "'";
        // List<LoginData> res = jdbc.query(queryCekDaftar, this::mapRowToLoginData);

        // String login = "umk";
        // if (!res.isEmpty()) {
        // login = "administrator";
        // }

        // this.session = login;
        // return login;
        if (noHp.length() > 4) {
            return "umk";
        } else {
            return "administrator";
        }

        // CHANGE THIS LOGIC
    }

    private LoginData mapRowToLoginData(ResultSet resultSet, int rowNum) throws SQLException {
        return new LoginData(
                resultSet.getString("Nohp"),
                resultSet.getString("Pass"));
    }

    public String getSession() {
        return session;
    }

}
