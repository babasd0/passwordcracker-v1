package com.passwordcracker;

/**
 * Point d'entrée de l'application passwordCracker.
 * Usage :
 *   passwordCracker -m BRUTE -h <hashMD5>
 *   passwordCracker -m DICO  -h <hashMD5>
 */
public class Main {

    public static void main(String[] args) {
        String method = null;
        String hash = null;

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-m")) {
                method = args[i + 1];
            } else if (args[i].equals("-h")) {
                hash = args[i + 1];
            }
        }

        if (method == null || hash == null) {
            System.out.println("Usage: passwordCracker -m BRUTE|DICO -h <hashMD5>");
            return;
        }

        try {
            HashCracker cracker = HashCrackerFactory.create(method);

            long start = System.currentTimeMillis();
            String result = cracker.crack(hash);
            long elapsed = System.currentTimeMillis() - start;

            if (result != null) {
                System.out.println("Password found: " + result);
            } else {
                System.out.println("Password not found");
            }
            System.out.println("Temps d'exécution : " + elapsed + " ms");

        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
