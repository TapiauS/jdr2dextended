--Ce script sert a remplir les tables SQL du script SQL_debut 

--Tables de niveau 0

--INSERT INTO compte_utilisateur

INSERT INTO etat_personnage(id_etat_personnage,nom_etat,effet_etat) VALUES 
    ('1','empoisonnement','incidence sur PV'),
    ('2','Endormi','empêche d’agir'),
    ('3','aveuglé','baisse la précision'),
    ('4','paralisé','empêche de se deplacer'),
    ('5','bénie','plus fort, plus résistant');

INSERT INTO type_aptitude(nom_type_aptitude) VALUES
('aptitude de base'),
('habilité'),
('maitrise des montures'),
('sort');


--INSERT INTO classe

INSERT INTO type_objet(nom_type_objet) VALUES
('arme distance'),
('arme à 2 mains'),
('arme cac'),
('casque'),
('armure'),
('gants'),
('pantalons'),
('chaussures'),
('potions'),
('Monnaies'),
('objet de quetes'),
('Décor');

INSERT INTO race VALUES
('1', 'Humains','Ambitieux, parfois héroïques, mais toujours confiants, les humains sont capables de travailler ensemble pour atteindre des objectifs communs, ce qui fait d’eux une puissance à ne pas négliger. Ils ont une espérance de vie réduite par rapport aux autres races, mais leur énergie sans limites et leurs passions leur permettent d’accomplir beaucoup de choses malgré leur brève existence.',true),
('2', 'Nains','On pense souvent que ces défenseurs des villes montagneuses petits et râblés sont sévères et dépourvus de tout sens de l’humour. Ils sont connus pour être des grand industriels dont les produit sont toujours d’une grande qualité et pour avoir une affinité particulière avec les richesses cachées dans les entrailles de la terre. Les nains ont aussi tendance à s’isoler et à se replier sur leurs traditions, au point de sombrer parfois dans la xénophobie.',true),
('3', 'Elfes','Grands, nobles et souvent hautains, les elfes à la longue espérance de vie sont les maîtres subtils de la nature. Ils excellent dans les arts magiques et utilisent souvent leur lien inné avec la nature pour inventer de nouveaux sorts et fabriquer de merveilleux objets qui, comme leurs créateurs, semblent presque hors d’atteinte des ravages du temps. Les elfes forment une race secrète et souvent introvertie, qui donne parfois l’impression d’être imperméable aux suppliques d’autrui.',false),
('4', 'Orques','Sauvages, brutaux et résistants, les orques sont souvent le fléau des lointaines étendues naturelles et des grottes profondes. Beaucoup deviennent de redoutables mercenaires, du fait de leur stature musculeuse et de leur tendance à entrer dans des rages sanglantes. Les quelques rares qui parviennent à contrôler leur soif de sang font d’excellents aventuriers.',true),
('5', 'Rat','',false),
('6', 'Chauve souris','',false);

INSERT INTO lieu VALUES 
('1','Bad town','',''),
('2','la reine','',''),
('3','la bête','',''),
('4','scissor palace','',''),
('5','lennybar','',''),
('6','Profit-city','',''),
('7','black market','',''),
('8','lehman sisters','',''),
('9','place-2-B','',''),
('10','treump shop','',''),
('11','nainportnawak','',''),
('12','maxilase','',''),
('13','flunchbar','',''),
('14','park in son','',''),
('15','in & out','',''),
('16','ELF village','',''),
('17','msncircus','',''),
('18','pirate search','',''),
('19','daddy awards','',''),
('20','potatoe valley','','');

--INSERT INTO recompense


INSERT INTO objectif(nom_objectif,validation_) VALUES 
('recuperer du houblon (Alain Chichon)',0),
('recuperer un fut (Maitre Kanter)',0),
('recuperer un élement secret (Chie mi Hendrix)',0),
('Aller tuer 4 chauves souris',0),
('Aller recuperer l''arme chez Durdur',0),
('Tuer Dracula (Jean Marie le PNJ)',0),
('Aller voir Hippique-sous pour avoir des infos',0),
('Donner des sousous à Durdur pour avoir une casquette à pointe afin de récupérer une boubourse à son concours de lancer de pommes',0),
('Retourner voir Benard Tappir et lui donner la boubourse gagner au concours de lancer de pomme',0),
('Combattre à l''arene',0),
('Choisir de tuer ou non Bours-La',0),
('Aller voir Nana Moule Curry pour récuperer la potion() a vendu la derniere potion au casino) elle lui donne une musse tongue à la place',0),
('rejoindre Lost Vegas au Scissor Palace',0),
('visiter le tunnel du pont en musse tongue avec Die Anna',0);

