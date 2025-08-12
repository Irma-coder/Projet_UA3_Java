## Remplacement des valeurs manquantes — Madeleine

## Objectifs
Ne pas modifier l’en-tête (ligne 0).
Identifier et remplacer toutes les cellules nulles ou vides (après trim()).
Utiliser un jeton standard "NA" comme valeur par défaut.
Préserver les autres données inchangées.
Traiter toutes les colonnes, quel que soit leur type.

## Détails d’implémentation
Portée : le traitement commence à partir de la ligne 1 (les données), en laissant l’en-tête intact.
Détection des valeurs manquantes :
    null → "NA".
    Chaîne vide ("") → "NA".
    Chaîne composée uniquement d’espaces (" ") → "NA".

## Préservation :
Toute valeur non manquante est conservée telle quelle (aucune transformation du texte ou des nombres).

## Modification en place :
Le tableau String[][] passé en paramètre est modifié directement.

## Complexité :
Temps : O(n × m) pour n lignes et m colonnes.
Mémoire : O(1).

## Exemples AVANT → APRÈS
| Ligne | Entrée (CSV)            | Sortie après traitement |
| ----- | ----------------------- | ----------------------- |
| 0     | `id,nom,age`            | `id,nom,age`            |
| 1     | `1,Alice,23`            | `1,Alice,23`            |
| 2     | `2,,`                   | `2,NA,NA`               |
| 3     | `3,   ,45`              | `3,NA,45`               |
| 4     | `4,null,` *(null Java)* | `4,NA,NA`               |



## Tests rapides (présentation)
En-tête inchangée : la ligne 0 reste identique.
Valeurs nulles et vides remplacées : toutes les occurrences sont converties en "NA".
Pas de décalage : aucune valeur ne change de colonne.
Largeur constante : toutes les lignes conservent le même nombre de colonnes.

## Limites & choix
Jeton fixe "NA" : choisi pour uniformiser la sortie (paramétrable si besoin).
Pas de traitement spécifique pour les valeurs numériques ou textuelles : la transformation se limite au remplacement des valeurs manquantes.

