package gamegenerator;

import DAO.DAOObject;
import DAO.MapDAO;
import DAO.PorteDAO;
import jdr2dcore.Map;
import jdr2dcore.Porte;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainGameGenerator {
    public static void main (String[]args) throws SQLException, IOException {
        //mettre des images dans la BDD et les lire
        //Verslaby.construitgroslaby(50,70,0.8);
        //Map m=MapDAO.getmap(6);
        //CoffreGenerator.filldatabase(m);
        /*DAOObject.queryUDC("UPDATE objet SET id_type_objet=(SELECT id_type_objet FROM type_objet WHERE nom_type_objet='default') WHERE id_type_objet IS NULL;",new ArrayList<>());
        //DAOObject.queryUDC("CREATE TABLE portrait (id_portrait SERIAL PRIMARY KEY,nom_portrait VARCHAR,image BYTEA);",new ArrayList<>());
        //DAOObject.queryUDC("ALTER TABLE personnage ADD COLUMN id_portrait INT;",new ArrayList<>());
        //DAOObject.queryUDC("ALTER TABLE personnage ADD FOREIGN KEY(id_portrait) REFERENCES portrait(id_portrait);",new ArrayList<>());
        //File f=new File("C:\\Users\\SimTa\\OneDrive\\Images\\dwarf1.png");
        //DAOObject.fillimagebank("Dwarf1","portrait",f);
        //DAOObject.readimagebank(1,"portrait");

        int id=6;
        Map tarante= MapDAO.getmap(id);
        //Map dernholm=MapDAO.getmap(6);
        String name=MapDAO.getmap(id).getNomLieu();
        System.out.println("nom lieu = "+name);
        System.out.println("x entre= "+Verslaby.trouvpos(tarante.getCarte(),'E')[0]+" y entre="+Verslaby.trouvpos(tarante.getCarte(),'E')[1]);
        System.out.println("x sortie= "+Verslaby.trouvpos(tarante.getCarte(),'S')[0]+" y sortie="+Verslaby.trouvpos(tarante.getCarte(),'S')[1]);

        Porte sortie=new Porte(tarante,69,51);
        Porte entre=new Porte(dernholm,60,0,sortie);

        PorteDAO.addPorte(sortie);
        */
    }
}
