package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Brand implements SearchFilter
{
    private final long id;
    private final String name;
    private final String url;

    @JsonCreator
    public Brand(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("url") String url)
    {
        this.id = id;
        this.name = name;
        this.url = url;
    }
    
    @Override
    public String getFilterId()
    {
        return "b" + getId();
    }

    /**
     * Returns the unique identifier of the brand
     */
    public long getId()
    {
        return id;
    }

    /**
     * Returns the display name of the brand
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the URL of the search page on POPSUGAR Shopping displaying all the products from that
     * brand
     */
    public String getUrl()
    {
        return url;
    }
}
