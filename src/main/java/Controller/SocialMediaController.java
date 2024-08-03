package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private MessageService messageService;
    private AccountService accountService;

    public SocialMediaController(){
        messageService = new MessageService();
        accountService = new AccountService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("messages",this::getMessages);
        app.get("messages/{message_id}",this::getMessageById);
        app.get("accounts/{account_id}/messages",this::getMessagesByAccountId);
        app.post("register", this::registerAccount);
        app.post("login",this::loginAccount);
        app.post("messages",this::postMessage);
        app.patch("messages/{message_id}",this::updateMessageById);
        app.delete("messages/{message_id}",this::deleteMessageByID);
        return app;
    }

    private void getMessages(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200);
        ctx.json(messages);
    }

    private void postMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        Message addedMessage = messageService.insertMessage(message);
        if(addedMessage != null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
        else{
            ctx.status(400);
        }
    }

    private void registerAccount(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.registerAccount(account);
        if(createdAccount != null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(createdAccount));
        }
        else{
            ctx.status(400);
        }
    }
    private void loginAccount(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginChecker = accountService.loginAccount(account);
        if(loginChecker == null){
            ctx.status(401);
        }
        else{
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(loginChecker));
        }
    }

    private void getMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = new Message(id,0,"",0);
        Message obtainedMessage = messageService.getMessageByID(message);
        ctx.status(200);
        if(obtainedMessage != null){
            ctx.json(mapper.writeValueAsString(obtainedMessage));
        }
        else{
            ctx.json("");
        }

    }
    
    private void getMessagesByAccountId(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        Message message = new Message(0,id,"",0);
        List<Message> obtainedMessage = messageService.getAllMessagesByAccountID(message);
        ctx.status(200);
        ctx.json(obtainedMessage);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void deleteMessageByID(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = new Message(id,0,"",0);
        Message obtainedMessage = messageService.getMessageByID(message);
        ctx.status(200);
        if(obtainedMessage != null){
            ctx.json(mapper.writeValueAsString(obtainedMessage));
        }
        else{
            ctx.json("");
        }
    }

    private void updateMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(),Message.class);
        message.setMessage_id(id);
        Message obtainedMessage = messageService.patchMessageByID(message);
        if(obtainedMessage != null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(obtainedMessage));
        }
        else{
            ctx.json("");
            ctx.status(400);
        }
    }


}