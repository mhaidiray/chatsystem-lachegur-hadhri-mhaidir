Bienvenue dans notre répértoire d'application ChatSystem. Vous trouverez ici des liens vers le SDD, le SRS, les diagrammes PSM et également la Javadoc du projet.

Trinôme : Lachegur Samih, Hahdri Salim, Mhaidir Ayoub.

Le principe de cette application est de permettre à des utilisateurs sur un réseau local de communiquer entre eux et de s'envoyer des fichiers. Chaque utilisateur se connecte sur son ordinateur, et peut alors communiquer avec d'autres utilisateurs connectés.

NOTE : Le projet a été développé sur NetBeans. En conséquence, il est préférable de faire le checkout avec NetBeans. Vous pouvez également faire le checkout avec eclipse, récupérer les fichiers source et les copier/coller dans un nouveau projet.


---

<h3>Projet, Javadoc et éxecutable</h3>

---


Afin de tester notre projet, il suffit de faire un checkout du lien suivant :

https://chatsystem-lachegur-hadhri-mhaidir.googlecode.com/svn/trunk/

Si vous préférez une version .jar exécutable directement, en voici une :

https://drive.google.com/folderview?id=0B3tSQf_FdWyvcmFyTUFfSGtxalE&usp=sharing

Voici un lien vers la Javadoc de notre projet :

http://etud.insa-toulouse.fr/~lachegur/javadoc/


---

<h3>SRS, SDD et PSM</h3>

---


Voici le SRS lié à notre projet : https://docs.google.com/document/d/1A7MH2qEWongEexx1iFWcKGw6e2RD79pvFSXytXJ2kcg/edit?usp=sharing

Voici le SDD lié à notre projet : https://docs.google.com/document/d/1-7eyibwtxfSkdkBPGdMBzk8p9e_zR-AWKXU463gHJOs/edit?usp=sharing

Voici notre diagramme PSM : Il est en trois parties, un PSM général avec toutes les méthodes/attributs, un PSM plus abrégé portant sur la partie GUI, et un PSM abrégé portant sur le NI.

https://docs.google.com/document/d/1S4JG2dIGl3c9D_6xbl4YqTIZ0VbWiKPgBsMhtaqxHj8/edit?usp=sharing


---

<h3>Tutoriel</h3>

---


Lors du démarrage de l'application, la fenêtre d'accueil s'affiche.

<img><img src='http://img15.hostingpics.net/pics/767292accueil.png' /></img>

## Connexion ##

Vous pouvez ici choisir un nickname et vous connecter. Une fois ceci fait, voici la nouvelle fenêtre :

<img><img src='http://img15.hostingpics.net/pics/778342principale.png' /></img>

Tout à droite se trouve la liste des utilisateurs. Vous êtes présent dans cette liste, cela vous permet de vous envoyer des messages à vous-mêmes afin de tester le programme.

## Communication ##

Pour communiquer, il suffit de choisir un utilisateur dans la liste, d'écrire quelque chose dans la zone de texte tout en bas et d'appuyer sur Send (ou alors utiliser la touche Enter). Le message s'affichera alors dans la zone d'historique (au milieu de l'interface). Les messages s'affichent avec leur heure d'arrivée.

Chaque utilisateur possède sa propre zone d'historique, pour afficher celle d'un utilisateur particulier il vous suffit de cliquer sur son nom dans la liste.

Si jamais un utilisateur vous envoie un message et que vous n'êtes pas sur sa page d'historique, son nom dans la liste va se colorer en vert, et afficher le nombre de messages non lus.

<img><img src='http://img4.hostingpics.net/pics/800702Capturedu20141219121200.png' /></img>

> Cette notification disparaît quand vous affichez l'historique lié à la personne en question.

<img><img src='http://img4.hostingpics.net/pics/873664Capturedu20141219121235.png' /></img>

## Envoi de fichier ##

Pour envoyer un fichier, il suffit de sélectionner l'utilisateur et d'appuyer sur Add File. Une fenêtre va s'ouvrir et vous proposer de sélectionner le fichier à envoyer.

Lorsque vous recevez un fichier, le fichier en question est sauvegardé dans le répertoire source du projet. Attention, lorsque vous lancez le projet depuis un JAR directement, il est possible qu'il y ait des bugs d'autorisation dans l'écriture du nouveau fichier. Il est préférable de lancer le projet depuis NetBeans ou Eclipse pour que la réception de fichier se fasse correctement.

## Déconnexion ##

Pour vous déconnecter, il suffit de cliquer sur Disconnect, ce qui vous renvoie à l'écran de connexion. Vous pouvez également quitter en cliquant sur la croix, la procédure de déconnexion s'effectue correctement quand même.