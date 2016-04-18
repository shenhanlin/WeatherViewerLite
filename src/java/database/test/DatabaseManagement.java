package database.test;

import utilities.Debug;



/**
 * Represents a database manager that implements <code>DatabaseManagement</code> interface. 
 * 
 * @author cjones
 */
public class DatabaseManagement implements database.DatabaseManagement {
    
    private  static  database.UserManager userManager ;
 
    private  static  database.StudentManager studentManager ;
    
    
    public DatabaseManagement(){
       
    }
    
    
    /**
     * Initializes this <code>courseManager</code> for this database.
     */
    @Override
    public final synchronized void  initializeDatabaseManagement (){
    
    }
            
    /**
     * Creates a table for this <code>DatabaseManagement</code>.
     * Throws UnsupportedOperationException when creating non-supported tables.
     */
    @Override
    public void CreateTables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns a <code>CourseManager</code> instance object for this <code>DatabaseManagement</code>.
     * 
     * @return A <code>CourseManager</code> instance object that can manipulates all kinds of operations on courses.
     */
    

    /**
     * Returns a <code>StudentManager</code> instance object for this <code>DatabaseManagement</code>.
     * 
     * @return A <code>StudentManager</code> instance object that can manipulates all kinds of operations on students.
     */
    @Override
    public database.StudentManager getStudentManager() {
        if(studentManager==null) {
            Debug.println("Student manager was null in getStudentManager() ");
            studentManager = null ; //new database.test.StudentManager();
        }
        return studentManager;
    }

    /**
     * Returns a <code>GeneralEducationManager</code> instance object for this <code>DatabaseManagement</code>.
     * 
     * @return A <code>GeneralEducationManager</code> instance object that can manipulates operations on courses.
     */
    

    /**
     * Returns a <code>WaitListManager</code> instance object for this <code>DatabaseManagement</code>.
     * 
     * @return A <code>WaitListManager</code> instance object that can manipulates operations on all kinds of waitlists.
     */
    

    /**
     * Returns a <code>UserManager</code> instance object for this <code>DatabaseManagement</code>.
     * 
     * @return A <code>UserManager</code> instance object that can manipulates operations on users.
     */
    @Override
    public database.UserManager getUserManager() {
        if (userManager == null){
            userManager = new database.test.UserManager();
        }
        return userManager; 
    }
    
    
}