--INSERT INTO caracteristique

INSERT INTO interaction(prerequis_interaction,nom_interaction,description_interaction) VALUES
('','Bonne brune','Mr Ohbar vous a demandé de faire de la bière brune'),
('avoir la batte ail','','Un vampire terrorise les habitants de la ville (Iggy Pope)'),
('','','Bernard Tappir vous demande de ramener une boubourse volée par les nains'),
('','Victoria secret','Victoria-Big-B vous demande d''aller chercher ses objets fétiches chez la reine Bours-La'),
('','Un carrosse dans le tunnel','Die Anna se sent lente, elle vous demande d''aller chercher le bipbip ');


INSERT INTO role_interaction VALUES 
('Q','questeur'),
('D','donneur'),
('R','recompenseur');



INSERT INTO position_s(x,y)
    SELECT x AS x,y AS y
        FROM
            GENERATE_SERIES(1,50) x
        CROSS JOIN 
            GENERATE_SERIES(1,50) y
;



--Tables de niveau 1

-- INSERT INTO aptitude 

INSERT INTO objet(id_objet,nom_objet,statistique_objet,equipe,description_objet,ouvert,id_type_objet) VALUES
('1','fusil Lorrain','+1 force','TRUE','Fusil légendaire de Lorraine, capable d envoyer des fuseaux a 50 km/h','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme distance' )),
('2','paprik arme','+2 force','TRUE','Le piment le plus fort de tous les héros','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme distance' )),
('3','lance saucisse','+3 force','TRUE','De son vrai nom große würst','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme distance' )),
('4','Patator','+4 force','TRUE','Le traditionnel lance patate Russe utiliser par Poutine en Ukraine','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme distance' )),
('5','arbalais','+5 force','TRUE','Monsieur Potter, merci de ramener le balais','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme distance' )),
('6','la boule de pétoncle','+6 force','TRUE','Célébre boule du comédon Cousteau','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme distance' )),
('7','pestolet','+7 force','TRUE','Ajouter du basilic, de l ail et des François pignon et du parmesan on obtient cette arme légendaire','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme distance' )),
('8','couperet bretzel','+2 force','TRUE','Apparut en Alsace pendant la période des spaetzle du cervelas et de la choucroute en l an 69','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme à 2 mains' )),
('9','rôtisseur','+4 force','TRUE','Célèbre arme du colonel Sander','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme à 2 mains' )),
('10','la pelle forte','+6 force','TRUE','Merci Berny pour votre invention autoroutiere','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme à 2 mains' )),
('11','batte ail','+8 force','TRUE','Arme favorite de Blade','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme à 2 mains' )),
('12','la fausse croix','+10 force','TRUE','Arme légendaire de nazareth','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme à 2 mains' )),
('13','poing chope','+1 force','TRUE','Mélange entre chopine et poing ricain','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme cac' )),
('14','dague Ober','+2 force','TRUE','Mais qui l a mise à l envers ?','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme cac' )),
('15','couilletau','+3 force','TRUE','Crustacé qui sent la marais','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme cac' )),
('16','dardvador','+4 force','TRUE','Aussi rouge que le sabre','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme cac' )),
('17','porc table','+5 force','TRUE','Mi porc, mi ours, mi homme,,, Je m égare il est seulement mi table','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme cac' )),
('18','jason tatane','+8 force','TRUE','Pour les puristes de la bagarre','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='arme cac' )),
('19','bonnet m','+1 agilité','TRUE','Le sugar daddy cool des chapias','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='casque' )),
('20','casquette a pointe','+2 agilité','TRUE','Célèbre casquette germanique, utilisée dans les concours de lancer de pomme','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='casque' )),
('21','k-lotte','+3 agilité','TRUE','Arme célèbre de la confrérie Lopez, 100% cuivre','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='casque' )),
('22','kippab','+4 agilité','TRUE','Salade tomate oignon du chapeau ','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='casque' )),
('23','baie raie','+5 agilité','TRUE','Le fruit qui résume bien la situation " l abricôt"','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='casque' )),
('24','plas thon','+1 agilité','TRUE','Construite en boite de petit navire','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='armure' )),
('25','plas tèque','+2 agilité','TRUE','Manger 5 fruits et légumes par jours','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='armure' )),
('26','côte de beauf','+3 agilité','TRUE','Parfait pour les soirées tuning','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='armure' )),
('27','l ainée','+4 agilité','TRUE','Allez voir sur wikipédia','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='armure' )),
('28','pull ovaire','+5 agilité','TRUE','Bien mieux que le col roulé','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='armure' )),
('29','gants stères','+1 agilité','TRUE','Parfait pour ramasser le bois','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='gants' )),
('30','mie Teigne','+2 agilité','TRUE','Boulanger tétu de renommé','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='gants' )), 
('31','bollet rouge','+3 agilité','TRUE','Célébre coiffe du comédon Cousteau','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='gants' )),
('32','croque mie teigne','+4 agilité','TRUE','Croque monsieur du boulanger','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='gants' )),
('33','gant de toilette','+5 agilité','TRUE','Progéniture de Servietski','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='gants' )),
('34','bretelle','+1 agilité','TRUE','Et porte jartelle','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='pantalons' )),
('35','bas thon','+2 agilité','TRUE','Le leggings du comédon Cousteau','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='pantalons')),
('36','benne laden','+3 agilité','TRUE','Enfilez vos deux jambes dedans','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='pantalons')),
('37','beer muda','+4 agilité','TRUE','Mi jaune mi blanc','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='pantalons')),
('38','fûtal','+5 agilité','TRUE','Parfait pour protéger votre trou du fût','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='pantalons')),
('39','crocs','+1 agilité','TRUE','Style hollandais, chaussette claquette','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='chaussure')),
('40','bas bouche','+2 agilité','TRUE','Babooshka, babooshka, babooshka ja, ja','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='chaussure')),
('41','adaddas','+3 agilité','TRUE','Des contre façons efficaces','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='chaussure')),
('42','les talons','+4 agilité','TRUE','Dixit the Italian Stallion','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='chaussure')),
('43','musse tongue','+5 agilité','TRUE','Aussi rapide que l original','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='chaussure')),
('44','pinte','+2 esquive','TRUE','Je vous remettrais bien la petite sœur?','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('45','tord boyaux','+50 mana / -50 pv','TRUE','Très utile pour les nœuds de huit','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('46','Sheba','+50 pv / -50 mana','TRUE','Alain sheba, un gros nul','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('47','shot','+25 pv','TRUE','Boisson chaude de charlie Sheen','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('48','grogs','+50 pv','TRUE','Recette mythique des mère grand','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('49','ukranium','+25 mana','TRUE','Très prisé en Russie actuellement','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('50','muscat death','-50 mana','TRUE','Tu bois tu meurts','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('51','houmous tache','-2 dodge','TRUE','Repas favoris de Staline','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('52','tsar tziki','-5 dodge','TRUE','Sauce favorite de Lenine','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('53','potion magique','/2 magic','TRUE','Remet les idées fixes','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('54','asperule','/2 pv','TRUE','C est comme le jagermeister mais en plus mauvais','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('55','sauce dallas','*2 magic','TRUE','Sur les boulettes ?','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('56','sauce biggy','*2 force','TRUE','Avec ou sans frites ?','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('57','sauce hannibal','*2 pv','TRUE','Pour les Lecter vegan','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('58','sauce blanche','*2 dodge','TRUE','Très populaire chez les albinos du RN','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('59','barbie turique','poisoned','TRUE','Le Ken lubrique','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('60','amnesia','stunt','TRUE','A ne surtout pas fumer','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('61','algoflash','blind','TRUE','L algorithme de toto','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('62','le sang du christ','blessed','TRUE','Tu ne trouves pas ça assez catholique ?','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('63','valium','sleep','TRUE','Bienvenue le marchand de sable','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('64','bezoard','heal poisoned','TRUE','Accumulations très denses de matière partiellement digérée ou non digérée pouvant se coincer dans l estomac ou les intestins','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('65','collyre','heal blind','TRUE','Lorsque vous êtes aveugles, bien viser les yeux','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('66','schneck','heal stunt','TRUE','Très bon pain aux raisins de Moselle','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('67','epinephrine','heal sleep','TRUE','Impécable pour l hyper tension','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('68','vega missile','*2 vitesse d attaque','TRUE','Pour être satélisé','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('69','red boule','/2 vitesse d attaque','TRUE','La contre façons','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('70','bipbip','*2 vitesse de déplacement','TRUE','A consommer dans un Saddam Usain bol','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('71','le coyotte','/2 vitesse de déplacement','TRUE','Le seul loup avec un frain à main','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='potions')),
('72','Boubourse','stock money','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Monnaies')),
('73','houblon','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='objet de quetes')),
('74','fut','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='objet de quetes')),
('75','sachet','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='objet de quetes')),
('76','mur','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('77','banc','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('78','table','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('79','fleurs','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('80','rochers','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('81','lampadaire','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('82','statue','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('83','tente','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('84','fontaine','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('85','petite maison','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('86','mur de berlin','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('87','seau','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor')),
('88','clôture','','FALSE','','TRUE',(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='Décor'));

--Tables de niveaux 2

--INSERT INTO constitue 

--TABLES de niveaux 3

INSERT INTO personnage(nom_personnage) VALUES 
('Bours-La'),
('Bernard Tappir'),
('Lost Vegas'),
('Ohbar'),
('Toutavendre'),
('Hippique-sous'),
('Durdur'),
('Chie mi Hendrix'),
('Maitre Kanter'),
('Nana Moule Curry'),
('Iggy Pope'),
('Victoria-Big-B'),
('Alain Chichon');

--INSERT INTO accorde

--INSERT INTO relie

--INSERT INTO contient

--INSERT INTO declenche

--TABLE NIVEAU 4

--INSERT INTO active

--INSERT INTO instance 

--INSERT INTO definit

INSERT INTO dialogue(contenu_dialogue,id_personnage) VALUES

('Bonjour mon bon monsieur, je vous mettrais bien une petite bière ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Ohbar')),
('A votre santé !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Ohbar')),
('Effectivement j''ai besoin d''aide, Toi l''amateur de bière j''ai besoin que tu ailles récupérer du bon houblon chez "Alain-Chichon", un fut chez "Maitre Kanter", et le dernier élement secret chez "Chie mi Hendrix"',(SELECT id_personnage FROM personnage WHERE nom_personnage='Ohbar')),
('Merci mon brave, voilà une bonne brune bien relevée. Bon chance !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Ohbar')),
('Héééééé, toi la bas, tu veux de la bonne ? ',(SELECT id_personnage FROM personnage WHERE nom_personnage='Alain Chichon')),
('Et voila mon brave, du bon houblon, n''en mets pas trop si tu veux pas planer. A bientôt. ',(SELECT id_personnage FROM personnage WHERE nom_personnage='Alain Chichon')),
('Bon vent !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Alain Chichon')),
('Hopla, qu''est ce que tu veux ici ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Maitre Kanter')),
('Ca joue, voila ton fut une fois !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Maitre Kanter')),
('Ok, barre toi alors wesh',(SELECT id_personnage FROM personnage WHERE nom_personnage='Maitre Kanter')),
('Salut fieu, j''ai concocté un gros paquet c''matin, t''en veux ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Chie mi Hendrix')),
('Tiens, v''la l''sachet !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Chie mi Hendrix')),
('Je te comprends, peace',(SELECT id_personnage FROM personnage WHERE nom_personnage='Chie mi Hendrix')),
('Bonjour mon enfant, tu veux une petite friandise ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Iggy Pope')),
('Tu devras aller chasser 4 chauves souris et les emener chez Durdur, pour qu''il te fabrique une Battte Ail.
Ensuite, tu devras combattre le terrible Jean Marie le PNJ, vivant dans le noir. Tu pourras me récuperer son œil en guise de trophée.',(SELECT id_personnage FROM personnage WHERE nom_personnage='Iggy Pope')),
('Si la prochaine fois, tu veux une friandise, tu viendras me voir dans le confessional,',(SELECT id_personnage FROM personnage WHERE nom_personnage='Iggy Pope')),
('Je vais te faire courir moi tu vas voir, n''est ce pas ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Jean Marie  le PNJ')),
('Vous êtes ici pour le Flouze ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Bernard tappir')),
('??',(SELECT id_personnage FROM personnage WHERE nom_personnage='Bernard tappir')),
('Oui mon gars , des nains pas plus haut que trois pommes m''ont volé ma bourbourse. Tu dois rencontrer Hippique-sous pour obtenir des informations',(SELECT id_personnage FROM personnage WHERE nom_personnage='Bernard tappir')),
('Bonjour monsieur, venez vous delester de vos picèes ici, je vous en prie !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Hippique-sous')),
('Et voilà la sauce du chef. Vous pourrez aller voir Durdur pour qu''il puisse vous forger une pièce rare, c''est un cadeau de Bernard. Bon courage.',(SELECT id_personnage FROM personnage WHERE nom_personnage='Hippique-sous')),
('Hallo ! Toi venir ici pour acheter armures ? Ja ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Durdur')),
('Ach so, vous vouloir grosse casquette à pointe traditionnelle de mein land! Moi avoir ça derrière gros comptoir, gut gut gut ! Vous vouloir lancer apfel sur ma grosse pointe ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Durdur')),
('Super gut, si toi atteindre ma pointe moi donner grosse boubourse ach,',(SELECT id_personnage FROM personnage WHERE nom_personnage='Durdur')),
('Ha sacré voleur de schleu, merci pour ton aide mein freund, voici un joli pull ovaire fait par ma fille,',(SELECT id_personnage FROM personnage WHERE nom_personnage='Bernard tappir')),
('Bonjour mon lapin, tu es la pour mes gros objets fétiches, hihihi ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Victoria-Big B ')),
('Tu devras prouver tes talents en combat singulier à l''arène, mais pas avec n''importe quelle arme mon lapin ! ',(SELECT id_personnage FROM personnage WHERE nom_personnage='Victoria-Big B ')),
('Vous revenez de chez Victoria Big-B ? ',(SELECT id_personnage FROM personnage WHERE nom_personnage='Bours-la')),
('Tu verras ou je vais te les mettres ses objets !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Bours-la')),
('Je suis en manque de vitesse, pouvez-vous m''aider ?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Die Anna')),
('J''ai besoin de récupérer une potion à Nana Moule Curry au restaurant in&out pour retrouver ma vitesse !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Die Anna')),
('J''ai vendu la dernière la potion au casino Scissor Palace, vous devriez peut être y faire un tour ! Pour me faire pardonner je voulaisse ces Musse Tongue',(SELECT id_personnage FROM personnage WHERE nom_personnage='Nana Moule Curry')),
('Tu as pu récupérer ma potion pour ma vitesse?',(SELECT id_personnage FROM personnage WHERE nom_personnage='Die Anna')),
('Super on va pouvoir aller visiter le tunnel en Musse Tongue maintenant !',(SELECT id_personnage FROM personnage WHERE nom_personnage='Die Anna'));

--SCRIPT permettant de fixer l'attitude de tout les PNJ entre eux en "non agressif"

INSERT INTO considere(id_personnage_jugee,id_personnage_juge,agressif)
    SELECT a.id_personnage,b.id_personnage,FALSE 
        FROM personnage as a,personnage as b WHERE a.id_personnage != b.id_personnage
            ORDER BY a.id_personnage;



--INSERT INTO appartient


--INSERT INTO affecte

--INSERT INTO possede

--INSERT INTO maitrise

INSERT INTO embranchement(choix,id_dialogue) VALUES 

('M''fois biensur, et tu me mettras la petite sœur avec !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='A votre santé !')),
('Tu m''as pris pour qui, un alcoolique? Je déconne tu m''en mettras 3',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='A votre santé !')),
('C''est ici la bonne brune?',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Effectivement j''ai besoin d''aide, Toi l''amateur de bière j''ai besoin que tu ailles récupérer du bon houblon chez "Alain-Chichon", un fut chez "Maitre Kanter", et le dernier élement secret chez "Chie mi Hendrix"')),
('Non merci je préfère l''eau',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='A votre santé !')),
('J''accepte volontiers',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Merci mon brave, voilà une bonne brune bien relevée. Bon chance !')),
('Non merci j''ai d''autres choses à faire',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='A votre santé !')),
('J''aimerais commercer avec vous',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Et voila mon brave, du bon houblon, n''en mets pas trop si tu veux pas planer. A bientôt. ')),
('Je suis à la recherche de houblon pour me faire une bonne brune.',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Et voila mon brave, du bon houblon, n''en mets pas trop si tu veux pas planer. A bientôt. ')),
('Non, ça ca sent trop les fleurs ici !!',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Bon vent !')),
('Je voudrais récuperer un fut pour brasser',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Ca joue, voila ton fut une fois !')),
('Tu me remettras la petite sœur, et tu me changeras le verre, celui-ci goute le savon. J''aime bien la mousse mais il faut pas exagerer !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Ca joue, voila ton fut une fois !')),
('Mais pas si vite, ça fait d''la mousse,',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Ok, barre toi alors wesh3')),
('Allons y, j''ai pris mon antiémétiques ce matin.',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Tiens, v''la l''sachet !')),
('Ohbar m''a donné ce sachet pour récuperer un ingrédient secret.',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Tiens, v''la l''sachet !')),
('vu l''odeur, ca me ferait mal !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Je te comprends, peace')),
('Il parait qu''un vampire rode dans les environs, besoin d''une croisade ?',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Tu devras aller chasser 4 chauves souris et les emener chez Durdur, pour qu''il te fabrique une Battte Ail. Ensuite, tu devras combattre le terrible Jean Marie le PNJ, vivant dans le noir. Tu pourras me récuperer son œil en guise de trophée.')),
('Je voudrais parier.',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='??')),
('J''aimerais vos conseils pour me lancer dans les affaires',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='??')),
('C''est vous qui êtes à la recherche d''une Bourbourse ?',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Oui mon gars , des nains pas plus haut que trois pommes m''ont volé ma bourbourse. Tu dois rencontrer Hippique-sous pour obtenir des informations')),
('Yep, j''accepte la grosse quête',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='??')),
('Non, vous avez assez de flouze je voudrais le vôtre ',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='??')),
('Non merci, je n''ai pas confiance. Je viens de la part de Nanard, il recherche sa boubourse perdue au dernier match de foot, vous auriez des infos ?',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Et voilà la sauce du chef. Vous pourrez aller voir Durdur pour qu''il puisse vous forger une pièce rare, c''est un cadeau de Bernard. Bon courage.')),
('Entre vous et ma femme, je vais peut-etre vous laisser mes economies,',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Bonjour monsieur, venez vous delester de vos picèes ici, je vous en prie !')),
('Les frais ne sont pas assez O low cost !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Bonjour monsieur, venez vous delester de vos picèes ici, je vous en prie !')),
('Vous avez pas plutôt des schnitzel?',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Ach so, vous vouloir grosse casquette à pointe traditionnelle de mein land! Moi avoir ça derrière gros comptoir, gut gut gut ! Vous vouloir lancer apfel sur ma grosse pointe ?')),
('Jajaja, Muskatnuss herr Muller !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Ach so, vous vouloir grosse casquette à pointe traditionnelle de mein land! Moi avoir ça derrière gros comptoir, gut gut gut ! Vous vouloir lancer apfel sur ma grosse pointe ?2')),
('C''est ici qu''on peut se procurer de l''équipement pour le concours de lancer de pomme?',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Ach so, vous vouloir grosse casquette à pointe traditionnelle de mein land! Moi avoir ça derrière gros comptoir, gut gut gut ! Vous vouloir lancer apfel sur ma grosse pointe ?')),
('Jajaja, moi avoir plein de deutschmark',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Super gut, si toi atteindre ma pointe moi donner grosse boubourse ach.')),
('Jajaja',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Super gut, si toi atteindre ma pointe moi donner grosse boubourse ach,')),
('nein nein nein',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Hallo ! Toi venir ici pour acheter armures ? Ja ?')),
('Biensur, et il n''y a pas que les objets qui sont gros hahaha. Plus c''est gros plus c''est bon,',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Tu devras prouver tes talents en combat singulier à l''arène, mais pas avec n''importe quelle arme mon lapin ! ')),
('Quels objets ? Tu m''as pris pour une œuvre de Paul McCarthy !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Tu devras prouver tes talents en combat singulier à l''arène, mais pas avec n''importe quelle arme mon lapin ! ')),
('Oui j''ai besoin de ses objets fétiches !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Tu verras ou je vais te les mettres ses objets !')),
('Non, je suis là pour me battre avec des gros durs !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Tu verras ou je vais te les mettres ses objets !2')),
('Oui biensur, et j''ai même des Musse Tongue en prime pour voyager sereinement !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='Super on va pouvoir aller visiter le tunnel en Musse Tongue maintenant !')),
('Non, malheureusement Nana Moule Curry à vendu la dernière au Casino !',(SELECT id_dialogue FROM dialogue WHERE contenu_dialogue='J''ai besoin de récupérer une potion à Nana Moule Curry au restaurant in&out pour retrouver ma vitesse !'));


--Tables de niveau 6

--INSERT INTO donne


--Script d'insertion dans precede, probablement à perfectionner 


INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement
                ON choix='M''fois biensur, et tu me mettras la petite sœur avec !' AND contenu_dialogue='Bonjour mon bon monsieur, je vous mettrais bien une petite bière ?'
;      
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Tu m''as pris pour qui, un alcoolique? Je déconne tu m''en mettras 3' AND contenu_dialogue='Bonjour mon bon monsieur, je vous mettrais bien une petite bière ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='C''est ici la bonne brune?' AND contenu_dialogue='Bonjour mon bon monsieur, je vous mettrais bien une petite bière ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Non merci je préfère l''eau' AND contenu_dialogue='Bonjour mon bon monsieur, je vous mettrais bien une petite bière ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='J''accepte volontiers' AND contenu_dialogue='Effectivement j''ai besoin d''aide, Toi l''amateur de bière j''ai besoin que tu ailles récupérer du bon houblon chez "Alain-Chichon", un fut chez "Maitre Kanter", et le dernier élement secret chez "Chie mi Hendrix"' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Non merci j''ai d''autres choses à faire' AND contenu_dialogue='Effectivement j''ai besoin d''aide, Toi l''amateur de bière j''ai besoin que tu ailles récupérer du bon houblon chez "Alain-Chichon", un fut chez "Maitre Kanter", et le dernier élement secret chez "Chie mi Hendrix"' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='J''aimerais commercer avec vous' AND contenu_dialogue='Héééééé, toi la bas, tu veux de la bonne ? ' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Je suis à la recherche de houblon pour me faire une bonne brune.' AND contenu_dialogue='Héééééé, toi la bas, tu veux de la bonne ? ' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Non, ça ca sent trop les fleurs ici !!' AND contenu_dialogue='Héééééé, toi la bas, tu veux de la bonne ? ' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Je voudrais récuperer un fut pour brasser' AND contenu_dialogue='Hopla, qu''est ce que tu veux ici ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Tu me remettras la petite sœur, et tu me changeras le verre, celui-ci goute le savon. J''aime bien la mousse mais il faut pas exagerer !' AND contenu_dialogue='Hopla, qu''est ce que tu veux ici ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Mais pas si vite, ça fait d''la mousse,' AND contenu_dialogue='Hopla, qu''est ce que tu veux ici ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Allons y, j''ai pris mon antiémétiques ce matin.' AND contenu_dialogue='Salut fieu, j''ai concocté un gros paquet c''matin, t''en veux ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Ohbar m''a donné ce sachet pour récuperer un ingrédient secret.' AND contenu_dialogue='Salut fieu, j''ai concocté un gros paquet c''matin, t''en veux ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='vu l''odeur, ca me ferait mal !' AND contenu_dialogue='Salut fieu, j''ai concocté un gros paquet c''matin, t''en veux ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Il parait qu''un vampire rode dans les environs, besoin d''une croisade ?' AND contenu_dialogue='Bonjour mon enfant, tu veux une petite friandise ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Je voudrais parier.' AND contenu_dialogue='Vous êtes ici pour le Flouze ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='J''aimerais vos conseils pour me lancer dans les affaires' AND contenu_dialogue='Vous êtes ici pour le Flouze ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='C''est vous qui êtes à la recherche d''une Bourbourse ?' AND contenu_dialogue='Vous êtes ici pour le Flouze ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Yep, j''accepte la grosse quête' AND contenu_dialogue='Oui mon gars , des nains pas plus haut que trois pommes m''ont volé ma bourbourse. Tu dois rencontrer Hippique-sous pour obtenir des informations' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Non, vous avez assez de flouze je voudrais le vôtre ' AND contenu_dialogue='Oui mon gars , des nains pas plus haut que trois pommes m''ont volé ma bourbourse. Tu dois rencontrer Hippique-sous pour obtenir des informations' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Non merci, je n''ai pas confiance. Je viens de la part de Nanard, il recherche sa boubourse perdue au dernier match de foot, vous auriez des infos ?' AND contenu_dialogue='Bonjour monsieur, venez vous delester de vos picèes ici, je vous en prie !' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Entre vous et ma femme, je vais peut-etre vous laisser mes economies,' AND contenu_dialogue='Bonjour monsieur, venez vous delester de vos picèes ici, je vous en prie !' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Les frais ne sont pas assez O low cost !' AND contenu_dialogue='Bonjour monsieur, venez vous delester de vos picèes ici, je vous en prie !' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Vous avez pas plutôt des schnitzel?' AND contenu_dialogue='Hallo ! Toi venir ici pour acheter armures ? Ja ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Jajaja, Muskatnuss herr Muller !' AND contenu_dialogue='Hallo ! Toi venir ici pour acheter armures ? Ja ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='C''est ici qu''on peut se procurer de l''équipement pour le concours de lancer de pomme?' AND contenu_dialogue='Hallo ! Toi venir ici pour acheter armures ? Ja ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Jajaja, moi avoir plein de deutschmark' AND contenu_dialogue='Hallo ! Toi venir ici pour acheter armures ? Ja ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Jajaja' AND contenu_dialogue='Ach so, vous vouloir grosse casquette à pointe traditionnelle de mein land! Moi avoir ça derrière gros comptoir, gut gut gut ! Vous vouloir lancer apfel sur ma grosse pointe ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='nein nein nein' AND contenu_dialogue='Ach so, vous vouloir grosse casquette à pointe traditionnelle de mein land! Moi avoir ça derrière gros comptoir, gut gut gut ! Vous vouloir lancer apfel sur ma grosse pointe ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Biensur, et il n''y a pas que les objets qui sont gros hahaha. Plus c''est gros plus c''est bon,' AND contenu_dialogue='Bonjour mon lapin, tu es la pour mes gros objets fétiches, hihihi ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Quels objets ? Tu m''as pris pour une œuvre de Paul McCarthy !' AND contenu_dialogue='Bonjour mon lapin, tu es la pour mes gros objets fétiches, hihihi ?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Oui j''ai besoin de ses objets fétiches !' AND contenu_dialogue='Vous revenez de chez Victoria Big-B ? ' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Non, je suis là pour me battre avec des gros durs !' AND contenu_dialogue='Vous revenez de chez Victoria Big-B ? ' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Oui biensur, et j''ai même des Musse Tongue en prime pour voyager sereinement !' AND contenu_dialogue='Tu as pu récupérer ma potion pour ma vitesse?' ;
INSERT INTO precede(id_dialogue,id_embranchement) 
    SELECT dialogue.id_dialogue,id_embranchement 
        FROM dialogue 
            JOIN embranchement 
                ON choix='Non, malheureusement Nana Moule Curry à vendu la dernière au Casino !' AND contenu_dialogue='Tu as pu récupérer ma potion pour ma vitesse?';





 