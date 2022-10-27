

/* Tables niveau 0 */


CREATE TABLE compte_utilisateur 
				(
					id_compte_utilisateur SERIAL PRIMARY KEY,
					pseudo_compte NOT NULL UNIQUE VARCHAR(255),
					couriel_compte NOT NULL UNIQUE VARCHAR(255),
					mdp_compte NOT NULL VARCHAR(255),
					valide BOOLEAN NOT NULL DEFAULT 'f'
				);


CREATE TABLE etat_personnage 
				(
					id_etat_personnage SERIAL PRIMARY KEY,
					nom_etat VARCHAR(255) UNIQUE NOT NULL,
					effet_etat VARCHAR(255) NOT NULL,
					description_etat VARCHAR(255) NOT NULL
				);



CREATE TABLE type_aptitude 
				(
					id_type_aptitude SERIAL PRIMARY KEY,
					nom_type_aptitude VARCHAR(255) UNIQUE NOT NULL,
				);


CREATE TABLE classe 
				(
					id_class SERIAL PRIMARY KEY,
					description_classe TEXT,
					nom_classe VARCHAR UNIQUE NOT NULL,
					jouable BOOLEAN NOT NULL
				);


CREATE TABLE type_objet 
				(
					id_type_objet SERIAL PRIMARY KEY,
					nom_type_objet VARCHAR(255) UNIQUE NOT NULL,
					emplacement_objet VARCHAR(255) NOT NULL 
					poids_objet INT NOT NULL
				);

