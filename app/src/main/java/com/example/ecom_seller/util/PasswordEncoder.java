package com.example.ecom_seller.util;
import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncoder {
    public static String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                return Base64.getEncoder().encodeToString(hash);
            }
            else
            {
                return android.util.Base64.encodeToString(hash, android.util.Base64.DEFAULT);
            }
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new RuntimeException("Could not encode password", ex);
        }
    }
    public static boolean checkPassword(String inputPassword, String hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(inputPassword.getBytes());
            String encodedHash = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                encodedHash = Base64.getEncoder().encodeToString(hash);
            }
            return encodedHash.equals(hashedPassword);
        } catch (NoSuchAlgorithmException ex) {
            // Xử lý lỗi
            return false;
        }
    }
}