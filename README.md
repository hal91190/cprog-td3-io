# TP Entrées/Sorties et Persistance

Le projet sera géré avec [Maven](https://maven.apache.org/) et vous utiliserez une approche pilotée par les tests pour la réalisation, i.e. écrivez toujours un test avant le code et pensez au refactoring. Vous devrez respecter les conventions utilise Maven pour l’arborescence des répertoires et définir les coordonnées du projet. La version du compilateur et des sources java utilisée est la 17. Vous utiliserez la dernière version stable de JUnit 4 (cf. [MVNRepository](https://mvnrepository.com/)). Bien entendu, vous devez configurer le `.gitignore` adapté aux projets Maven (cf. [A collection of .gitignore templates](https://github.com/github/gitignore)) et ajoutez-y les fichiers et répertoires de votre IDE.
Vous rédigerez votre code source en respectant les [conventions de codage](https://google.github.io/styleguide/javaguide.html) du guide de stype _Google_.
Enfin, la prise en compte des contraintes se fera par de la gestion d’erreurs et des exceptions.

À chaque étape, validez vos modifications avec `git` et si nécessaire, exécutez le cycle maven adapté pour vérifier vos manipulations. Vous effectuerez des `commits` réguliers comportant des messages informatifs. Vous devez donner la possibilité de supprimer tous les fichiers issus de la compilation dans votre configuration Maven. Modifiez le POM pour intégrer la vérification des conventions de codage avec [`checkstyle`](http://maven.apache.org/plugins/maven-checkstyle-plugin/) en utilisant les conventions _Google_.

Vous devez créer un `jar` du projet autonomes avec possibilité de lancer les différents commandes demandées ci-dessous. Ajoutez une méthode `main` qui démontre quelques fonctionnalités de la classe, puis modifiez le POM pour que le jar généré soit exécutable (cf. [Apache Maven JAR Plugin](https://maven.apache.org/plugins/maven-jar-plugin/index.html)) Modifiez la méthode `main` pour que les différents affichages se fassent à l'aide de la bibliothèque de logging [`SLF4J`](http://www.slf4j.org/)
1. En utilisant le plugin [assembly](https://maven.apache.org/plugins/maven-assembly-plugin/) (ou le plugin [shade](https://maven.apache.org/plugins/maven-shade-plugin/)), générez une archive du projet contenant ses dépendances (uber-jar)


## Les Entrées/sorties et persistance

Le but de cette exercice est de réaliser quelques outils de manipulation de fichiers textes.

### Utilisation des flux de caractères

Écrire un programme prenant en argument un nom de fichier et affichant le nombre de lignes de ce fichier. 

Quelle classe du package `java.io` vous sera utile pour cette tâche ?
> RÉPONDRE ICI

Écrire un programme prenant en argument une chaîne de caractères et un nom de fichier et qui affiche les lignes (avec leur numéro) du fichier contenant la chaîne.

Quelle classe du package `java.io` vous sera utile pour cette tâche ?
> RÉPONDRE ICI

### Implémentation d’un flux de filtrage
Cette exercice consiste à implémenter une commande grep simplifiée. Parmi les nombreuses options de cette commande, vous implémenterez le sous-ensemble suivant (cf. [page de manuel](http://manpagesfr.free.fr/man/man1/grep.1.html)) : `--help, -e|--regexp, -f|--file, -i|--ignore-case, -n|--line-number`.

Vous utiliserez la bibliothèque:

 - [Apache Commons CLI](http://commons.apache.org/cli/) pour la manipulation des arguments de ligne de commande,

 - de manipulation d’expressions rationnelles du JDK (cf. [Tutoriel](http://docs.oracle.com/javase/tutorial/essential/regex/index.html)).

Questions:
    
 - Réalisez le flux de filtrage qui lit un flux en retournant uniquement les lignes correspondant au motif recherché en ajoutant une classe `GrepReader` et une classe `GrepReaderTest` dans les répertoires et packages appropriés. 

Énumérez une liste de cas de tests à réaliser en n'oubliant pas les cas d'erreur.
> RÉPONDRE ICI

Puis écrire chaque cas de test,
1. écrivez le test JUnit correspondant dans la classe de test,
1. vérifiez qu’il échoue,
1. implémentez la fonctionnalité dans la classe,
1. vérifiez que le test passe,
1. appliquez un étape de refactoring sur les tests et la classe si nécessaire.

 - Réaliser la classe principale `GrepApp`

Puis produire le jar avec toutes les dépendances,


