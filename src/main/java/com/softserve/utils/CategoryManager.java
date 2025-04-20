package com.softserve.utils;

import com.softserve.models.category.Category;
import com.softserve.models.category.ExpenseCategory;
import com.softserve.models.category.IncomeCategory;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryManager {

    private CategoryManager() {
    }

    private static final Map<String, Category> CATEGORY_LOOKUP;

    static {
        CATEGORY_LOOKUP = Stream.concat(
                Arrays.stream(ExpenseCategory.values()),
                Arrays.stream(IncomeCategory.values())
        ).collect(Collectors.toMap(
                c -> c.getCategoryName().toUpperCase(),
                Function.identity()));
    }
}
