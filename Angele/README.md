# Standardisation des données — Angèle
## Objectifs
- Ne pas modifier l’**entête** (ligne 0).
- **Détecter automatiquement** les colonnes numériques.
- Uniformiser les nombres au **format décimal point** (`518,625` → `518.625`).
- Standardiser le **texte** : `trim()` + **minuscules**.
- **Préserver** la donnée si ambiguë (ne pas forcer en numérique quand ce n’est pas sûr).
  
## Détails d’implémentation 
- **Détection colonnes numériques** : une colonne est considérée *numérique* si ≥ **50 %** des cellules (lignes 1..n) sont parseables en `BigDecimal` **après** normalisation (voir ci-dessous).
- **Normalisation “fr → point”** :  
  `1 234,56` → `1234.56`, `518,625` → `518.625` (suppression séparateurs de milliers + virgule → point).
- **Forme canonique** des nombres : pas de zéros inutiles, pas de notation scientifique (via `BigDecimal.stripTrailingZeros().toPlainString()`).
- **Texte** : `trim()` + `toLowerCase()` sur les colonnes non numériques.
- **Préservation** : si une valeur non numérique apparaît dans une colonne détectée numérique, on la **garde** (formatée minimalement) plutôt que la supprimer.

## Exemples AVANT → APRÈS
| Type de colonne | Entrée (CSV)   | Sortie standardisée |
|---|---|---|
| Numérique (fr)  | `"1 234,56"`   | `1234.56` |
| Numérique (fr)  | `"518,625"`    | `518.625` |
| Texte           | `"  MALE "`    | `male` |

## Tests rapides (présentation)
1. **Largeur constante** : le nombre de colonnes est identique pour l’entête et toutes les lignes.  
2. **Nombres FR** : on retrouve des `.` au lieu des `,` dans les colonnes numériques.  
3. **Texte** homogène : minuscules et espaces de bord supprimés.  
4. **Aucune “délocalisation”** de valeurs entre colonnes.

## Limites & choix
- Seuil **50 %** pour classer une colonne en numérique (simple, efficace pour ce projet).
- Standardisation texte en **minuscules** (paramétrable si requis).
- Les **valeurs manquantes** sont **déjà** traitées par l’étape précédente.

## Fichiers concernés
- **Ma contribution** : `src/Standardisation.java`  
-  **contribution commune **Intégration : `LireFichierCSV.java`,  `SauvegardeFichierCSV.java`


