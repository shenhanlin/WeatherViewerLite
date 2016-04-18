
package common;

import java.io.Serializable;
import utilities.Debug;

/**
 * LIST WHAT EACH USER CAN DO IN SYSTEM __ SEE ADVISOR
 * Represents the role with which a <code> User </code> in this system. Different
 * users have different access levels and different levels of functionality and
 * responsibility within the system.  
 * 
 * @author cjones
 */
public enum UserRole implements Serializable{
    
    /**
     * The administrator for the entire program, has complete control of the
     * system. 
     */
    SystemAdmin("SystemAdmin"),
    
    /**
     * An administrator at BU, likely a College Dean. The supervisor of a Chair.
     */
    Administrator("Administrator"),
    
    /**
     * The chair of a department. Manages faculty, students, and classes. 
     */
    Chair("Chair"),
    /**
     * A Secretary, Administrative Assistant or Office Manager. 
     * An Administrative Assistant supports a Chair or
     * Administrator. 
     * 
     */
    
   AdministrativeAssistant("AdministrativeAssistant"),
   
   /**
     * A Faculty member can be an advisor or an instructor.  
     * Faculty can check graduation 
     * requirements and recommend courses to students. They can also 
     * place students on wait lists. 
     * 
     */
   Faculty("Faculty"),
   
    
    /**
     * An advisor to one or more students. Advisors can check graduation 
     * requirements and recommend courses to students. They can also 
     * place students on wait lists. 
     */
    Advisor("Advisor"),
    
    /**
     * An instructor of one of more courses.
     */
    Instructor("Instructor"),
    
    /**
     * A user that works at the university. <code> Staff </code> can add 
     * students to a wait list. 
     */
    Staff("Staff"),
    
    /**
     * A student in the university.
     */
    Student("Student"),
    
    /**
     * The default account, has very limited access.
     */
    Guest("Guest");
    
    private final String roleName;
    
    /**
     * Constructs a <code> UserRole </code> given a role name. A user role
     * represents their position in the college and does not require them to be
     * a member of the <code> Faculty </code>.
     * 
     * @param roleName The name of this <code> UserRole </code> position.
     */
    UserRole(String roleName){
        this.roleName=roleName.trim();
    }
    
    /**
     * Returns the <code> UserRole </code> of the given role name. If the role name
     * given is not one of the roles in the enumerated type, the default is set to "Guest."
     * 
     * @param roleName The name of this role, preferably one already in the enumerated type
     * (SystemAdmin, Administrator, Chair, Advisor, Instructor, Staff, or Student).
     * 
     * @return The <code> UserRole </code> corresponding to the role name given.
     */
     public static UserRole getUserRole (String roleName) {
       UserRole name = Guest; // default is guest 

        for (UserRole e : UserRole.values ()) {
            if (e.roleName.equalsIgnoreCase(roleName.trim())){
                name = e;
                break;
            }
        }
        return (name);
    }

     /**
     * Returns the role name of this <code> UserRole </code>.
     * 
     * @return This <code> UserRole </code>'s name.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Returns this <code> UserRole </code>'s name as a string.
     * 
     * @return The name of this <code> UserRole </code> as a string.
     */
    @Override
    public String toString() {
        return "UserRole{" + "roleName=" + roleName + '}';
    }
    
    /**
     * Tests the <code>UserRole</code> class. Reviews the toString(), 
     * getRoleName(), getEnum(), and UserRole().
     * 
     * @param args Command line arguments.
     */
    public static void main (String args[])
    {
        Debug.println(getUserRole("chair"));
        Debug.println(getUserRole("C").toString());
        
        Debug.println(Chair.getRoleName());
        Debug.println(Advisor.getRoleName());
        Debug.println(Instructor.getRoleName());
        Debug.println(Staff.getRoleName());
        Debug.println(SystemAdmin.getRoleName());
        Debug.println(Administrator.getRoleName());
        Debug.println(Student.getRoleName());
        Debug.println(Guest.getRoleName());
        
        Debug.println(getUserRole(Chair.getRoleName()));
        Debug.println(getUserRole(Advisor.getRoleName()));
        Debug.println(getUserRole(Instructor.getRoleName()));
        Debug.println(getUserRole(Staff.getRoleName()));
        Debug.println(getUserRole(SystemAdmin.getRoleName()));
        Debug.println(getUserRole(Administrator.getRoleName()));
        Debug.println(getUserRole(Student.getRoleName()));
        Debug.println(getUserRole(Guest.getRoleName()));
        
        
        Debug.println();
    }
}
