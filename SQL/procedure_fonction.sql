--fonction ajoutant un utilisateur

CREATE OR REPLACE PROCEDURE add_user(mail VARCHAR,nom VARCHAR,mdp VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO compte_utilisateur(couriel_compte,pseudo_compte,mdp_compte,valide) VALUES (mail,nom,mdp,'f');
END;
$$;

--fonction ajoutant un personnage pour un utilisateur

CREATE OR REPLACE PROCEDURE create_char(nom VARCHAR,nom_compte VARCHAR)
LANGUAGE plpgsql
AS $$
--script minimum de création de personnage,a completer quand on decide de ce qui definit un personnage au debut du jeu
BEGIN
INSERT INTO personnage(nom_personnage,id_compte_utilisateur,vivant) VALUES
(nom,(SELECT id_compte_utilisateur FROM compte_utilisateur WHERE pseudo_compte=nom_compte),'t');
END;
$$;

--fonction de suppression d'un personnage

CREATE OR REPLACE PROCEDURE suppr_char(nom VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
DELETE FROM personnage WHERE nom_personnage=nom;
END;
$$;

--fonction qui udapte le nom d'un personnage

CREATE OR REPLACE PROCEDURE chang_nom(ancien VARCHAR,nouveau VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE personnage SET nom_personnage=nouveau WHERE nom_personnage=ancien;
END;
$$;


--fonction qui rajoute un lieu

--dans l'ideal cette fonction devrait également positionner des portes pour le lieu

CREATE OR REPLACE PROCEDURE add_place(nom VARCHAR,description VARCHAR,carte TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO lieu(nom_lieu,description_lieu,carte_lieu) VALUES (nom,description,carte);
END;
$$;

-- fonction qui cree une quete

CREATE OR REPLACE PROCEDURE add_quete(nom VARCHAR,nom_donneur VARCHAR,nom_recomp VARCHAR,descript TEXT)
LANGUAGE plpgsql
AS $$
DECLARE
idinter INT ;
iddonneur INT;
idrec INT;
BEGIN 
INSERT INTO interaction(nom_interaction,description_interaction) VALUES (nom,descript);
SELECT interaction.id_interaction FROM interaction WHERE nom_interaction=nom INTO idinter;
SELECT personnage.id_personnage FROM personnage WHERE nom_personnage=nom_donneur INTO iddonneur;
SELECT personnage.id_personnage FROM personnage WHERE nom_personnage=nom_recomp INTO idrec;
INSERT INTO joue_un_role VALUES (iddonneur,idinter,'D'),(idrec,idinter,'R');
END;
$$;


--fonction qui crée un objectif et retourne l'idquete associé

CREATE OR REPLACE FUNCTION add_obj(nom VARCHAR,descr VARCHAR,idinter INT) RETURNS INTEGER
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO objectif(nom_objectif,description_objectif) VALUES (nom,descr);
RETURN idinter;
END;
$$;

--fonction qui cree une recompense pour un objectif d'une quete

CREATE OR REPLACE PROCEDURE add_rec(nom VARCHAR,descript VARCHAR,idobj INT,idinter INT, idobje INT[]) --necessaire de mettre un array pour les idobjet pour rentrer tous les objets d'une recompense
LANGUAGE plpgsql
AS $$
DECLARE 
idrec INT;
i INT;
BEGIN
INSERT INTO recompense(nom_recompense,description_recompense) VALUES (nom,descript);
SELECT id_recompense FROM recompense WHERE nom_recompense=nom INTO idrec;
FOREACH i IN ARRAY idobje LOOP
    INSERT INTO accorde VALUES (i,idrec);
END LOOP;
INSERT INTO declenche VALUES (idinter,idrec,idobj);
END;
$$;





