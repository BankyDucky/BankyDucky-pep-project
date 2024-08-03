package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /* 
     * Register account with given account object
     * Returns account info on SUCCESS
     * Returns null on FAILURE
     */
    public Account registerAccount(Account account){
        if(account.getUsername().length() <= 0){
            return null;
        }
        if(account.getPassword().length() < 4){
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    /*
     * Check if login info matches with data in database
     * Returns account info on SUCCESS
     * Returns null on FAILURE
     */
    public Account loginAccount(Account account){
        return accountDAO.getAccount(account);
    }

}
