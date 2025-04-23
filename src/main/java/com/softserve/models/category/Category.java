package com.softserve.models.category;

import com.softserve.models.transaction.TransactionType;

public interface Category {

    String getCategoryName();
    TransactionType getCategoryType();

}
