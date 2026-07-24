package com.passwordcracker;

/**
 * Fabrique simple (Simple Factory) responsable de la création
 * des instances de HashCracker en fonction de la méthode choisie.
 * Centralise la création afin d'éviter toute instanciation directe
 * des classes concrètes dans le programme principal.
 */
public class HashCrackerFactory {

    private static final String DICTIONARY_PATH = "dictionary.txt";

    public static HashCracker create(String method) {
        switch (method.toUpperCase()) {
            case "DICO":
                return new DictionaryHashCracker(DICTIONARY_PATH);
            case "BRUTE":
                return new BruteForceHashCracker();
            default:
                throw new IllegalArgumentException("Méthode inconnue : " + method);
        }
    }
}
