
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import utilities.ErrorLogger;
import utilities.PropertyManager;

/**
 *
 * @author cjones
 */
/*
db.url=jdbc:oracle:thin:@sisdbstg.buad.bloomu.edu:1521:cspstg
db.user=cs_se_user
db.password=********

jdbc:oracle:thin:cs_se_user/*********@sisdbstg.buad.bloomu.edu:1521:cspstg

If you haven't already done so, you'll need to download an Oracle driver.  I've had good luck with ojdbc6.jar.  

*/
public class Oracle_Helper {
    private static int connectionCount = 0;
    private static final boolean REUSE_CONNECTION=false;

//  private static  ConnectionPoolMySQL connectionPool = null; // ConnectionPoolMySQL.getInstance();
    private static final ConnectionPoolOracle connectionPool = ConnectionPoolOracle.getInstance();
 //   private static final boolean USE_DB_POOLING = false;
    private static final boolean USE_DB_POOLING = 
          PropertyManager.getProperty("UseDBPooling").equalsIgnoreCase("yes");
    private static Connection connection = null;
    private static final String oraclelPrefix="jdbc:oracle:thin:@";
    private static final String hostname =PropertyManager.getProperty("OracleProductionHostName").trim();
    private static final String databaseName = PropertyManager.getProperty("OracleProductionDatabaseName").trim();
    private static final String databaseURL = oraclelPrefix+hostname +":"+databaseName;
    private static final String userName = PropertyManager.getProperty("OracleProductionUserName").trim();
    private static final String password = PropertyManager.getProperty("OracleProductionPassword").trim();
    
    
     /**
     * Returns a connection to the database. 
     * 
     * @return A <code>Connection</code> to the database.
     */
    public static synchronized Connection getConnection() {
//     ErrorLogger.log(Level.SEVERE, "DB Connection Pooling is "+
//                   USE_DB_POOLING );
     if(USE_DB_POOLING)  return connectionPool.getConnection();
     if(REUSE_CONNECTION==false)   return  Oracle_Helper.getNewConnection(); 
     try {
        if (connection == null || connection.isClosed()) {
            connection = Oracle_Helper.getNewConnection();
            return connection;
        }
        // If we get here the old connection did exist, but is it still valid?
        
            //allow 1 seconds to see if connection is still valid
            if (connection.isValid(1)) {
                return connection;
            } else {
                connection = getNewConnection();
                return connection;
            }
        } catch (SQLException e) {
            return getNewConnection();
        }
    }

    /**
     * Creates a new connection with a DriverManager and returns it if the connection
     * is established. Catches any SQLException exception thrown by the
     * <code>getConnection(String url, String user, String password)</code>
     * method of <code>DriverManager</code> class and logs an error message in the error log
     * file.  
     *
     * @return A <code>Connection</code> to the database or null if none could be created.
     */
    private static Connection getNewConnection() {
        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); //Not normally needed for Oracle - but ...
        //    Debug.println("Oracle Driver Loaded");
        } catch (ClassNotFoundException ex) {
            ErrorLogger.log(Level.SEVERE, "Could not find the class oracle.jdbc.driver.OracleDriver \n"+
                    "Program will now exit. ", ex);
            System.exit(1);
        } catch (InstantiationException ex) {
            ErrorLogger.log(Level.SEVERE, "Could not instaniate the class oracle.jdbc.driver.OracleDriver \n"+
                    "Program will now exit. ", ex);
            System.exit(1);
        } catch (IllegalAccessException ex) {
            ErrorLogger.log(Level.SEVERE, "Could not access the class oracle.jdbc.driver.OracleDriver \n"+
                    "Program will now exit. ", ex);
            System.exit(1);
        }

        try {
            /*
            Debug.println("Database url is "+databaseURL +" user name is "+userName +
                    " password is "+ password);
            */
            conn = DriverManager.getConnection(databaseURL,userName,password);

       //     conn = DriverManager.getConnection("jdbc:oracle:thin@sisdbstg.buad.bloomu.edu/cspstg",userName,password);
      //      conn = DriverManager.getConnection("jdbc:oracle:thin:cs_se_user/Iw$2436@sisdbstg.buad.bloomu.edu:1521:cspstg");
        } catch (SQLException e) {
            ErrorLogger.log(Level.SEVERE, "SQL Exception was thrown while "
                    + "trying to connect to the database. Database string = "+
                    databaseURL+ " user ="+userName + " password="+password);
            ErrorLogger.log(Level.SEVERE, "Connection code is \n conn = DriverManager.getConnection("+
                    databaseURL+ ","+userName + ","+password+");");
        }
        System.out.println("Connection Count is "+ ++connectionCount);
        //Connection is thread safe
        return conn;
    }

    
    /**
     * Returns the given <code>Connection</code> object to the connection pool.
     *
     * @param connection The Connection object being returned.
     */
    public static void returnConnection(Connection connection) {
        if(USE_DB_POOLING) {
             connectionPool.freeConnection(connection);
             return;
        }
        if(REUSE_CONNECTION) return; 
        
        // Close connection if we are not reusing them 
        if (connection != null) { 
          try { connection.close(); connection=null;
              } catch (SQLException e) {
                    ErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while " +
                    "trying to close a Connection object. The connection " + 
                    "object was not null.", e); 
              }
        }
         
    }
    
    
    
    
    /**
     * Closes the given <code>Connection</code> object if it is not null.
     * Catches SQLException if it is thrown by the <code>close</code> method of
     * <code>Connection</code> interface and logs an error message in the 
     * error log file.
     *
     */
    public static void closeConnectionsOnExit() {
        if(USE_DB_POOLING) {
             return;
        }
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            ErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                    + "trying to close a Connection object during "
                    + "program termination. The connection "
                    + "object was not null.", e);
        }

    }
    
   /**
     * Closes the given <code>Statement</code> object if it is not null. Catches
     * SQLException if it is thrown by the <code>close</code> method of
     * <code>Statement</code> interface and logs an error message in the error log file.
     *
     * @param stmt The Statement object to close.
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                ErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                        + "trying to close a Statement object. The connection "
                        + "object is not null.", e);
            }
        }
    }
    
    /**
     * Closes the given <code>PreparedStatement</code> object if it is not null.
     * Catches SQLException if it is thrown by the <code>close</code> method of
     * <code>PreparedStatement</code> interface and logs an error message in the error log
     * file.
     *
     * @param ps The PreparedStatement object to close.
     */
    public static void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                ErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                        + "trying to close a PreparedStatement object. The connection "
                        + "object is not null.", e);
            }
        }
    }
    
    /**
     * Closes the given <code>ResultSet</code> object if it is not null. Catches
     * the SQLException if it is thrown by the <code>close</code> method of
     * <code>ResultSet</code> interface and logs an error message in the error log file.
     *
     * @param rs The ResultSet object to close.
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
               ErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                        + "trying to close a ResultSet object. The connection "
                        + "object is not null.", e);
            }
        }
    }
    

}
