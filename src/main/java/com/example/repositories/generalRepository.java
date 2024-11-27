package com.example.repositories;

public interface generalRepository {
    boolean login(String username, String password);

    String getSession();
}
