package DAO;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.reflect.Method;

    abstract public class DAOObject {
        protected static String filename = "app.config";
        protected static Properties config = null;
        protected static Connection conn = null;

        public static void close() throws SQLException {
            if(conn!=null) {
                conn.close();
                conn = null;
            }
        }
        protected static Properties loadConfig(String fileName){
            Properties prop = new Properties();

            try (FileInputStream fis = new FileInputStream(fileName)){
                prop.load(fis);
            }
            catch (FileNotFoundException ex){
                System.err.println(ex);
                System.exit(-1);
            }
            catch (IOException ex) {
                System.err.println("Unknown IOException");
                System.err.println(ex);
                System.exit(-2);
            }
            filename = fileName;

            return prop;
        }

        public static Connection getConnection(String fileName, boolean force) {
            boolean forcing = force || filename != fileName ||config == null|| conn==null;
            if(forcing){
                config = loadConfig(fileName);
                try {
                    conn = DriverManager.getConnection(config.getProperty("connexion"), config);
                }
                catch (SQLException sqe){
                    JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","Erreur",JOptionPane.ERROR_MESSAGE);
                    System.err.println(sqe.getMessage());
                    System.exit(-1);
                }
            }

            return conn;
        }

        public static Statement createStatement() throws SQLException{
            return getConnection(filename, false).createStatement();
        }

        public static PreparedStatement createPreparedStatement(String sql) throws SQLException{
            return getConnection(filename, false).prepareStatement(sql);
        }

        // TODO : CallableStatement

        public static ResultSet query(String sql) throws SQLException{
            return createStatement().executeQuery(sql);
        }

        public static ResultSet query(String sql, ArrayList<Object> args) throws SQLException{
            PreparedStatement pst = createPreparedStatement(sql);

            bindValues(pst, args);

            return pst.executeQuery();
        }

        public static void queryUDC(String sql, ArrayList<Object> args) throws SQLException{
            PreparedStatement pst = createPreparedStatement(sql);
            bindValues(pst, args);
           pst.executeUpdate();
           pst.close();
        }

        private static void bindValues(PreparedStatement pst, ArrayList<Object> args) throws SQLException{

            int i = 1;
            Hashtable<String, StringClassPair> correspondance = new Hashtable<String, StringClassPair>();
            correspondance.put("java.lang.Integer",    new StringClassPair("setInt", int.class));
            correspondance.put("java.lang.Boolean",    new StringClassPair("setBoolean", boolean.class));
            correspondance.put("java.lang.Date",       new StringClassPair("setDate", Date.class));
            correspondance.put("java.lang.Double",     new StringClassPair("setDouble", double.class));
            correspondance.put("java.lang.Float",      new StringClassPair("setFloat", float.class));
            correspondance.put("java.lang.Long",       new StringClassPair("setLong", long.class));
            correspondance.put("java.lang.Object",     new StringClassPair("setObject", Object.class));
            correspondance.put("java.lang.Short",      new StringClassPair("setShort", Short.class));
            correspondance.put("java.lang.String",     new StringClassPair("setString", String.class));
            correspondance.put("java.lang.jdr2dcore.Time",       new StringClassPair("setTime", Time.class));
            correspondance.put("java.lang.Timestamp",  new StringClassPair("setTimestamp", Timestamp.class));
            correspondance.put("java.lang.Byte",       new StringClassPair("setByte", Byte.class));
            //correspondance.put("")             ,        new StringClassPair("setBinaryStream",)

            for(Object arg : args){
                String func = correspondance.get(arg.getClass().getName()).getKey();
                Class c = correspondance.get(arg.getClass().getName()).getValue();

                if(func != null){
                    try{
                        Method m = pst.getClass().getMethod(func, int.class, c);
                        m.setAccessible(true);
                        m.invoke(pst, i, arg);
                        i++;
                    }
                    catch(Exception e){
                        System.err.println(e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }




