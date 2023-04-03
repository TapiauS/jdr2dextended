package ServerPart.DAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import static Logging.Jdr2dLogger.LOGGER;

public abstract class ImageDAO extends DAOObject{

    public static BufferedImage readoneimage(int id, String imgebankname) throws DAOException {
        try {
            getConnection(filename, false);
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("SELECT image FROM " + imgebankname + " WHERE id_" + imgebankname + "=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            byte[] imgBytes = null;
            if (rs.next())
                imgBytes = rs.getBytes(1);
            InputStream is = null;
            if (imgBytes != null)
                is = new ByteArrayInputStream(imgBytes);
            BufferedImage bi = null;
            if (is != null)
                bi = ImageIO.read(is);
            rs.close();
            ps.close();
            conn.setAutoCommit(true);
            return bi;
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
        catch (IOException ioe){
            LOGGER.severe(ioe.getMessage());
            throw new DAOException(ioe,ErrorType.IOSEVERE);
        }
        catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw new DAOException(e,ErrorType.GENERALSEVERE);
        }
    }


    public static Hashtable<Integer,BufferedImage> loadfullimagebank(String imgbankname) throws DAOException {
        try {
            getConnection(filename, false);
            conn.setAutoCommit(false);
            ResultSet rs = conn.createStatement().executeQuery("SELECT id_" + imgbankname + ",image FROM " + imgbankname + ";");
            Hashtable<Integer, BufferedImage> result = new Hashtable<>();
            byte[] imgBytes = null;
            InputStream is = null;
            while (rs.next()) {
                imgBytes = rs.getBytes(2);
                is = new ByteArrayInputStream(imgBytes);
                result.put(rs.getInt(1), ImageIO.read(is));
            }
            rs.getStatement().close();
            conn.setAutoCommit(true);
            System.out.println("resultat contient " + result.size() + " images");
            return result;
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
        catch (IOException ioe){
            LOGGER.severe(ioe.getMessage());
            throw new DAOException(ioe,ErrorType.IOSEVERE);
        }
        catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw new DAOException(e,ErrorType.GENERALSEVERE);
        }
    }

    public static void fillimagebank(String imgname,String imgebankname, File img) throws DAOException {
        try {
            getConnection(filename, false);
            conn.setAutoCommit(false);
            FileInputStream fis = new FileInputStream(img);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + imgebankname + "(nom_" + imgebankname + ",image) VALUES (?,?)");
            ps.setString(1, imgname);
            ps.setBinaryStream(2, fis, img.length());
            ps.executeUpdate();
            ps.close();
            fis.close();
            conn.setAutoCommit(true);
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
        catch (IOException ioe){
            LOGGER.severe(ioe.getMessage());
            throw new DAOException(ioe,ErrorType.IOSEVERE);
        }
        catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw new DAOException(e,ErrorType.GENERALSEVERE);
        }
    }

}
