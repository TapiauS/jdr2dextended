--trigger qui supprime tt les personnages 


CREATE OR REPLACE FUNCTION compte_utilisateur_AD() RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM personnage WHERE id_compte_utilisateur=OLD.id_compte_utilisateur;
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER mise_a_jour_perso 
    BEFORE DELETE ON compte_utilisateur
    FOR EACH ROW EXECUTE PROCEDURE compte_utilisateur_AD(); 




CREATE OR REPLACE FUNCTION pos_objet() RETURNS TRIGGER AS $$
DECLARE
xt INT;
yt INT;
id INT;
BEGIN
CASE 
    WHEN OLD.id_personnage_possede IS NULL AND NEW.id_personnage_possede IS NOT NULL THEN
        NEW.x = NULL ;
        NEW.y = NULL ;
        NEW.contenant=NULL;
        RETURN NEW ;
    WHEN OLD.id_personnage_possede IS NOT NULL AND NEW.id_personnage_possede IS NULL THEN
        SELECT x FROM personnage WHERE id_personnage=OLD.id_personnage_possede INTO xt;
        SELECT y FROM personnage WHERE id_personnage=OLD.id_personnage_possede INTO yt;
        SELECT containedcoffre(xt,yt) INTO id;
        IF id IS NOT NULL THEN
            NEW.contenant=id;
            RETURN NEW;
        ELSE
            INSERT INTO objet(nom_objet,x,y,id_type_objet) VALUES ('un tas d''objet',xt,yt,(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='tas')) RETURNING objet.id_objet INTO id;
            NEW.contenant=id;
            RETURN NEW;
        END IF;
    WHEN OLD.id_personnage_equipe IS NULL AND NEW.id_personnage_equipe IS NOT NULL THEN
        NEW.id_personnage_possede=NULL;
        RETURN NEW;
    WHEN OLD.id_personnage_equipe IS NOT NULL AND NEW.id_personnage_equipe IS NULL THEN
        NEW.id_personnage_possede=OLD.id_personnage_equipe;
        RETURN NEW;
ELSE
    RETURN NEW;
END CASE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER prendep_obj
    BEFORE UPDATE ON objet 
    FOR EACH ROW EXECUTE PROCEDURE pos_objet();


--trigger pour la gestion de la mort d'un personnage joueur,elle met le serveur POStgres en pause donc probablement pas la bonne solution ?




CREATE OR REPLACE FUNCTION addquete() RETURNS TRIGGER AS $$
DECLARE
id_obj INT [];
id INT;
BEGIN

SELECT ARRAY_AGG(id_objectif) FROM objectif WHERE id_interaction=NEW.id_interaction INTO id_obj;
    FOREACH id IN ARRAY id_obj LOOP
        INSERT INTO valide(id_objectif,id_personnage,validation) VALUES(id,NEW.id_personnage,false);
END LOOP ;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER add_quete
    BEFORE INSERT ON queste 
    FOR EACH ROW EXECUTE PROCEDURE addquete();



--Trigger qui donne une copie d'un objet a un personnage quand il réussit une quete

CREATE OR REPLACE FUNCTION getrecomp() RETURNS TRIGGER AS $$
DECLARE
valid BOOLEAN [];
val BOOLEAN;
id_obj INT [];
id INT;
queteval BOOLEAN;
id_copie INT;
BEGIN
queteval=TRUE;
IF NEW.validation=TRUE THEN
SELECT ARRAY_AGG(NEW.validation) FROM valide WHERE id_personnage=OLD.id_personnage AND id_objectif=OLD.id_objectif INTO valid;
FOREACH val IN ARRAY valid LOOP
    IF val IS FALSE THEN
        queteval=FALSE;
    END IF;
END LOOP;
ELSE
    RETURN NEW;
END IF;
IF queteval IS FALSE THEN
    RETURN NEW;
