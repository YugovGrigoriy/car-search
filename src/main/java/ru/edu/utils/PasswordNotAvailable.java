package ru.edu.utils;

// todo: переименовать класс
public class PasswordNotAvailable {
    public static void main(String[] args) {

        String name="grigoriy";
        String pass="12345678QWERTY";
        System.out.println(passwordCorrect(pass,name));
    }
// пароль :от 8 символов , не содержит username, не содержит последовательности, минимум 1 заглавная, 1 строчная и 1 цифра
    public static boolean passwordCorrect(String password, String username) {

        if (password.matches(".*abcdefghijklmnopqrstuvwxyz.*") || password.matches(".*0123456789.*")) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            return false;
        }
        return !password.matches(".*" + username + ".*") && !password.matches(".*" + new StringBuilder(username).reverse().toString() + ".*");
    }
}
