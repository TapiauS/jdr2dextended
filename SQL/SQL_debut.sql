DROP TABLE donne ;
DROP TABLE mene ; 
DROP TABLE maitrise ;
DROP TABLE possede ;
DROP TABLE affecte ;
DROP TABLE appartient ;
DROP TABLE positionne ;
DROP TABLE considere ;
DROP TABLE dialogue ;
DROP TABLE definit ;
DROP TABLE instance ;
DROP TABLE active ;
DROP TABLE declenche ;
DROP TABLE contient ;
DROP TABLE relie ;
DROP TABLE accorde ;
DROP TABLE personnage ;
DROP TABLE constitue ;
DROP TABLE objet ;
DROP TABLE aptitude ;
DROP TABLE position_s ;
DROP TABLE role_interaction ;
DROP TABLE interaction ;
DROP TABLE caracteristique ;
DROP TABLE objectif ;
DROP TABLE recompense ;
DROP TABLE lieu ;
DROP TABLE race ;
DROP TABLE type_objet ;
DROP TABLE classe ;
DROP TABLE type_aptitude ;
DROP TABLE etat_personnage ;
DROP TABLE compte_utilisateur ;

/* Tables niveau 0 */


CREATE TABLE compte_utilisateur 
				(
					id_compte_utilisateur SERIAL PRIMARY KEY,
					pseudo_compte  VARCHAR(255) NOT NULL UNIQUE,
					couriel_compte  VARCHAR(255) NOT NULL UNIQUE,
					mdp_compte VARCHAR(255)NOT NULL,
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
					nom_type_aptitude VARCHAR(255) UNIQUE NOT NULL
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
				);

CREATE TABLE race
				(
					id_race SERIAL PRIMARY KEY,
					nom_race VARCHAR(255) UNIQUE NOT NULL,
					description_race TEXT,
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


CREATE TABLE objectif 
				(
					id_objectif SERIAL PRIMARY KEY,
					nom_objectif VARCHAR(255) NOT NULL UNIQUE,
					description_objectif TEXT,
					validation_ BOOLEAN NOT NULL 
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
					x INT UNIQUE,
					y INT UNIQUE,
					PRIMARY KEY(x,y)
				);




/* Tables niveau 1 */

CREATE TABLE aptitude
				(
					id_aptitude SERIAL PRIMARY KEY,
					nom_aptitude VARCHAR(50),
					effet_aptitude VARCHAR(255),
					prerequis_aptitude VARCHAR(255),
					id_type_aptitude INTEGER ,
					FOREIGN KEY (id_type_aptitude) REFERENCES type_aptitude(id_type_aptitude)
				);

CREATE TABLE objet 
				(
					id_objet SERIAL PRIMARY KEY,
					nom_objet VARCHAR(255) NOT NULL,
					statistique_objet VARCHAR(255),
					id_type_objet INT,
					equipe BOOLEAN DEFAULT 'true',
					ouvert BOOLEAN DEFAULT 'false',
					description_objet TEXT,
					FOREIGN KEY(id_type_objet) REFERENCES type_objet(id_type_objet)
				);


/* TABLES niveau 2 */

CREATE TABLE constitue
				(
					id_interaction INT,
					id_objectif INT,
					parametre VARCHAR(255),
					PRIMARY KEY(id_interaction,id_objectif),
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction),
					FOREIGN KEY(id_objectif) REFERENCES objectif(id_objectif)
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
					id_race INT,
					id_lieu INT,
					id_compte_utilisateur INT,
					FOREIGN KEY(id_race) REFERENCES race(id_race),
					FOREIGN KEY(id_compte_utilisateur) REFERENCES compte_utilisateur(id_compte_utilisateur),
					FOREIGN KEY(id_lieu) REFERENCES lieu(id_lieu)
				);

CREATE TABLE accorde 
				(
					id_objet INT,
					id_recompense INT,
					quantite INT,
					PRIMARY KEY(id_objet,id_recompense),
					FOREIGN KEY(id_recompense) REFERENCES recompense(id_recompense)
				);


CREATE TABLE relie
				(
					id_objet_porte_entre INT,
					id_objet_porte_sortie INT,
					PRIMARY KEY(id_objet_porte_entre,id_objet_porte_sortie),
					FOREIGN KEY(id_objet_porte_entre) REFERENCES objet(id_objet),
					FOREIGN KEY(id_objet_porte_sortie) REFERENCES objet(id_objet)
				);

