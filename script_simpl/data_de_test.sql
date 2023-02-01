--Ce document sera remplit avec les script insert servant a tester les classes DAO, a chaque nouveau test le script associé sera ajouté afin d'assurer la repetabilité des tests dans le temp, en attendant de savoir créer une fausse database pour tester plus efficacement

--compte utilisateur
INSERT INTO compte_utilisateur(pseudo_compte,couriel_compte,mdp_compte) VALUES ('Ahuizolte','simtapiau@live.fr','mdptest');

--rajout des maps via le code de jdr2d( écrire des tableau c'est fatiguant)

--ajout de personnage de test

INSERT INTO personnage(nom_personnage,x,y,id_lieu,id_compte_utilisateur) VALUES ('Virgile',0,0,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante'),(SELECT id_compte_utilisateur FROM compte_utilisateur WHERE nom_compte_utilisateur='Ahuizolte')),('Donatien',4,2,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante'),null);

--ajout d'objet

INSERT INTO objet(nom_objet,x,y,id_lieu) VALUES ('pistolet a silex',2,4,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante')),('baton de sorcier',1,3,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante'));
INSERT INTO objet(nom_objet) VALUES ('fusil hextec');

-- ajout de quete

INSERT INTO interaction(nom_interaction) VALUES ('tuer donatien');

INSERT INTO objectif(nom_objectif,id_objet,id_personnage,id_interaction) VALUES ('trouver pistolet',(SELECT id_objet FROM objet WHERE nom_objet='pistolet a silex'),NULL,(SELECT id_interaction FROM interaction WHERE nom_interaction='tuer donatien')),('trouver pistolet',NULL,(SELECT id_personnage FROM personnage WHERE nom_personnage='Donatien'),(SELECT id_interaction FROM interaction WHERE nom_interaction='tuer donatien'));

INSERT INTO queste(id_personnage,id_interaction) VALUES ((SELECT id_personnage FROM personnage WHERE nom_personnage='Virgile'),(SELECT id_interaction FROM interaction WHERE nom_interaction='tuer donatien'));

INSERT INTO accorde VALUES ((SELECT id_objet FROM objet WHERE nom_objet='fusil hextec'),(SELECT id_interaction FROM interaction WHERE nom_interaction='tuer donatien'));


INSERT INTO interaction (nom_interaction) VALUES ('trouver baton de mage');

INSERT INTO objectif(nom_objectif,id_objet,id_interaction) VALUES ('trouver baton',(SELECT id_objet FROM objet WHERE nom_objet='baton de sorcier'),(SELECT id_interaction FROM interaction WHERE nom_interaction='trouver baton de mage'));

INSERT INTO accorde VALUES ((SELECT id_objet FROM objet WHERE nom_objet='fusil hextec'),(SELECT id_interaction FROM interaction WHERE nom_interaction='trouver baton de mage'));

INSERT INTO queste(id_personnage,id_interaction) VALUES ((SELECT id_personnage FROM personnage WHERE nom_personnage='Virgile'),(SELECT id_interaction FROM interaction WHERE nom_interaction='trouver baton de mage'));


--ajout de porte


