# Projet CSI 2532 Partie II

## Langages utilisés

    Deux langages sont utilisés pour réaliser l'application. L'application elle même est une application Scala. Cet un langage similaire au Java qui tourne sur le JVM mais ajoute plusieurs composantes fonctionelles. Pour la base de donnée, on a utilisé le PostgreSQL.

## Guide d'installation

### Prérequis(version temporaire pour le développement de l'application)

- Scala Build Tool (sbt)
- Scala 2.18+
- JDK 8+

Vous pouvez trouver les détails [ici](https://scalameta.org/metals/docs/editors/vscode)

Cette extension de Visual Studio Code est utile pour lancer l'application.

Pour le driver JBDC, vous pouvez télécharger un `.jar` [ici](https://jdbc.postgresql.org/download.html). Veuillez utiliser la version 4.2 la plus récente.

### Installation

Clonez le repositore. Lorsque vous ouvriez le directoire, l'extension devrait vouz demander de "Import build". Ceci créera plusieurs directoire et fichier nécessaire pour lancer, debogger et compiler l'application. Ajouter l'endroit où est sauvegardé le `.jar` du driver JDBC sur votre système à `"classpath"` de `.bloop/app/app.json`.

Dans la ligne de commande, créer une base de donnée `appdb` sur le localhost de psql. Modifier le user et mot de passe dans `Database.scala` (je le changera peut-être à des ressources XML pour éviter des conflits) comme il le faut pour connecter sur votre machine (ceci est temporaire pour créer l'application. On peut migrer à une base de donnée permanente lorsqu'on aura fini l'application).

Éxecutez `schema.sql` et `seed.sql` dans la ligne de commande psql avec `\i`. Vous pouvez maintenant lancer `Main.scala`.
