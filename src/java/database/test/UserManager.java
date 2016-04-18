package database.test;

import common.AcademicOrganization;
import common.User;
import database.MYSQL_Helper;
import database.SQLUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.ErrorLogger;
import utilities.PropertyManager;

/**
 * Represents a manager of the user's database, which implement
 * <code>UserManager</code> interface.This class can be used to modify and
 * extract the user's data which is stored in the database.
 *
 * @author cjones
 */
public class UserManager implements database.UserManager {

    /**
     * Returns this <code>User</code> object when the login name and password
     * are correct. At the same time the last login time and the number of
     * logins should be updated, if the login name and password are correct.
     *
     * @param loginName The login name of this user.
     * @param password The password of this user.
     * @return A <code>User</code> object contains all the information about
     * this user.
     */
    @Override
    public User validateUser(String loginName, String password) {
        String sql = "SELECT * FROM user_data WHERE loginName = ? AND userPassword = ? ; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        User user = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, loginName);
            stmt2.setString(2, password);
            rs = stmt2.executeQuery();
            if (rs == null || rs.next() == false) {
                MYSQL_Helper.returnConnection(conn);
                return null;
            }
            user = SQLUtility.convertResultSetToUser(rs);
            
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in validateUser(String loginName, String password) loginName=" + 
                    loginName + " password=" + password + " error: " + ex);
            return null;
        } finally {
            MYSQL_Helper.closePreparedStatement(stmt2);
            MYSQL_Helper.closeResultSet(rs);
            MYSQL_Helper.returnConnection(conn);
        }

        updateUser(user);
        return user;
    }

    /**
     * Returns this <code>User</code> object which has been added to the user's
     * database successfully.
     *
     * @param user A <code>User</code> object which will be used to add to the
     * user's database.
     * @return A <code>User</code> object which has been added to the user's
     * database.
     */
    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO user_data (userNumber, loginName, userPassword, salt, firstName, lastName, emailAddress, academicOrganization,"
                + "userRole, lastLogin, loginCount) VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?); ";
        Connection conn = database.MYSQL_Helper.getConnection();

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, user.getLoginName().trim());
            stmt2.setString(2, user.getUserPassword().trim());
            stmt2.setString(3, user.getSalt().trim());
            stmt2.setString(4, user.getFirstName().trim());
            stmt2.setString(5, user.getLastName().trim());
            stmt2.setString(6, user.getEmailAddress().trim());
            stmt2.setString(7, user.getAcademicOrganization().getOrganizationName());
            stmt2.setString(8, user.getUserRole().getRoleName());
            stmt2.setString(9, user.getLastLogin().toString());
            stmt2.setInt(10, user.getLoginCount());

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in addUser(User user) user="+user+" error: "+ex);
            MYSQL_Helper.returnConnection(conn);
            return user;
        }

        return getUserByLoginName(user.getLoginName());//need to sue genterated keys
    }

    /**
     * Returns a <code>User</code> object according to the given user ID.
     *
     * @param userID The ID of a user that want to be exported.
     * @return A <code>User</code> object with this user's ID.
     */
    @Override
    public User getUserByID(int userID) {
        String sql = "SELECT * FROM user_data WHERE userNumber = ? ; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        User user = null;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setInt(1, userID);

            rs = stmt2.executeQuery();
            if (rs == null || rs.next() == false) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return null;
            }
            user = SQLUtility.convertResultSetToUser(rs);
            
            rs.close();
            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getUserByID(int userID) userID="+userID + " error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return null;
        }
        MYSQL_Helper.returnConnection(conn);

        return user;
    }

    /**
     * Returns a <code>User</code> object according to the given login name.
     *
     * @param loginName The login name for a user that want to be exported.
     * @return A <code>User</code> object with this login name.
     */
    @Override
    public User getUserByLoginName(String loginName) {
        String sql = "SELECT * FROM user_data WHERE loginName = ? ; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        User user = null;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, loginName);

            rs = stmt2.executeQuery();
            if (rs == null || rs.next() == false) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return null;
            }
             user = SQLUtility.convertResultSetToUser(rs);
            rs.close();
            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getUserByLoginName(String loginName) loginName=" + loginName + " error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return null;
        }
        MYSQL_Helper.returnConnection(conn);
        return user;
    }

    /**
     * Returns a collection of <code>User</code> object, which includes all the
     * users that use the given email address.
     *
     * @param emailAddress A email address.
     * @return A collection of <code>User</code> object according to the given
     * email address.
     */
    @Override
    public Collection<User> getAllUsersWithEmailAddress(String emailAddress) {
        Collection<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user_data WHERE emailAddress = ? ; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        User user = null;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, emailAddress);

            rs = stmt2.executeQuery();
            if (rs == null) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return users;
            }
            while (rs.next()) {
                user = SQLUtility.convertResultSetToUser(rs);
                users.add(user);
            }

            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getAllUsersWithEmailAddress(String emailAddress) emailAddress="+emailAddress+" error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return users;
        }

        MYSQL_Helper.returnConnection(conn);

        return users;

    }

    /**
     * Returns a collection of <code>User</code> object, which includes all the
     * users in the database.
     *
     * @return A collection of <code>User</code> object, which includes all the
     * users in the database.
     */
    @Override
    public Collection<User> getAllUsers() {
        Collection<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user_data ; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        User user = null;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            rs = stmt2.executeQuery();
            if (rs == null) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return users;
            }
            while (rs.next()) {
                user = SQLUtility.convertResultSetToUser(rs);
                
                users.add(user);
            }
            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getAllUsers() error:" + ex);
            MYSQL_Helper.returnConnection(conn);
            return users;
        }

        MYSQL_Helper.returnConnection(conn);

        return users;
    }

    /**
     * Returns a <code>User</code> object with latest update, which is stored in
     * the database. This method also clears reset token if user is currently in
     * reset mode.
     *
     * @param user A <code>User</code> object which contains the latest
     * information.
     * @return A <code>User</code> object with latest update.
     */
    @Override
    public User updateUser(User user) {
        String sql = "UPDATE user_data SET loginName =?, userPassword=?, salt=?,"
                + "firstName = ?, lastName=?, emailAddress=?,"
                + "academicOrganization=?, userRole=?, lastLogin=?, lastAttemptedLogin=?, loginCount = ?, attemptedLoginCount=?,"
                + " token=?, tokenTime=?, locked=? where userNumber = ? ; ";

        //User newUser = null;
        Connection conn = database.MYSQL_Helper.getConnection();
        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, user.getLoginName().trim());
            stmt2.setString(2, user.getUserPassword().trim());
            stmt2.setString(3, user.getSalt().trim());
            stmt2.setString(4, user.getFirstName().trim());
            stmt2.setString(5, user.getLastName().trim());
            stmt2.setString(6, user.getEmailAddress().trim());
            stmt2.setString(7, user.getAcademicOrganization().getOrganizationName());
            stmt2.setString(8, user.getUserRole().getRoleName());
            stmt2.setString(9, user.getLastLogin().toString());
            stmt2.setString(10, user.getAttemptedLogin().toString());
            stmt2.setInt(11, user.getLoginCount());
            stmt2.setInt(12, user.getAttemptedLoginCount());
            stmt2.setString(13, "");
            stmt2.setString(14, LocalDateTime.now().toString());
            stmt2.setBoolean(15, user.locked());
            stmt2.setInt(16, user.getUserNumber());
            

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in updateUser(User user) " + user + " error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return getUserByID(user.getUserNumber());
        }

        MYSQL_Helper.returnConnection(conn);
        return getUserByID(user.getUserNumber());
    }

    /**
     * Return ture if the given user have been deleted from the database.
     * Otherwise, return false.
     *
     * @param user A <code>User</code> object which is expected to delete from
     * database
     * @return Ture if the given user have been deleted from the database
     * successfully.Otherwise, return false
     */
    @Override
    public boolean deleteUser(User user) {
        String sql = "DELETE FROM user_data WHERE userNumber =?; ";
        Connection conn = database.MYSQL_Helper.getConnection();

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setInt(1, user.getUserNumber());
            stmt2.executeUpdate();
            MYSQL_Helper.returnConnection(conn);
            return !isUser(user);

        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in deleteUser(User user) " + user + " error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return false;
        }

    }

    @Override
    public boolean deleteUserbyID(int userID) {
        User user = getUserByID(userID);
        if(user == null){ return false; }
        return deleteUser(user);
    }

    private boolean isUser(User user) {
        return getUserByLoginName(user.getLoginName()) != null;
    }

    @Override
    public boolean setForgetToken(int userID, String token) {
        String sql = "UPDATE user_data SET Token = ?, TokenTime = ? where userNumber = ? ; ";

        Connection conn = database.MYSQL_Helper.getConnection();
        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, token);
            int mins = Integer.parseInt(PropertyManager.getProperty("TokenMinutesExpire"));
            stmt2.setString(2, LocalDateTime.now().plusMinutes(mins).toString());
            stmt2.setInt(3, userID);

            stmt2.executeUpdate();

        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in setForgetToken(int userID, String token) " + userID + ", " + token + " error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return false;
        }

        MYSQL_Helper.returnConnection(conn);
        return true;
    }

    @Override
    public String getForgottenToken(int userID) {
        String sql = "SELECT Token, TokenTime FROM user_data where userNumber=?; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        String result = "";

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setInt(1, userID);
            rs = stmt2.executeQuery();
            if (rs == null) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return null;
            }
            rs.next();

            result = rs.getString("Token");

            if (rs.getString("TokenTime") == null || LocalDateTime.now().isAfter(LocalDateTime.parse(rs.getString("TokenTime")))) {
                return null;
            }

            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getForgotten(int userID) " + userID + " error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return null;
        }

        MYSQL_Helper.returnConnection(conn);

        return result;
    }

    @Override
    public Collection<User> getAllUsersWithoutSystemAdmins() {
        Collection<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user_data WHERE userRole != ?; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        User user = null;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, common.UserRole.SystemAdmin.getRoleName());
            rs = stmt2.executeQuery();
            if (rs == null) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return users;
            }
            while (rs.next()) {
                user = SQLUtility.convertResultSetToUser(rs);
                users.add(user);
            }
            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getAllUsersWithoutSystemer() error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return users;
        }

        MYSQL_Helper.returnConnection(conn);

        return users;
    }

    @Override
    public Collection<User> getAllUserSameOrganization(AcademicOrganization organization) {
        Collection<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user_data WHERE academicOrganization = ? ; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        User user = null;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, organization.getOrganizationName());

            rs = stmt2.executeQuery();
            if (rs == null) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return users;
            }
            while (rs.next()) {
                user= SQLUtility.convertResultSetToUser(rs);
                users.add(user);
            }

            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, 
                "SQLException in getAllUserSameOrganization(AcademicOrganization organization) organization=" 
                + organization + " error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return users;
        }

        MYSQL_Helper.returnConnection(conn);

        return users;
    }

    @Override
    public String getSaltByLoginName(String loginName) {
        String result = null;
        String sql = "SELECT salt FROM user_data WHERE loginName = ? ; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, loginName.trim());

            rs = stmt2.executeQuery();

            if (rs.next()) {
                result = rs.getString("salt");
            }

            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getSaltByLoginName(String loginName) loginName="
                    +loginName+" error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return null;
        }

        MYSQL_Helper.returnConnection(conn);
        return result;
    }

    @Override
    public Collection<User> getSystemAdmins() {
        Collection<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user_data WHERE userRole = ?; ";
        Connection conn = database.MYSQL_Helper.getConnection();
        ResultSet rs;
        User user = null;

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, common.UserRole.SystemAdmin.getRoleName());
            rs = stmt2.executeQuery();
            if (rs == null) {
                MYSQL_Helper.closePreparedStatement(stmt2);
                MYSQL_Helper.returnConnection(conn);
                return users;
            }
            while (rs.next()) {
                user = SQLUtility.convertResultSetToUser(rs);
                users.add(user);
            }
            MYSQL_Helper.closePreparedStatement(stmt2);
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "SQLException in getSystemAdmins() error: " + ex);
            MYSQL_Helper.returnConnection(conn);
            return users;
        }

        MYSQL_Helper.returnConnection(conn);

        return users;
    }

}