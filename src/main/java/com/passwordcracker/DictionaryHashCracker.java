package com.passwordcracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Stratégie de cassage par dictionnaire.
 * Charge une liste de mots et compare leur hash MD5 au hash recherché.
 */
public class DictionaryHashCracker implements HashCracker {

    private final String dictionaryPath;

    public DictionaryHashCracker(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    @Override
    public String crack(String hash) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                if (word.isEmpty()) {
                    continue;
                }
                if (Md5Utils.md5(word).equals(hash)) {
                    return word;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du dictionnaire : " + e.getMessage());
        }
        return null;
    }
}
