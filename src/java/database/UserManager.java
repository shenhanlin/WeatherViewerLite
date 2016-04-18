
package database;

import common.AcademicOrganization;
import common.User;
import java.util.Collection;

/**
 * Represents a <code>interface</code> of <code>UserManagement</code> to
 * manage all the user accounts which are stored in the user table.
 * 
 * @author cjones
 */
public interface UserManager {
    public User validateUser(String loginName, String password);
    public User addUser(User user); 
    public User updateUser(User user);
    public boolean deleteUser(User user);
    public User getUserByID(int userID); 
    public User getUserByLoginName(String loginName );
    public Collection<User> getAllUsersWithEmailAddress(String emailAddress);
    public Collection<User> getAllUsers();
    public boolean deleteUserbyID(int  userID);
    public boolean setForgetToken(int userID, String token);
    public String getForgottenToken(int userID);
    public Collection<User> getAllUsersWithoutSystemAdmins();
    public Collection<User> getAllUserSameOrganization(AcademicOrganization organization);
    public String getSaltByLoginName(String loginName);
    public Collection<User> getSystemAdmins();
}
