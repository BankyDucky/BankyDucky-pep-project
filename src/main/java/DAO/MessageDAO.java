package DAO;

import java.sql.*;
import java.util.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    

    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();
            if(result.next()){
                int id = (int) result.getLong(1);
                return new Message(id,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public List<Message> getMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> returnArray = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                int id = result.getInt("message_id");
                int posted_by = result.getInt("posted_by");
                String message_text = result.getString("message_text");
                long time_posted_epoch = result.getLong("time_posted_epoch");
                returnArray.add(new Message(id,posted_by,message_text,time_posted_epoch));
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return returnArray;
    }

    public Message getMessageByID(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,message.getMessage_id());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                int id = result.getInt("message_id");
                int posted_by = result.getInt("posted_by");
                String message_text = result.getString("message_text");
                long time_posted_epoch = result.getLong("time_posted_epoch");
                return new Message(id,posted_by,message_text,time_posted_epoch);
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Message deleteMessageByID(Message message){
        Message messageToDelete = this.getMessageByID(message);
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,message.getMessage_id());
            preparedStatement.executeUpdate();
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return messageToDelete;
    }

    public Message patchMessageByID(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text=? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,message.getMessage_text());
            preparedStatement.setInt(2,message.getMessage_id());
            preparedStatement.executeUpdate();
            return getMessageByID(message);
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public List<Message> getMessagesByAccount(Message message){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> returnArray = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.posted_by);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                int id = result.getInt("message_id");
                int posted_by = result.getInt("posted_by");
                String message_text = result.getString("message_text");
                long time_posted_epoch = result.getLong("time_posted_epoch");
                returnArray.add(new Message(id,posted_by,message_text,time_posted_epoch));
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return returnArray;
    }
}

