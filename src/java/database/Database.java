
package database;

import utilities.PropertyManager;

/**
 *
 * @author cjones
 */
public  class Database {
    private static DatabaseManagement database=null;
    
        
    public static synchronized DatabaseManagement getDatabaseManagement(){
       if(database == null) intialize();
       return database;
   }

    private static void intialize() {
        if(PropertyManager.isProduction()){
            database = new database.production.DatabaseManagement();
        }
        else database = new database.test.DatabaseManagement();
        
        database.initializeDatabaseManagement();
    }
    
}
