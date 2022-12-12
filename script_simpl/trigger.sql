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

CREATE OR REPLACE FUNCTION pos_objet() RETURNS TRIGGER AS $$
BEGIN
CASE
    WHEN OLD.id_personnage_possede IS NULL AND OLD.contenant IS NULL AND NEW.id_personnage_possede IS NOT NULL THEN
        UPDATE objet SET x=NULL,y=NULL WHERE id_personnage_possede=NEW.id_personnage_possede;
        RETURN NEW;
    WHEN OLD.id_personnage_possede IS NULL AND OLD.contenant IS NOT NULL AND NEW.id_personnage_possede IS NOT NULL THEN
        UPDATE objet SET contenant=NULL WHERE id_personnage_possede=NEW.id_personnage_possede;
        RETURN NEW;
    WHEN NEW.id_personnage_possede IS NULL AND OLD.id_personnage_possede IS NOT NULL THEN
         UPDATE objet SET x=(SELECT x FROM personnage WHERE id_personnage=OLD.id_personnage_possede),y=(SELECT y FROM personnage WHERE id_personnage=OLD.id_personnage_possede) WHERE id_personnage_possede=OLD.id_personnage_possede;
         RETURN NEW;
    ELSE 
    RETURN NULL;
END CASE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER prendep_obj
    BEFORE UPDATE ON objet 
    FOR EACH ROW EXECUTE PROCEDURE pos_objet();
