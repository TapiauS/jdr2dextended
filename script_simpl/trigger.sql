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
        NEW.x=xt; 
        NEW.y=yt;
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
        INSERT INTO objet(nom_objet,deg,redudeg,description_objet,nbrmain,emplacement,id_personnage_possede,poid,id_type_objet) VALUES
            ((SELECT nom_objet FROM objet WHERE id_objet=id),
             (SELECT deg FROM objet WHERE id_objet=id),
             (SELECT redudeg FROM objet WHERE id_objet=id),
             (SELECT description_objet FROM objet WHERE id_objet=id),
             (SELECT nbrmain FROM objet WHERE id_objet=id),
             (SELECT emplacement FROM objet WHERE id_objet=id),
             NEW.id_personnage,
             (SELECT poid FROM objet WHERE id_objet=id),
             (SELECT id_type_objet FROM objet WHERE id_objet=id));
    END LOOP;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER add_rec
    BEFORE UPDATE ON valide 
    FOR EACH ROW EXECUTE PROCEDURE getrecomp();













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






















