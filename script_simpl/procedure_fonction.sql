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

CREATE OR REPLACE FUNCTION dist(idref INT ,idcomp  INT ,tablenameref VARCHAR,tablenamecomp VARCHAR) RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
xref INT;
yref INT;
xcomp INT;
ycomp INT;
idnameref VARCHAR ;
idnamecomp VARCHAR;
requete TEXT;
BEGIN 
idnameref=CONCAT('id_',tablenameref);
idnamecomp=CONCAT('id_',tablenamecomp);
requete=CONCAT('SELECT t0.x AS refx,t0.y AS refy,t1.x AS compx,t1.y AS compy FROM ',tablenameref,' AS t0 
    JOIN ',tablenamecomp,' AS t1 ON t0.id_lieu = t1.id_lieu
WHERE t0.',idnameref,'=',idref,' AND t1.',idnamecomp,'=',idcomp);
EXECUTE requete INTO xref,yref,xcomp,ycomp;
RETURN ABS(xref-xcomp)+ABS(yref-ycomp);
END;
$$;

--Fonction qui trouve le dialogue d'entré d'un personnage

CREATE OR REPLACE FUNCTION startdialogue(idperso INT) RETURNS INT
LANGUAGE plpgsql
AS $$
DECLARE
lisid INT [];
id INT;
BEGIN 
SELECT ARRAY_AGG(id_dialogue) FROM dialogue WHERE dialogue.id_personnage=idperso INTO lisid;
FOREACH id IN ARRAY lisid LOOP 
    IF (SELECT id_embranchement FROM embranchement WHERE id_dialogue=id) IS NULL THEN
        RETURN id;
    END IF;
END LOOP;
END;
$$;


--Fonction qui determine si un objet est ou non un coffre

CREATE OR REPLACE FUNCTION is_coffre(idobjet INT) RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
BEGIN
IF (SELECT id_objet FROM objet WHERE contenant=idobjet) IS NOT NULL THEN
    RETURN true;
ELSE
    RETURN false;
END IF;
END;
$$;
