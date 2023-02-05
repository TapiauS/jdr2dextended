--Liste des personnage de chaque compte

CREATE VIEW perso_compte(pseudo_compte,id_compte,liste_personnage) AS 
    SELECT pseudo_compte,compte_utilisateur.id_compte_utilisateur,nom_personnage 
        FROM personnage
        JOIN compte_utilisateur ON compte_utilisateur.id_compte_utilisateur=personnage.id_compte_utilisateur ;

--inventaire/equipement des personnage

CREATE VIEW possession(nom_perso,id_perso,nom_objet_possede,nom_objet_equipe) AS
    SELECT nom_personnage,personnage.id_personnage,o1.nom_objet,o2.nom_objet
        FROM personnage
        JOIN possede ON personnage.id_personnage=possede.id_personnage 
        JOIN objet AS o1 ON o1.id_personnage_possede=personnage.id_personnage
        JOIN objet AS o2 ON o2.id_personnage_equipe=personnage.id_personnage;

--journal de quêtes

CREATE VIEW journal_quete(id_quete,intervenant,role,objectifs,description_objectif,validation,objet,quantite) AS
    SELECT interaction.id_interaction,nom_personnage,nom_role_interaction,nom_objectif,description_objectif,nom_objet,quantite,validation_
        FROM interaction
        JOIN joue_un_role ON interaction.id_interaction=joue_un_role.id_interaction 
        JOIN personnage ON personnage.id_personnage=joue_un_role.id_personnage
        JOIN role_interaction ON joue_un_role.code_role_interaction=role_interaction.code_role_interaction 
        JOIN declenche ON declenche.id_interaction=interaction.id_interaction
        JOIN objectif ON objectif.id_objectif=declenche.id_objectif
        JOIN recompense ON recompense.id_recompense=declenche.id_recompense
        JOIN accorde ON recompense.id_recompense=accorde.id_recompense
        JOIN objet ON accorde.id_objet=objet.id_objet;

-- View regroupant les choix de dialogues

CREATE VIEW option_dialogues(dialogue_initiateur,options) AS
    SELECT contenu_dialogue,choix 
        FROM dialogue 
        JOIN precede ON precede.id_dialogue=precede.id_dialogue
        JOIN embranchement ON embranchement.id_embranchement=precede.id_embranchement;

--View donnant les coffres et leurs contenu

CREATE VIEW coffres(id_coffre,nom_coffre,objet_contenu) AS
    SELECT o1.id_objet,o1.nom_objet,o2.nom_objet
    FROM objet AS o1
    JOIN contient ON o1.id_objet=contient.contenant
    JOIN objet AS o2 ON o2.id_objet=contient.id_objet_contenu;

--View donnant la carte et la postion de chaque personnage

CREATE VIEW map(nom_perso,coordonnee,map) AS
    SELECT nom_personnage,personnage.coordonnee,carte_lieu
    FROM personnage
    JOIN lieu ON personnage.id_lieu=lieu.id_lieu;

--liste de PNJS avec leur localisation

CREATE VIEW pnj(nom,position) AS
    SELECT nom_personnage,personnage.coordonnee
    FROM personnage WHERE personnage.id_compte_utilisateur IS NULL;


CREATE OR REPLACE VIEW distperso(persoref,persocomp,diff) AS
    SELECT t0.id_personnage AS id1,t1.id_personnage AS id2,(SELECT dist(t0.id_personnage,t1.id_personnage,'personnage','personnage')) AS dist FROM personnage as t0
    JOIN personnage AS t1 ON t0.id_lieu=t1.id_lieu
    WHERE (SELECT dist(t0.id_personnage,t1.id_personnage,'personnage'))>0;

CREATE OR REPLACE VIEW distobjet(persoref,objet,diff) AS
    SELECT id_personnage,id_objet,(SELECT dist(id_personnage,id_objet,'personnage','objet')) AS dist FROM personnage
    JOIN objet ON personnage.id_lieu=objet.id_lieu
    WHERE (SELECT dist(id_personnage,id_objet,'personnage','objet'))>0;

--fiche d'un personnage

CREATE OR REPLACE VIEW fichperso(id_personnage,nom_personnage,id_lieu,id_compte_utilisateur,x,y,pV,pVmax,deg,redudeg) AS
    SELECT personnage.id_personnage,nom_personnage,id_lieu,id_compte_utilisateur,x,y,p0.valeur AS pV,p1.valeur AS pVmax,p2.valeur AS deg,p3.valeur AS redudeg FROM personnage 
        LEFT JOIN caracterise AS p0 ON p0.id_personnage=personnage.id_personnage AND p0.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='pV')
        LEFT JOIN caracterise AS p1 ON p1.id_personnage=personnage.id_personnage AND p1.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='pVmax')
        LEFT JOIN caracterise AS p2 ON p2.id_personnage=personnage.id_personnage AND p1.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='deg')
        LEFT JOIN caracterise AS p3 ON p3.id_personnage=personnage.id_personnage AND p1.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='redudeg');

--fiche d'un objet

CREATE OR REPLACE VIEW fichobjet(id_objet,nom_objet,description_objet,id_personnage_possede,id_personnage_equipe,contenant,nom_type_objet,id_lieu,poid,x,y,deg,redudeg,nbrmain,pv,pvmax,emplacement,duree) AS
    SELECT objet.id_objet,nom_objet,description_objet,id_personnage_possede,id_personnage_equipe,contenant,nom_type_objet,id_lieu,poid,x,y,p0.valeur AS deg,p1.valeur AS redudeg,p2.valeur AS nbrmain,p3.valeur AS pv,p4.valeur AS pvmax,emplacement,p5.valeur AS duree FROM objet
        LEFT JOIN type_objet ON objet.id_type_objet=type_objet.id_type_objet 
        LEFT JOIN affecte AS p0 ON p0.id_objet=objet.id_objet AND p0.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='deg')
        LEFT JOIN affecte AS p1 ON p1.id_objet=objet.id_objet AND p1.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='redudeg') 
        LEFT JOIN affecte AS p2 ON p2.id_objet=objet.id_objet AND p2.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='nbrmain') 
        LEFT JOIN affecte AS p3 ON p3.id_objet=objet.id_objet AND p3.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='pv')
        LEFT JOIN affecte AS p4 ON p4.id_objet=objet.id_objet AND p4.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='pvmax')
        LEFT JOIN affecte AS p5 ON p5.id_objet=objet.id_objet AND p5.id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='duree');


