package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filter to select products within a price range
 */
public class Price implements SearchFilter
{
    private final long id;
    private final String name;
    private final String url;
    
    @JsonCreator
    public Price(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("url") String url)
    {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Override
    public String getFilterId()
    {
        return "p" + getId();
    }
    
    /**
     * Returns the unique identifier of this price range
     */
    public long getId()
    {
        return id;
    }

    /**
     * Returns the display name of this price range
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the URL of the search page on POPSUGAR Shopping displaying all the products matching
     * this price range
     */
    public String getUrl()
    {
        return url;
    }
}
