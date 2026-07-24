package com.passwordcracker;

/**
 * Interface commune à toutes les stratégies de cassage de hash.
 * Chaque implémentation doit être capable de retrouver un mot de passe
 * à partir de son empreinte MD5.
 */
public interface HashCracker {

    /**
     * Tente de retrouver le mot de passe correspondant au hash donné.
     *
     * @param hash le hash MD5 recherché
     * @return le mot de passe trouvé, ou null si aucun résultat
     */
    String crack(String hash);
}
