package com.softserve.models.category;

import java.math.BigDecimal;

public interface Category {

    String getCategoryName();
    boolean isValidAmount(BigDecimal amount);
    CategoryType getCategoryType();
}
