package com.gmail.at.sichyuriyy.onlinestore.persistance.transaction;

import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;

/**
 * Created by Yuriy on 4/24/2017.
 */
public class TransactionManager {

    private ConnectionManager cm;

    public TransactionManager(ConnectionManager cm) {
        this.cm = cm;
    }

    public void tx(Transaction tx) {
        Transaction.tx(cm, tx);
    }
}
