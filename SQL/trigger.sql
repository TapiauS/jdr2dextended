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

--trigger qui supprime un objet d'un coffre et de la carte quand on le ramasse

CREATE OR REPLACE FUNCTION pos_objet() RETURNS TRIGGER AS $$
BEGIN
IF OLD.objet.id_compte_utilisateur=NULL
    UDAPTE FROM objet SET coordonnee IS NULL WHERE id_personnage_possede=NEW;
    DELETE FROM contient WHERE id_objet_contenu=(SELECT id_objet FROM objet WHERE id_personnage_possede=NEW)
ELS
RETURN NEW;
END;
$$ LANGUAGE plpgsql;