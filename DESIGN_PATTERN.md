# Design Pattern - State Pattern

J'ai choisi le State Pattern (pattern comportemental) pour gérer le cycle de vie d'une réservation.

Une réservation peut être dans 3 états : CONFIRMED, CANCELLED ou COMPLETED.
Au lieu de mettre des if/else partout dans le code pour vérifier si une transition est possible, chaque état est une classe et du coup elle sait elle-même ce qu'elle a le droit de faire.

Par exemple, une réservation CONFIRMED peut être annulée ou complétée. Mais une réservation qui est déjà en CANCELLED ne peut plus rien faire, c'est un état final.

Ca permet d'avoir un code plus propre et si on veut ajouter un nouvel état plus tard (genre EN_ATTENTE), il suffit de créer une nouvelle classe sans toucher au reste du code.

Le code et les fichiers du pattern utilisé sont à cet endroit : reservation-service/pattern/state/.
