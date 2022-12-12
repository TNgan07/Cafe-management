/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import connect_orcle.DBUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main_class.Account;

/**
 *
 * @author LOAN
 */
public class Account_Cnn {
    private static Account_Cnn instance;
    Account account = new Account();

    public Account_Cnn() {
    }
    public static Account_Cnn getInstance() {
        if (instance == null) {
            instance = new Account_Cnn();
        }
        return instance;
    }
    public static void setInstance(Account_Cnn instance) {
        Account_Cnn.instance = instance;
    }
    // Kiểm tra đăng nhập
    public Boolean login (String username, String password){
        
        try {
            Connection con = DBUtility.openConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM account WHERE username = ? AND pass = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                account.setId(rs.getInt(1));
                account.setUsername(rs.getString(2));
                account.setPassword(rs.getString(3));
                account.setName(rs.getString(4));
                System.out.println(rs);
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Account_Cnn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Account getAccount() {
        return account;
    }
    // Danh sách tài khoản
    public List<Account>  listAccount (){
        List<Account> list = new ArrayList<Account>();
        try {
            Connection con = DBUtility.openConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT ID, username, pass, name FROM account");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Account account = new Account(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4));
                list.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Account_Cnn.class.getName()).log(Level.SEVERE, null, ex);
        }
       return list; 
    }
    //Thêm tài khoản
    public Boolean add(String name, String username, String pass) {
       
        try {
            Connection con = DBUtility.openConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO account(id, username, pass, name) VALUES (?,?,?,?)");
            pstmt.setInt(1, maxID());
            pstmt.setString(2, username);
            pstmt.setString(3, pass);
            pstmt.setString(4, name);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Account_Cnn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //Cập nhật tài khoản
    public Boolean Update(int id, String name, String  password){
        try {
            Connection con = DBUtility.openConnection();
            PreparedStatement pstmt = con.prepareStatement("UPDATE account SET pass=?,name=? WHERE ID=?");
            pstmt.setString(1, password);
            pstmt.setString(2, name);
            pstmt.setInt(3, id);
            int i = pstmt.executeUpdate();
            if (i>0){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Account_Cnn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    // Xóa tài khoản
    public Boolean Delete(int id) {
        try {
            Connection con = DBUtility.openConnection();
            PreparedStatement pstmt = con.prepareStatement("Delete from account where ID=?");
            pstmt.setInt(1, id);
            int i = pstmt.executeUpdate();
            if (i>0){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Account_Cnn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    // đổi mật khẩu
    public Boolean changePassword(int id, String pass){
        
        try {
            Connection con = DBUtility.openConnection();
            PreparedStatement pstmt = con.prepareStatement("UPDATE account SET pass=? WHERE ID=?");
            pstmt.setString(1, pass);
            pstmt.setInt(2, id);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Account_Cnn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public int maxID(){
        int maxid = 0;
        try {
            Connection con = DBUtility.openConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM account");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (maxid < rs.getInt(1)){
                    maxid = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Drinks_Cnn.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return maxid + 1;
    }
}
