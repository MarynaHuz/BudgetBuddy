package com.softserve;

import com.softserve.controllers.AccountController;
import com.softserve.controllers.TransactionController;
import com.softserve.dao.impl.AccountDao;
import com.softserve.dao.impl.TransactionDao;
import com.softserve.services.AccountService;
import com.softserve.services.TransactionService;
import com.softserve.ui.AccountMenu;
import com.softserve.ui.MainMenu;
import com.softserve.ui.Menu;
import com.softserve.ui.TransactionMenu;

public class BudgetBuddyApp {

    public static void main(String[] args) {
        AccountDao accountDao = new AccountDao();
        TransactionDao transactionDao = new TransactionDao();

        AccountService accountService = new AccountService(accountDao);
        TransactionService transactionService = new TransactionService(transactionDao,accountService);

        AccountController accountController = new AccountController(accountService);
        TransactionController transactionController = new TransactionController(transactionService);

        Menu accountMenu = new AccountMenu(accountController);
        Menu transactionMenu = new TransactionMenu(transactionController);
        MainMenu mainMenu = new MainMenu(accountMenu, transactionMenu);

        mainMenu.show();
    }
}