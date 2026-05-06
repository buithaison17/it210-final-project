package com.example.it210finalproject.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BcryptPassword {
    public String bcrypt(String password) {
        return BCrypt.withDefaults().hashToString(8, password.toCharArray());
    }

    public boolean verify(String password, String hash) {
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified;
    }
}
