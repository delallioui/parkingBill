Comment utiliser l'application
------
* générer je JAR avec la commande maven : clean install 
* lancer le JAR avec la commande java : java -jar <NOM_DU_JAR>
* une fois lancer quand le message "Veuillez saisir un input ou 'q' pour quitter" s'affiche, inserer des input.
    * Ex1 : * 1 voiture essence arrivée à 13h24 et repartie à 15h10
    * Ex2 : * 1 moto essence arrivée à 19h30 et repartie à 00h37
    * Ex3 : * 1 voiture GPL arrivée à  7h43 et repartie à 15h10
* L'application est robuste contre les inputs erroné.
    * Ex1 : xxxxxxxxxxx
    * Ex2 : 1 voiture essence  à 13h24 et repartie à 15h10
    * Ex3 : 1 voiture essence arrivée à 13h24 et  à 15h10
* inserer 'q' pour quitter l'application
