package com.passwordcracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitaire pour le calcul de hash MD5.
 * Centralise la logique de hachage afin d'éviter les duplications
 * entre les différentes stratégies de cassage.
 */
public final class Md5Utils {

    private Md5Utils() {
        // classe utilitaire : pas d'instanciation
    }

    public static String md5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithme MD5 non disponible", e);
        }
    }
}