CREATE TABLE contient
				(
					contenant INT,
					id_objet_contenu INT,
					quantite INT,
					PRIMARY KEY(contenant,id_objet_contenu),
					FOREIGN KEY(contenant) REFERENCES objet(id_objet),
					FOREIGN KEY(id_objet_contenu) REFERENCES objet(id_objet)
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



/* TABLE NIVEAU 4 */

CREATE TABLE active
				(
					id_objet INT,
					id_interaction INT,
					code_role_interaction CHAR(2),
					PRIMARY KEY(id_objet,id_interaction,code_role_interaction),
					FOREIGN KEY(id_objet) REFERENCES objet(id_objet),
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction),
					FOREIGN KEY(id_interaction) REFERENCES interaction(id_interaction)
				);

CREATE TABLE instance
				(
					id_objet INT,
					id_lieu INT,
					x INT,
					y INT,
					quantite INT,
					PRIMARY KEY(id_objet,id_lieu,x,y),
					FOREIGN KEY(id_objet) REFERENCES objet(id_objet),
					FOREIGN KEY(id_lieu) REFERENCES lieu(id_lieu),
					FOREIGN KEY(x) REFERENCES position_s(x),
					FOREIGN KEY(y) REFERENCES position_s(y)
				);

CREATE TABLE definit
				(
					id_personnage INT,
					id_statistique INT,   /*penser à enlever majuscule MCD */
					valeur INT,
					valeur_max INT,
					PRIMARY KEY(id_personnage,id_statistique),
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage),
					FOREIGN KEY(id_statistique) REFERENCES caracteristique(id_statistique)
				);

CREATE TABLE dialogue /*LES MAJUSCULES !!!!!!!!*/
				(
					id_dialogue SERIAL PRIMARY KEY,
					contenu_dialogue TEXT NOT NULL,
					id_personnage INT,
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage)
				);

CREATE TABLE considere 
				(
					id_personnage_jugee INT,
					id_personnage_juge INT,
					agressif BOOLEAN NOT NULL,
					PRIMARY KEY(id_personnage_jugee,id_personnage_juge),
					FOREIGN KEY(id_personnage_jugee) REFERENCES personnage(id_personnage),
					FOREIGN KEY(id_personnage_juge) REFERENCES personnage(id_personnage)
				);


CREATE TABLE positionne 
				(
					id_personnage INT,
					x INT,
					y INT,
					PRIMARY KEY(id_personnage,x,y),
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage),
					FOREIGN KEY(x) REFERENCES position_s(x),
					FOREIGN KEY(y) REFERENCES position_s(y)
				);

CREATE TABLE appartient
				(
					id_personnage INT,
					id_classe INT,
					PRIMARY KEY(id_personnage,id_classe),
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage),
					FOREIGN KEY(id_classe) REFERENCES classe(id_class)
				);

CREATE TABLE affecte
				(
					id_personnage INT,
					id_etat_personnage INT,
					PRIMARY KEY(id_personnage,id_etat_personnage),
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage),
					FOREIGN KEY(id_etat_personnage) REFERENCES etat_personnage(id_etat_personnage)
				);

/* TABLE NIVEAU 5 */

CREATE TABLE possede 
				(
					id_personnage INT,
					id_objet INT,
					PRIMARY KEY(id_personnage,id_objet),
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage),
					FOREIGN KEY(id_objet) REFERENCES objet(id_objet)
				);

CREATE TABLE maitrise 
				(
					id_personnage INT,
					id_aptitude INT,
					pourcentage_maitrise INT NOT NULL CHECK (pourcentage_maitrise>0 AND pourcentage_maitrise <101),
					PRIMARY KEY(id_personnage,id_aptitude),
					FOREIGN KEY(id_personnage) REFERENCES personnage(id_personnage),
					FOREIGN KEY(id_aptitude) REFERENCES aptitude(id_aptitude)
				);

CREATE TABLE mene
				(
					id_dialogue_suivant INT,
					id_dialogue_precedent INT,
					choix TEXT NOT NULL UNIQUE,
					PRIMARY KEY(id_dialogue_suivant,id_dialogue_precedent),
					FOREIGN KEY(id_dialogue_suivant) REFERENCES dialogue(id_dialogue),
					FOREIGN KEY(id_dialogue_precedent) REFERENCES dialogue(id_dialogue)
				);



/*TABLE NIVEAU 6  , pas un niveau 6 en réalité car les tables ont des origines communes (mais ce n'est pas grave) */

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
