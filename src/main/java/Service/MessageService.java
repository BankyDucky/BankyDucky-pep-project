package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    /*
     * Inserts a message while checking proper message parameters and if user exists
     * Null on FAILURE
     * Message info on SUCCESS
     */
    public Message insertMessage(Message message){
        int messageLength = message.getMessage_text().trim().length();
        if(messageLength <= 0 || messageLength > 255){
            return null;
        }
        Account account = accountDAO.getAccountByID(new Account(message.getPosted_by(),"",""));
        if(account == null){
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    /*
     * Obtains all messages
     * Returns List of messages or Empty List
     */
    public List<Message> getAllMessages(){
        return messageDAO.getMessages();
    }

    /*
     * Obtain a message by its ID
     * Returns null if FAILURE
     * Returns message on SUCCESS
     */
    public Message getMessageByID(Message message){
        return messageDAO.getMessageByID(message);
    }

    /*
     * Deletes a message by its ID
     * Returns null on FAILURE
     * Returns deleted message data on SUCCESS
     */
    public Message deleteMessageByID(Message message){
        return messageDAO.deleteMessageByID(message);
    }

    /*
     * Patches a message by its ID
     * Returns null on FAILURE
     * Returns deleted message data on SUCCESS
     */
    public Message patchMessageByID(Message message){
        int messageLength = message.getMessage_text().trim().length();
        if(messageLength <= 0 || messageLength > 255){
            return null;
        }
        return messageDAO.patchMessageByID(message);
    }

    /*
     * Obtains all messages by AccountID
     * Returns List of Messages or Empty List
     */
    public List<Message> getAllMessagesByAccountID(Message message){
        return messageDAO.getMessagesByAccount(message);
    }
}
