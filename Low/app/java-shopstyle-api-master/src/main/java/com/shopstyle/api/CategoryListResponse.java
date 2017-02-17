package com.shopstyle.api;

import com.shopstyle.bo.Category;

public class CategoryListResponse
{
    private Category[] categories;

    public Category[] getCategories()
    {
        return categories;
    }

    public void setCategories(Category[] categories)
    {
        this.categories = categories;
    }
}
