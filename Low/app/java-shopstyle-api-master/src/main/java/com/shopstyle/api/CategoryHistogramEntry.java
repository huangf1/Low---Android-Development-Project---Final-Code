package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shopstyle.bo.Category;

public class CategoryHistogramEntry extends HistogramEntry
{
    @JsonUnwrapped
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
