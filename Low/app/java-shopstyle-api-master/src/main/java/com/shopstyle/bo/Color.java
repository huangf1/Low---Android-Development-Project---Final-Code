package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Normalized color of a product
 */
public class Color implements SearchFilter
{
    private final long id;
    private final String name;
    private final String url;

    @JsonCreator
    public Color(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("url") String url)
    {
        this.id = id;
        this.name = name;
        this.url = url;
    }
    
    @Override
    public String getFilterId()
    {
        return "c" + getId();
    }
    
    /**
     * Returns the unique identifier of this color
     */
    public long getId()
    {
        return id;
    }

    /**
     * Returns the display name of this color
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the URL of the search page on POPSUGAR Shopping displaying
     * all the products with this color
     */
    public String getUrl()
    {
        return url;
    }
}