CREATE TABLE race
				(
					id_race SERIAL PRIMARY KEY,
					nom_race VARCHAR(255) UNIQUE NOT NULL,
					jouable BOOLEAN
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

CREATE TABLE objet 
				(
					id_objet SERIAL PRIMARY KEY,
					nom_objet VARCHAR(255) NOT NULL,
					statistique_objet VARCHAR(255),
					equipe BOOLEAN,
					ouvert BOOLEAN,
					description_objet TEXT
				);

CREATE TABLE objectif 
				(
					id_objectif SERIAL PRIMARY KEY,
					nom_objectif VARCHAR(255) NOT NULL UNIQUE,
					description_objectif TEXT,
					validation_ BOOLEAN NOT NULL /*Modifier le MCD*/
				);

CREATE TABLE caracteristique
				(
					id_statistique SERIAL PRIMARY KEY,
					nom_caracteristique VARCHAR(255),
					description_caracteristique TEXT
				);

CREATE TABLE interaction
				(
					id_interaction SERIAL PRIMARY KEY,
					prerequis_interaction VARCHAR(255),
					nom_interaction VARCHAR(255),
					description_interaction VARCHAR(255)
				);

CREATE TABLE role_interaction
				(
					code_role_interaction CHAR(2) PRIMARY KEY,
					nom_role_interaction VARCHAR(255)
				);

CREATE TABLE position_s
				(
					coordonnee POINT PRIMARY KEY
				);




/* Tables niveau 1 */

CREATE TABLE aptitude
				(
					id_aptitude COUNTER PRIMARY KEY,
					nom_aptitude VARCHAR(50),
					effet_aptitude VARCHAR(255),
					prerequis_aptitude VARCHAR(255),
					id_type_aptitude INTEGER REFERENCES type_aptitude(id_type_aptitude) FOREIGN KEY 
				);

/* TABLES niveau 2 */

CREATE TABLE constitue
				(
					id_interaction INT FOREIGN KEY REFERENCES interaction(id_interaction),
					id_objectif INT FOREIGN KEY REFERENCES objectif(id_objectif),
					parametre VARCHAR(255),
					PRIMARY KEY(id_interaction,id_objectif)
				);

CREATE TABLE contient
				(
					contenant INT FOREIGN KEY REFERENCES objet(id_objet),
					id_objet_contenu INT FOREIGN KEY REFERENCES(id_objet),
					quantite INT,
					PRIMARY KEY(contenant,id_objet_contenu)
				);

CREATE TABLE relie
				(
					id_objet_porte_entre INT FOREIGN KEY REFERENCES objet(id_objet),
					id_objet_porte_sortie INT FOREIGN KEY REFERENCES objet(id_objet),
					PRIMARY KEY(id_objet_porte_entre,id_objet_porte_sortie)
				);

CREATE TABLE accorde 
				(
					id_objet INT FOREIGN KEY REFERENCES objet(id_objet),
					id_recompense INT FOREIGN KEY REFERENCES recompense(id_recompense),
					quantite INT,
					PRIMARY KEY(id_objet,id_recompense)
				);

CREATE TABLE caracterise 
				(
					id_objet INT FOREIGN KEY REFERENCES objet(id_objet),
					id_type_objet INT FOREIGN KEY REFERENCES type_objet(id_type_objet),
					PRIMARY KEY(id_objet,id_type_objet)
				);


/* TABLE NIVEAU 3 */

CREATE TABLE personnage 
				(
					id_personnage SERIAL PRIMARY KEY,
					nom_personnage VARCHAR(255) NOT NULL,
					niveau_personnage SMALLINT NOT NULL ,
					direction CHAR(10) NOT NULL,
					alignement VARCHAR(255),
					monnaie BIGINT,
					vivant BOOLEAN NOT NULL,
					id_race INT FOREIGN KEY REFERENCES race(id_race),
					id_compte_utilisateur INT FOREIGN KEY REFERENCES compte_utilisateur(id_compte_utilisateur),
					id_lieu INT FOREIGN KEY REFERENCES lieu(id_lieu)
				);

CREATE TABLE active
				(
					id_objet INT FOREIGN KEY REFERENCES objet(id_objet),
					id_interaction INT FOREIGN KEY REFERENCES interaction(id_interaction),
					code_role_interaction CHAR(2) FOREIGN KEY REFERENCES interaction(id_interaction),
					PRIMARY KEY(id_objet,id_interaction,code_role_interaction)
				);

CREATE TABLE declenche
				(
					id_interaction INT FOREIGN KEY REFERENCES interaction(id_interaction),
					id_recompense INT FOREIGN KEY REFERENCES recompense(id_recompense),
					id_objectif INT FOREIGN KEY REFERENCES objectif(id_objectif),
					PRIMARY KEY(id_interaction,id_recompense,id_objectif)
				);

CREATE TABLE instance
				(
					id_objet INT FOREIGN KEY REFERENCES objet(id_objet),
					id_lieu INT FOREIGN KEY REFERENCES lieu(id_lieu),
					coordonnee POINT FOREIGN KEY REFERENCES position_s(coordonee),
					quantite INT,
					PRIMARY KEY(id_objet,id_lieu,coordonee)
				);




/* TABLE NIVEAU 4 */

CREATE TABLE definit
				(
					id_personnage INT FOREIGN KEY REFERENCES personnage(id_personnage),
					id_statistique INT FOREIGN KEY REFERENCES statistique(id_statistique),   /*penser à enlever majuscule MCD */
					valeur INT,
					valeur_max INT,
					PRIMARY KEY(id_personnage,id_statistique)
				);

CREATE TABLE dialogue /*LES MAJUSCULES !!!!!!!!*/
				(
					id_dialogue SERIAL PRIMARY KEY,
					contenu_dialogue TEXT NOT NULL,
					id_personnage FOREIGN KEY REFERENCES personnage(id_personnage)
				);

CREATE TABLE considere 
				(
					id_personnage_jugee INT FOREIGN KEY REFERENCES personnage(id_personnage),
					id_personnage_juge INT FOREIGN KEY REFERENCES personnage(id_personnage),
					agressif BOOLEAN NOT NULL,
					PRIMARY KEY(id_personnage_jugee,id_personnage_juge)
				);

CREATE TABLE possede 
				(
					id_personnage INT FOREIGN KEY REFERENCES personnage(id_personnage),
					id_objet INT FOREIGN KEY REFERENCES objet(id_objet),
					PRIMARY KEY(id_personnage,id_objet)
				);
CREATE TABLE positionne 
				(
					id_personnage INT FOREIGN KEY REFERENCES personnage(id_personnage),
					coordonee POINT FOREIGN KEY REFERENCES position_s(coordonee),
					PRIMARY KEY(id_personnage,coordonee)
				);

CREATE TABLE appartient
				(
					id_personnage INT FOREIGN KEY REFERENCES personnage(id_personnage),
					id_classe INT FOREIGN KEY REFERENCES classe(id_classe),
					PRIMARY KEY(id_personnage,id_classe)
				)
CREATE TABLE affecte
				(
					id_personnage INT FOREIGN KEY REFERENCES personnage(id_personnage),
					id_etat_personnage INT FOREIGN KEY REFERENCES etat_personnage(id_personnage),
					PRIMARY KEY(id_personnage,id_etat_personnage)
				);

/* TABLE NIVEAU 5 */

CREATE TABLE maitrise 
				(
					id_personnage INT FOREIGN KEY REFERENCES personnage(id_personnage),
					id_aptitude INT FOREIGN KEY REFERENCES aptitude(id_aptitude),
					pourcentage_maitrise INT NOT NULL CHECK (pourcentage_maitrise>0 AND pourcentage_maitrise <101),
					PRIMARY KEY(id_personnage,id_aptitude)
				);

CREATE TABLE mene
				(
					id_dialogue_suivant INT FOREIGN KEY REFERENCES dialogue(id_dialogue),
					id_dialogue_precedent INT FOREIGN KEY REFERENCES dialogue(id_dialogue),
					choix TEXT NOT NULL,
					PRIMARY KEY(id_dialogue_suivant,id_dialogue_precedent)
				);



/*TABLE NIVEAU 6  , pas un niveau 6 en réalité car les tables ont des origines communes (mais ce n'est pas grave) */

CREATE TABLE donne 
				(
					id_interaction INT FOREIGN KEY REFERENCES interaction(id_interaction),
					id_dialogue INT FOREIGN KEY REFERENCES dialogue(id_dialogue),
					code_role_interaction CHAR(2) INT FOREIGN KEY REFERENCES role_interaction(code_role_interaction),
					PRIMARY KEY(id_interaction,id_dialogue,code_role_interaction)
				);