ELSE 
    SELECT ARRAY_AGG(accorde.id_objet) FROM accorde JOIN
        interaction ON interaction.id_interaction=accorde.id_interaction JOIN
        objectif ON interaction.id_interaction=objectif.id_interaction JOIN
        valide ON valide.id_objectif=objectif.id_objectif AND valide.id_personnage=NEW.id_personnage AND objectif.id_objectif=NEW.id_objectif INTO id_obj;


    FOREACH id IN ARRAY id_obj LOOP
        INSERT INTO objet(nom_objet,description_objet,emplacement,id_personnage_possede,id_type_objet) VALUES
            ((SELECT nom_objet FROM objet WHERE id_objet=id),
             (SELECT description_objet FROM objet WHERE id_objet=id),
             (SELECT emplacement FROM objet WHERE id_objet=id),
             NEW.id_personnage,
             (SELECT poid FROM objet WHERE id_objet=id),
             (SELECT id_type_objet FROM objet WHERE id_objet=id));
        SELECT id_objet FROM objet WHERE nom_objet=(SELECT nom_objet FROM objet WHERE id_objet=id) AND id_personnage_possede=NEW.id_personnage INTO id_copie;
        INSERT INTO affecte(id_objet,id_statistique,valeur) VALUES (id_copie,(SELECT id_statistique FROM statistique WHERE nom_statistique='deg'),(SELECT deg FROM fichobjet WHERE id_objet=id)),
                                                                    (id_copie,(SELECT id_statistique FROM statistique WHERE nom_statistique='redudeg'),(SELECT redudeg FROM fichobjet WHERE id_objet=id)),
                                                                    (id_copie,(SELECT id_statistique FROM statistique WHERE nom_statistique='pv'),(SELECT pv FROM fichobjet WHERE id_objet=id)),
                                                                    (id_objet,(SELECT id_statistique FROM statistique WHERE nom_statistique='pvmax'),(SELECT pvmax FROM fichobjet WHERE id_objet=id)),
                                                                    (id_objet,(SELECT id_statistique FROM statistique WHERE nom_statistique='duree'),(SELECT duree FROM fichobjet WHERE id_objet=id));                                                                   
    END LOOP;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER add_rec
    BEFORE UPDATE ON valide 
    FOR EACH ROW EXECUTE PROCEDURE getrecomp();



--trigger d'une création de personnage

CREATE OR REPLACE FUNCTION startchar() RETURNS TRIGGER AS $$
DECLARE
idlieu INT;
BEGIN
    IF NEW.id_compte_utilisateur IS NOT NULL THEN
        SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante' INTO idlieu;
        RAISE NOTICE 'idlieu %',idlieu ;
        UPDATE personnage SET id_lieu=idlieu,x=33,y=69 WHERE id_personnage=NEW.id_personnage;
        INSERT INTO caracterise(id_personnage,id_statistique,valeur) VALUES (NEW.id_personnage,(SELECT id_statistique FROM statistique WHERE nom_statistique='pV'),15),
                                                                            (NEW.id_personnage,(SELECT id_statistique FROM statistique WHERE nom_statistique='pVmax'),15),
                                                                            (NEW.id_personnage,(SELECT id_statistique FROM statistique WHERE nom_statistique='deg'),0),
                                                                            (NEW.id_personnage,(SELECT id_statistique FROM statistique WHERE nom_statistique='redudeg'),0);
        RETURN NEW;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER new_char
    AFTER INSERT ON personnage 
    FOR EACH ROW EXECUTE PROCEDURE startchar();














-- CREATE OR REPLACE FUNCTION mort() RETURNS TRIGGER AS $$
-- BEGIN
-- IF NEW.pv=0 AND OLD.id_compte_utilisateur IS NOT NULL THEN
--     NEW.vivant=FALSE;
--     PERFORM pg_sleep(10);
--     NEW.x=0;
--     NEW.y=0;
--     NEW.pv=10;
--     NEW.vivant=FALSE;
--     RETURN NEW;
-- ELSE
--     RETURN NEW;
-- END IF;
-- END;
-- $$ LANGUAGE plpgsql;

-- CREATE OR REPLACE TRIGGER gestion_deces
--     BEFORE UPDATE ON personnage
--     FOR EACH ROW EXECUTE PROCEDURE mort();






















