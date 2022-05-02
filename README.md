# Projet CSI 2532 Partie II

## Langages utilisés

Deux langages sont utilisés pour réaliser l'application. L'application elle même est une application Scala. Cet un langage similaire au Java qui tourne sur le JVM mais ajoute plusieurs composantes fonctionelles. Pour la base de donnée, on a utilisé le PostgreSQL.

## DLL utilisés

Les librairies standards du JRE et le pilote JDBC pour PostgreSQL.

## Guide d'installation

### Prérequis(version temporaire pour le développement de l'application)

- [Scala Build Tool 1.6.2 (sbt)](https://www.scala-sbt.org/)
- [Scala 2.13+](https://www.scala-lang.org/download/)
- JDK 8+
- PostgreSQL

### Installation

`PATHtoAPP` représente dorénavant le directiore dans lequel se trouve `/app/`. Entrez dans la console PSQL et connectez vous aux serveur localhost défaut. Créez une base de données avec `CREATE DATABASE appdb`. Connectez vous à cette base de donées et entrez la commande `\i PATHtoAPP/src/main/sql/schema.sql` suivi de `\i PATHtoAPP/src/main/sql/seed.sql`.

Entrer dans la ligne de commande, naviguez à `app`. Utilisez la commande `sbt` pour ouvrir la console sbt. Par la suite, utilisez la commande `run com.example.Main [user] [pass]` où `[user]` est l'utilisateur local pour PostgreSQL et `[pass]` c'est le mot de passe. L'application devrait s'éxecuter.

Si vous avez des problèmes, envoyez un email à [emart133@uottawa.ca](mailto:emart133@uottawa.ca).
