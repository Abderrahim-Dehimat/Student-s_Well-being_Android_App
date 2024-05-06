package com.example.happyuapp;

public class CustomAuthManager {
    private static String currentLoggedInEmail;

    public static void setCurrentLoggedInEmail(String email) {
        currentLoggedInEmail = email;
    }

    public static String getCurrentLoggedInEmail() {
        return currentLoggedInEmail;
    }
}
