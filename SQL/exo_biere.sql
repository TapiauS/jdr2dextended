-- Liste biére

SELECT nom_article,NOM_MARQUE,NOM_TYPE,NOM_COULEUR,NOM_PAYS,NOM_CONTINENT
    FROM article 
        INNER JOIN marque 
            ON marque.ID_MARQUE=article.ID_MARQUE
        INNER JOIN typebiere
            ON typebiere.ID_TYPE=article.id_type 
        INNER JOIN couleur 
            ON couleur.ID_COULEUR=article.ID_COULEUR
        INNER JOIN pays 
            ON pays.ID_PAYS=marque.ID_PAYS
        INNER JOIN continent 
            ON continent.ID_CONTINENT=pays.ID_CONTINENT
        ORDER BY NOM_CONTINENT,NOM_PAYS
;

SELECT nom_fabricant,COUNT(id_article)
    FROM article
        INNER JOIN marque
            ON marque.id_marque=article.id_marque
        INNER JOIN fabricant
            ON marque.id_fabricant=fabricant.id_fabricant

GROUP BY nom_fabricant;

-- ne marche pas !!!!! ordre chelou d'execution des requétes

-- SELECT type_biere,COUNT(id_article) AS compte
--     FROM article
--         INNER JOIN typebiere
--             ON typebiere.id_type=article.id_type
-- GROUP BY typebiere


-- SELECT nom_pays,AVG(compte.COUNT)
--     FROM article 
--         INNER JOIN marque
--             ON marque.id_marque=article.id_marque
--         INNER JOIN pays
--             ON pays.ID_PAYS=marque.ID_PAYS
--         INNER JOIN typebiere
--             ON typebiere.ID_TYPE=article.id_type 

-- GROUP BY nom_pays,typebiere.id_type;


SELECT nom_article,quantite,prix_vente,ticket.numero_ticket,ticket.annee,quantite*prix_vente AS total
    FROM article
        INNER JOIN vendre 
            ON article.id_article=vendre.id_article
        INNER JOIN ticket 
            ON ticket.numero_ticket=vendre.numero_ticket AND ticket.annee=vendre.annee
    ORDER BY ticket.annee,total
   ;

select pays.nom_pays, avg(t1.compteur) 
    from pays 
        inner join 
          ( select  nom_pays,nom_type,count(id_article) 
                as compteur from article 
            inner join typebiere 
                on article.id_type=typebiere.id_type 
            inner join marque 
                on article.id_marque=marque.id_marque 
            inner join pays 
                on marque.id_pays=pays.id_pays 
            group by nom_pays,nom_type) as t1 
                on pays.nom_pays=t1.nom_pays 
        group by pays.nom_pays;

SELECT ticket.annee, AVG(t1.total::NUMERIC)::MONEY AS "ticket moyen"
     FROM ticket
            JOIN (SELECT ticket.numero_ticket,ticket.annee,quantite*prix_vente AS total 
                        FROM ticket
                            INNER JOIN vendre
                                ON ticket.numero_ticket=vendre.numero_ticket AND ticket.annee=vendre.annee
                 ) AS t1
            ON t1.numero_ticket=ticket.numero_ticket AND ticket.annee=t1.annee
    GROUP BY ticket.annee
    ORDER BY annee ;    
