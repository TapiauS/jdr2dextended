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
x0 INT;
BEGIN

CASE 
    WHEN OLD.id_personnage_possede IS NULL AND NEW.id_personnage_possede IS NOT NULL THEN
        UPDATE objet SET x=NULL,y=NULL WHERE id_personnage_possede=NEW.id_personnage_possede;
        UPDATE objet SET contenant=NULL WHERE id_personnage_possede=NEW.id_personnage_possede;


        -- a partir d'ici ca ne fonctionne pas , au dessus si !

    WHEN OLD.id_personnage_possede IS NOT NULL AND NEW.id_personnage_possede IS NULL THEN
        SELECT x FROM personnage WHERE id_personnage=OLD.id_personnage_possede INTO xt;
        SELECT y FROM personnage WHERE id_personnage=OLD.id_personnage_possede INTO yt;
        -- UPDATE objet SET x=xt,y=yt WHERE OLD.id_personnage_possede IS NOT NULL AND NEW.id_personnage_possede IS NULL;
        SELECT id_objet FROM objet WHERE OLD.id_personnage_possede IS NOT NULL AND NEW.id_personnage_possede IS NULL INTO x0;
        UPDATE objet SET x=xt,y=yt WHERE id_objet=x0;
        RAISE NOTICE 'x= %',xt ;
        RAISE NOTICE 'y= %',yt ;
        RAISE NOTICE 'x0= %',x0 ;
        -- RETURN NEW;
ELSE
    RETURN NULL;
END CASE;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER prendep_obj
    AFTER UPDATE ON objet 
    FOR EACH ROW EXECUTE PROCEDURE pos_objet();




























