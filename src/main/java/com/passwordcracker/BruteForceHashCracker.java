package com.passwordcracker;

/**
 * Stratégie de cassage par force brute.
 * Génère toutes les combinaisons possibles (a-z) jusqu'à une longueur
 * maximale de 4 caractères, et compare leur hash MD5 au hash recherché.
 */
public class BruteForceHashCracker implements HashCracker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;

    @Override
    public String crack(String hash) {
        for (int length = 1; length <= MAX_LENGTH; length++) {
            String result = tryLength(hash, new char[length], 0);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * Génère récursivement toutes les combinaisons de la longueur donnée
     * et teste leur hash à chaque fois qu'un mot complet est formé.
     */
    private String tryLength(String hash, char[] combination, int index) {
        if (index == combination.length) {
            String candidate = new String(combination);
            if (Md5Utils.md5(candidate).equals(hash)) {
                return candidate;
            }
            return null;
        }

        for (int i = 0; i < ALPHABET.length(); i++) {
            combination[index] = ALPHABET.charAt(i);
            String result = tryLength(hash, combination, index + 1);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
