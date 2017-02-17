package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Categories are organized within a tree. Each category points to its parent categories except the
 * root one. For instance, 'dresses' is the parent of 'black-dresses', 'cocktail-dresses',
 * 'day-dresses' and 'evening-dresses'.
 */
public class Category
{
    private final String id;
    private final String name;
    private final String parentId;

    @JsonCreator
    public Category(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("parentId") String parentId)
    {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
    
    /**
     * Returns the unique identifier of this category
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns the display name of this category.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the identifier of the parent category.
     * @return
     */
    public String getParentId()
    {
        return parentId;
    }
}
