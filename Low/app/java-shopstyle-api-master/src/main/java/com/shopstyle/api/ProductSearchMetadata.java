package com.shopstyle.api;

import com.shopstyle.bo.Category;


public class ProductSearchMetadata extends PaginatedMetadata
{
    private Category category;

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }
}
