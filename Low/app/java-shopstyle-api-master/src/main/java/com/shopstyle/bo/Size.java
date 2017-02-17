package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Normalized size of a product. There are different set of sizes for different categories
 */
public class Size implements SearchFilter
{
    private final String id;
    private final String name;
    private final String url;

    @JsonCreator
    public Size(@JsonProperty("id") String id, @JsonProperty("name") String name, 
        @JsonProperty("url") String url)
    {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Override
    public String getFilterId()
    {
        return "s" + getId();
    }

    /**
     * Returns the unique identifier of this size
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns the display name of this size
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the URL of the search page on POPSUGAR Shopping displaying
     * all the products with this size
     */
    public String getUrl()
    {
        return url;
    }
}
