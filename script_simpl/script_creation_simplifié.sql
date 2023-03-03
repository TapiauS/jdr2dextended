DROP TABLE precede CASCADE;
DROP TABLE queste CASCADE;
DROP TABLE embranchement CASCADE;
DROP TABLE donne CASCADE;
DROP TABLE dialogue CASCADE;
DROP TABLE declenche CASCADE;
DROP TABLE valide CASCADE;
DROP TABLE accorde CASCADE;
DROP TABLE personnage CASCADE;
DROP TABLE interaction CASCADE;
DROP TABLE objectif CASCADE;
DROP TABLE lieu CASCADE;
DROP TABLE compte_utilisateur CASCADE;
DROP TABLE objet CASCADE;
DROP TABLE type_objet CASCADE;

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
					id_lieu SERIAL PRIMARY KEY ,
					nom_lieu VARCHAR(255) UNIQUE,
					description_lieu VARCHAR(255),
					carte_lieu TEXT
				);

CREATE TABLE recompense
				(
					id_recompense SERIAL PRIMARY KEY ,
					nom_recompense VARCHAR(255) NOT NULL UNIQUE,
					description_recompense VARCHAR(255)
				);


CREATE TABLE statistique
				(		
					id_statistique SERIAL PRIMARY KEY,
					nom_statistique VARCHAR(255)
				);

CREATE TABLE interaction
				(
					id_interaction SERIAL PRIMARY KEY ,
					nom_interaction VARCHAR(255),
					description_interaction VARCHAR(255)
				);


CREATE TABLE type_objet
				(
					id_type_objet SERIAL PRIMARY KEY,
					nom_type_objet VARCHAR(255)
				);


/* Tables niveau 1 */


/* TABLE NIVEAU 2 */

CREATE TABLE porte
				(
					id_porte SERIAL PRIMARY KEY ,
					x INT,
					y INT,
					id_lieu INT,
					id_porte_relie INT,
					FOREIGN KEY(id_lieu) REFERENCES lieu(id_lieu) ON DELETE CASCADE,
					FOREIGN KEY(id_porte_relie) REFERENCES porte(id_porte) ON DELETE CASCADE
				);

CREATE TABLE personnage 
				(
					id_personnage SERIAL PRIMARY KEY ,
					nom_personnage VARCHAR(255) NOT NULL UNIQUE,
                    xp INT,
					vivant BOOLEAN,
                    x INT,
					y INT,
					id_lieu INT,
					id_compte_utilisateur INT,
					race VARCHAR,
					nomme BOOLEAN,
					FOREIGN KEY(id_compte_utilisateur) REFERENCES compte_utilisateur(id_compte_utilisateur) ON DELETE CASCADE,
					FOREIGN KEY(id_lieu) REFERENCES lieu(id_lieu) ON DELETE CASCADE
				);

--Tables de niveau 3


CREATE TABLE dialogue
				(
					id_dialogue SERIAL PRIMARY KEY ,
					contenu_dialogue TEXT NOT NULL,
					id_personnage INT,
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage) ON DELETE CASCADE
				);


CREATE TABLE objet 
				(
					id_objet SERIAL PRIMARY KEY ,
					nom_objet VARCHAR(255) NOT NULL,
					description_objet TEXT,
					x INT,
					y INT,
					UNIQUE(x,y),
					emplacement VARCHAR ,
                    id_personnage_possede INT,
                    id_personnage_equipe INT,
					contenant INT,
					id_lieu INT,
					poid INT,
					id_type_objet INT, 
                    FOREIGN KEY (id_personnage_possede) REFERENCES personnage(id_personnage) ON DELETE CASCADE,
                    FOREIGN KEY (id_personnage_equipe) REFERENCES personnage(id_personnage) ON DELETE CASCADE,
					FOREIGN KEY (contenant) REFERENCES objet(id_objet) ON DELETE CASCADE,
					FOREIGN KEY (id_lieu) REFERENCES lieu(id_lieu) ON DELETE CASCADE,
					FOREIGN KEY (id_type_objet) REFERENCES type_objet(id_type_objet) ON DELETE CASCADE
				);


CREATE TABLE declenche
				(
					id_interaction INT,
					id_recompense INT,
					id_objectif INT,
					PRIMARY KEY(id_interaction,id_recompense,id_objectif) ,
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction) ON DELETE CASCADE,
					FOREIGN KEY(id_recompense) REFERENCES recompense(id_recompense) ON DELETE CASCADE,
					FOREIGN KEY(id_objectif) REFERENCES objectif(id_objectif) ON DELETE CASCADE
				);


--Tables de niveau 4

CREATE TABLE affecte
				(
					id_objet INT,
					id_statistique INT,
					valeur INT,
					PRIMARY KEY(id_objet,id_statistique),
					FOREIGN KEY(id_objet) REFERENCES objet(id_objet) ON DELETE CASCADE,
					FOREIGN KEY(id_statistique) REFERENCES statistique(id_statistique) ON DELETE CASCADE
				);

CREATE TABLE caracterise 
				(
					id_personnage INT,
					id_statistique INT,
					valeur INT,
					PRIMARY KEY(id_statistique,id_personnage),
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage) ON DELETE CASCADE,
					FOREIGN KEY(id_statistique) REFERENCES statistique(id_statistique) ON DELETE CASCADE
				);


CREATE TABLE donne 
				(
					id_interaction INT,
					id_dialogue INT,
					PRIMARY KEY(id_interaction,id_dialogue) ,
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction) ON DELETE CASCADE,
					FOREIGN KEY(id_dialogue) REFERENCES dialogue(id_dialogue) ON DELETE CASCADE,
				);

CREATE TABLE embranchement
				(
					id_embranchement SERIAL,
					id_dialogue INT,
					choix TEXT NOT NULL UNIQUE,
					PRIMARY KEY(id_embranchement) ,
					FOREIGN KEY(id_dialogue) REFERENCES dialogue(id_dialogue) ON DELETE CASCADE
				);


CREATE TABLE queste
						(
							id_personnage INT,
							id_interaction INT,
							FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage) ON DELETE CASCADE,
							FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction) ON DELETE CASCADE,
							PRIMARY KEY(id_personnage,id_interaction) 
						);



--Tables de niveau 5

CREATE TABLE precede 
				(
					id_dialogue INT,
					id_embranchement INT,
					PRIMARY KEY(id_embranchement,id_dialogue) ,
					FOREIGN KEY(id_embranchement) REFERENCES embranchement(id_embranchement) ON DELETE CASCADE,
					FOREIGN KEY(id_dialogue) REFERENCES dialogue(id_dialogue) ON DELETE CASCADE
				);

--Tables de niveau 9

CREATE TABLE objectif 
				(
					id_objectif SERIAL PRIMARY KEY ,
					nom_objectif VARCHAR(255),
					id_interaction INT.
					ordre INT,
					description_objectif TEXT,
					id_objet INT,
					id_personnage INT,
					id_dialogue INT,
					FOREIGN KEY(id_dialogue) REFERENCES dialogue(id_dialogue) ON DELETE CASCADE,
					FOREIGN KEY(id_objet) REFERENCES objet(id_objet) ON DELETE CASCADE,
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage) ON DELETE CASCADE,
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction) ON DELETE CASCADE
				);

--Tables de niveau 11


CREATE TABLE valide 
				(
					id_personnage INT,
					id_objectif INT,
					validation BOOLEAN NOT NULL,
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage) ON DELETE CASCADE,
					FOREIGN KEY(id_objectif) REFERENCES objectif(id_objectif) ON DELETE CASCADE,
					PRIMARY KEY(id_personnage,id_objectif) 
				);

