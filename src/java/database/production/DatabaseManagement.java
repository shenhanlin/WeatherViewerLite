package database.production;

import database.StudentManager;




/**
 *
 * @author cjones
 */
public class DatabaseManagement implements database.DatabaseManagement {
    

    private  static final database.UserManager userManager = new UserManager();

    
    @Override
    public void initializeDatabaseManagement() {
        
    }   
    
    @Override
    public void CreateTables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public database.UserManager getUserManager() {
        return userManager;
    }

    @Override
    public StudentManager getStudentManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
