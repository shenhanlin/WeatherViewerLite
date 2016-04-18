
package database;

import common.AcademicOrganization;
import common.User;
import common.UserRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import utilities.ErrorLogger;

/**
 *
 * @author cjones
 */
public class SQLUtility {
    

   

    public static User convertResultSetToUser(ResultSet rs) {
        User user = new User();
        try {
            user.setUserNumber(rs.getInt("userNumber"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setLoginName(rs.getString("loginName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setUserRole(UserRole.getUserRole(rs.getString("userRole")));
            LocalDateTime now = LocalDateTime.parse(rs.getString("lastLogin"));
            user.setLastLogin(now);
            if (rs.getString("LastAttemptedLogin") == null) {
                user.setAttemptedLogin(LocalDateTime.now());
            } else {
                //user.setAttemptedLogin(rs.getTimestamp("LastAttemptedLogin").toLocalDateTime());
                 user.setAttemptedLogin( LocalDateTime.parse(rs.getString("LastAttemptedLogin")));
            }
            int loginCount = rs.getInt("loginCount");
            user.setLoginCount(loginCount);
            user.setAttemptedLoginCount(rs.getInt("attemptedLoginCount"));
            user.setAcademicOrganization(AcademicOrganization.getAcademicOrganization(rs.getString("academicOrganization")));
            user.setLocked(rs.getBoolean("locked"));
            user.setSalt(rs.getString("salt"));
            
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToUser error " + ex,ex);
            return null;
        }
        return user;
    }
}
