package gamegenerator;

import DAO.ImageDAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Objects;

public class MainGameGenerator {
    public static void main (String[]args) throws SQLException, IOException {

        /*Path target= Paths.get("C:\\Users\\SimTa\\Documents\\Projet_afpa\\jdr2d\\code_java\\jdr2d\\Portraits\\test.png");

        InputStream is = new ByteArrayInputStream(DAOObject.readoneimage(1,"portrait"));
        BufferedImage bi = ImageIO.read(is);
        Graphics2D g = bi.createGraphics();
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Hello World", 100, 100);

        // save it
        ImageIO.write(bi, "png", target.toFile());

*/
        File sourcerepo=new File("Portraits");

        for (File f: Objects.requireNonNull(sourcerepo.listFiles())) {
            ImageDAO.fillimagebank(f.getName(),"portrait",f);
        }

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
