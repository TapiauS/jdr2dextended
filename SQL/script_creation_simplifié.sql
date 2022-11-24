DROP TABLE precede CASCADE;
DROP TABLE joue_un_role CASCADE;
DROP TABLE embranchement CASCADE;
DROP TABLE donne CASCADE;
DROP TABLE dialogue CASCADE;
DROP TABLE contient CASCADE;
DROP TABLE declenche CASCADE;
DROP TABLE accorde CASCADE;
DROP TABLE personnage CASCADE;
DROP TABLE role_interaction CASCADE;
DROP TABLE interaction CASCADE;
DROP TABLE objectif CASCADE;
DROP TABLE recompense CASCADE;
DROP TABLE lieu CASCADE;
DROP TABLE compte_utilisateur CASCADE;
DROP TABLE objet CASCADE;

/* Tables niveau 0 */


CREATE TABLE compte_utilisateur 
				(
					id_compte_utilisateur SERIAL PRIMARY KEY,
					pseudo_compte  VARCHAR(255) NOT NULL UNIQUE,
					couriel_compte  VARCHAR(255) NOT NULL UNIQUE,
					mdp_compte VARCHAR(255)NOT NULL,
					valide BOOLEAN NOT NULL DEFAULT 'f'
				);

CREATE TABLE lieu
				(
					id_lieu SERIAL PRIMARY KEY,
					nom_lieu VARCHAR(255),
					description_lieu VARCHAR(255),
					carte_lieu TEXT
				);

CREATE TABLE recompense
				(
					id_recompense SERIAL PRIMARY KEY,
					nom_recompense VARCHAR(255) NOT NULL,
					description_recompense VARCHAR(255)
				);


CREATE TABLE objectif 
				(
					id_objectif SERIAL PRIMARY KEY,
					nom_objectif VARCHAR(255),
					description_objectif TEXT,
					validation_ BOOLEAN NOT NULL DEFAULT 'f'
				);


CREATE TABLE interaction
				(
					id_interaction SERIAL PRIMARY KEY,
					nom_interaction VARCHAR(255),
					description_interaction VARCHAR(255)
				);

CREATE TABLE role_interaction
				(
					code_role_interaction CHAR(2) PRIMARY KEY,
					nom_role_interaction VARCHAR(255)
				);



/* Tables niveau 1 */


/* TABLE NIVEAU 2 */

CREATE TABLE personnage 
				(
					id_personnage SERIAL PRIMARY KEY,
					nom_personnage VARCHAR(255) NOT NULL,
					direction CHAR(10) ,
                    xp INT,
                    pv INT,
					vivant BOOLEAN,
                    coordonnee POINT,
					id_lieu INT,
					id_compte_utilisateur INT,
					FOREIGN KEY(id_compte_utilisateur) REFERENCES compte_utilisateur(id_compte_utilisateur),
					FOREIGN KEY(id_lieu) REFERENCES lieu(id_lieu)
				);

--Tables de niveau 3


CREATE TABLE dialogue
				(
					id_dialogue SERIAL PRIMARY KEY,
					contenu_dialogue TEXT NOT NULL,
					id_personnage INT,
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage)
				);


CREATE TABLE objet 
				(
					id_objet SERIAL PRIMARY KEY,
					nom_objet VARCHAR(255) NOT NULL,
					statistique_objet VARCHAR(255),
					ouvert BOOLEAN DEFAULT 'false',
					description_objet TEXT,
                    id_personnage_possede INT,
                    id_personnage_equipe INT,
                    FOREIGN KEY id_personnage_possede REFERENCES personnage(id_personnage),
                    FOREIGN KEY id_personnage_equipe REFERENCES personnage(id_personnage)
				);


CREATE TABLE declenche
				(
					id_interaction INT,
					id_recompense INT,
					id_objectif INT,
					PRIMARY KEY(id_interaction,id_recompense,id_objectif),
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction),
					FOREIGN KEY(id_recompense) REFERENCES recompense(id_recompense),
					FOREIGN KEY(id_objectif) REFERENCES objectif(id_objectif)
				);




--Tables de niveau 4

CREATE TABLE accorde 
				(
					id_objet INT,
					id_recompense INT,
					quantite INT,
					PRIMARY KEY(id_objet,id_recompense),
					FOREIGN KEY(id_recompense) REFERENCES recompense(id_recompense)
				);

CREATE TABLE donne 
				(
					id_interaction INT,
					id_dialogue INT,
					code_role_interaction CHAR(2),
					PRIMARY KEY(id_interaction,id_dialogue,code_role_interaction),
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction),
					FOREIGN KEY(id_dialogue) REFERENCES dialogue(id_dialogue),
					FOREIGN KEY(code_role_interaction) REFERENCES role_interaction(code_role_interaction)
				);

CREATE TABLE contient
				(
					contenant INT,
					id_objet_contenu INT,
					PRIMARY KEY(contenant,id_objet_contenu),
					FOREIGN KEY(contenant) REFERENCES objet(id_objet),
					FOREIGN KEY(id_objet_contenu) REFERENCES objet(id_objet)
				);

CREATE TABLE embranchement
				(
					id_embranchement SERIAL,
					id_dialogue INT,
					choix TEXT NOT NULL UNIQUE,
					PRIMARY KEY(id_embranchement),
					FOREIGN KEY(id_dialogue) REFERENCES dialogue(id_dialogue)
				);


CREATE TABLE joue_un_role
						(
							id_personnage INT,
							id_interaction INT,
							code_role_interaction VARCHAR,
							FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage),
							FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction),
							FOREIGN KEY(code_role_interaction) REFERENCES role_interaction(code_role_interaction),
							PRIMARY KEY(id_personnage,code_role_interaction,id_interaction)
						);



--Tables de niveau 5

CREATE TABLE precede 
				(
					id_dialogue INT,
					id_embranchement INT,
					PRIMARY KEY(id_embranchement,id_dialogue),
					FOREIGN KEY(id_embranchement) REFERENCES embranchement(id_embranchement),
					FOREIGN KEY(id_dialogue) REFERENCES dialogue(id_dialogue)
				);
