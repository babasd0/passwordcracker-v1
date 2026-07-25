# PasswordCracker v1

Outil en ligne de commande permettant de retrouver un mot de passe à partir de son hash MD5, en utilisant le patron de conception **Simple Factory**.

## 1. Introduction

Dans le cadre d'un audit de sécurité, il est parfois nécessaire de vérifier la robustesse des mots de passe stockés sous forme de hash. PasswordCracker v1 est une première version d'un tel outil, développée en Java, permettant de retrouver un mot de passe MD5 via deux stratégies : dictionnaire et force brute.

## 2. Présentation du problème

Les mots de passe sont généralement stockés sous forme de hash (ici MD5) plutôt qu'en clair. L'objectif est de développer un outil capable de retrouver le mot de passe original à partir de ce hash, selon deux approches :
- **DICO** : recherche dans une liste de mots prédéfinie.
- **BRUTE** : génération exhaustive de toutes les combinaisons possibles (alphabet a-z, longueur max 4).

## 3. Architecture

Le projet repose sur une interface commune `HashCracker`, implémentée par deux stratégies concrètes (`DictionaryHashCracker` et `BruteForceHashCracker`), instanciées exclusivement via une fabrique centralisée (`HashCrackerFactory`). Un utilitaire `Md5Utils` centralise le calcul de hash afin d'éviter toute duplication de code entre les stratégies.

### Responsabilités des classes

| Classe | Responsabilité |
|---|---|
| `HashCracker` | Interface définissant le contrat `crack(hash): String` |
| `DictionaryHashCracker` | Recherche du mot de passe dans un dictionnaire |
| `BruteForceHashCracker` | Génération et test exhaustif de toutes les combinaisons |
| `HashCrackerFactory` | Création centralisée des instances de `HashCracker` |
| `Md5Utils` | Calcul du hash MD5 d'une chaîne de caractères |
| `Main` | Point d'entrée : parsing des arguments et affichage des résultats |

## 4. Diagramme UML
<img width="441" height="684" alt="Diagramme Projet" src="https://github.com/user-attachments/assets/af1430e8-8527-48a4-af55-8d25b1405ee5" />

## 5. Usage du patron Simple Factory

La création des objets `HashCracker` est entièrement centralisée dans `HashCrackerFactory.create(method)`. Le programme principal ne connaît jamais les classes concrètes (`DictionaryHashCracker`, `BruteForceHashCracker`) : il manipule uniquement l'interface `HashCracker`, obtenue via la fabrique. Cela découple la logique métier de l'instanciation et facilite la maintenance.

## 6. Résultats obtenus

| Méthode | Hash testé | Mot attendu | Résultat | Temps d'exécution |
|---|---|---|---|---|
| DICO | `098f6bcd4621d373cade4e832627b4f6` | test | Password found: test | 132 ms |
| DICO | `21232f297a57a5a743894a0e4a801fc3` | admin | Password found: admin | 21 ms |
| DICO | `ab4f63f9ac65152575886860dde480a1` | azerty | Password found: azerty | 22 ms |
| BRUTE | `098f6bcd4621d373cade4e832627b4f6` | test | Password found: test | 857 ms |
| BRUTE | `e2fc714c4727ee9395f324cd2e7f331f` | abcd | Password found: abcd | 158 ms |
| BRUTE | `21232f297a57a5a743894a0e4a801fc3` | admin (5 lettres) | Password not found (attendu : hors limite de 4 caractères) | 952 ms |
| DICO | `00000000000000000000000000000000` | (hash inexistant) | Password not found (attendu) | 21 ms |
| BRUTE | `00000000000000000000000000000000` | (hash inexistant) | Password not found (attendu) | 1044 ms |

Ces tests valident les deux stratégies sur des cas de succès (mots présents dans le dictionnaire, mots courts en brute force) et des cas d'échec attendus (mot trop long pour la limite de force brute, hash ne correspondant à rien).

**Vidéo de démonstration :** [lien à insérer]

## 7. Difficultés rencontrées

- Éviter la duplication de code entre les deux stratégies pour le calcul du hash MD5 → résolu via la classe utilitaire `Md5Utils`.
- Générer efficacement toutes les combinaisons pour la force brute jusqu'à 4 caractères sans explosion du temps d'exécution → utilisation d'une génération récursive avec arrêt dès qu'une correspondance est trouvée.

## 8. Conclusion

Ce mini-projet a permis de mettre en pratique le patron Simple Factory pour centraliser la création d'objets, tout en respectant le principe de responsabilité unique entre les différentes classes. La limite principale de cette approche (modification de la fabrique nécessaire pour chaque nouvelle stratégie) sera abordée dans le mini-projet suivant.

## Questions de réflexion

**1. Quels avantages apporte la fabrique simple ?**
Elle centralise la logique de création des objets, masque les détails d'instanciation au code appelant, et simplifie l'ajout de nouvelles stratégies du point de vue de l'utilisateur de la fabrique.

**2. Quels sont ses inconvénients ?**
Elle viole le principe Open/Closed : toute nouvelle stratégie nécessite de modifier le code existant de la fabrique (ajout d'un `case`). Elle centralise aussi une connaissance de toutes les classes concrètes, ce qui peut la rendre volumineuse si le nombre de stratégies augmente.

**3. Que faut-il modifier lorsqu'une nouvelle stratégie est ajoutée ?**
Il faut créer la nouvelle classe implémentant `HashCracker`, puis modifier la méthode `create()` de `HashCrackerFactory` pour y ajouter un nouveau cas correspondant.

**4. La fabrique respecte-t-elle le principe Open/Closed ?**
Non. Le principe Open/Closed stipule qu'une classe doit être ouverte à l'extension mais fermée à la modification. Or ici, ajouter une stratégie oblige à modifier directement le corps de `HashCrackerFactory.create()`, ce qui viole ce principe.

## Compilation et exécution

```bash
javac -d target/classes src/main/java/com/passwordcracker/*.java
java -cp target/classes com.passwordcracker.Main -m DICO -h <hashMD5>
java -cp target/classes com.passwordcracker.Main -m BRUTE -h <hashMD5>
```
**Vidéo de démonstration :** [Voir la vidéo de démonstration](https://youtu.be/LaVz1fPPl9c)