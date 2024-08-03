package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account insertAccount(Account newUser){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username,password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,newUser.getUsername());
            preparedStatement.setString(2,newUser.getPassword());
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();
            if(result.next()){
                int id = (int) result.getLong(1);
                return new Account(id,newUser.getUsername(),newUser.getPassword());
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Account getAccountByID(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,account.getAccount_id());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                int id = result.getInt(1);
                String username = result.getString(2);
                String password = result.getString(3);
                return new Account(id,username,password);
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Account getAccount(Account User){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=? AND password=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,User.getUsername());
            preparedStatement.setString(2,User.getPassword());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                int id = result.getInt(1);
                return new Account(id,User.getUsername(),User.getPassword());
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
