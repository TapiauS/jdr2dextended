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

--trigger qui supprime un objet d'un coffre et de la carte quand on le ramasse ou a l'opposée dépose un objet sur la carte aux bonnes coordonnées si on l'enleve de l'inventaire

-- CREATE OR REPLACE FUNCTION pos_objet() RETURNS TRIGGER AS $$
-- BEGIN
-- CASE
--     WHEN OLD.id_personnage_possede=NULL AND OLD.contenant=NULL AND NEW.id_personnage_possede!=NULL THEN
--         UPDATE objet SET x=NULL,y=NULL WHERE OLD.id_personnage_possede=NULL AND NEW.id_personnage_possede !=NULL;
--         RETURN NEW;
--     WHEN OLD.id_personnage_possede=NULL AND OLD.contenant !=NULL AND NEW.id_personnage_possede != NULL THEN
--         UPDATE objet SET contenant=NULL WHERE OLD.id_personnage_possede=NULL AND NEW.id_personnage_possede!=NULL;
--         RETURN NEW;
--     WHEN NEW.id_personnage_possede=NULL AND OLD.id_personnage_possede!=NULL THEN
--          UPDATE objet SET x=(SELECT x FROM personnage WHERE id_personnage=OLD.id_personnage_possede),y=(SELECT y FROM personnage WHERE id_personnage=OLD.id_personnage_possede) WHERE NEW.id_personnage_possede=NULL AND OLD.id_personnage_possede !=NULL;
--         RETURN NEW;
--     ELSE 
--     RETURN NEW;
-- END CASE;
-- END;
-- $$ LANGUAGE plpgsql;

-- CREATE OR REPLACE TRIGGER prendep_obj
--     BEFORE UPDATE ON objet 
--     FOR EACH ROW EXECUTE PROCEDURE pos_objet();


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



--TODO : trigger qui set les objectifs d'une quete a false quand il la prend

CREATE OR REPLACE FUNCTION addquete() RETURNS TRIGGER AS $$
DECLARE
id_obj INT [];
id INT;
BEGIN
IF NEW.code_role_interaction='Q' THEN
    SELECT ARRAY_AGG(id_objectif) FROM objectif WHERE id_interaction=NEW.id_interaction INTO id_obj;
    FOREACH id IN ARRAY id_obj LOOP
        INSERT INTO valide(id_objectif,id_personnage,validation) VALUES(id,NEW.id_personnage,false);
    END LOOP ;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER add_quete
    BEFORE INSERT ON joue_un_role 
    FOR EACH ROW EXECUTE PROCEDURE addquete();



















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






















