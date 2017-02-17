package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Retailer implements SearchFilter
{
    private final long id;
    private final String name;
    private final String url;
    private final boolean deeplinkSupport;

    @JsonCreator
    public Retailer(@JsonProperty("id") long id, @JsonProperty("name") String name, 
        @JsonProperty("url") String url, @JsonProperty("deeplinkSupport") boolean deeplink)
    {
        this.id = id;
        this.name = name;
        this.url = url;
        this.deeplinkSupport = deeplink;
    }
    
    @Override
    public String getFilterId()
    {
        return "r" + getId();
    }
  
    /**
     * Returns the unique identifier of this retailer
     */
    public long getId()
    {
        return id;
    }

    /**
     * Returns the display name of this retailer
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the URL of the search page on POPSUGAR Shopping displaying
     * all the products sold by this retailer
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Specifies whether deeplinking is supported by this retailer. Deeplinking allows clicking to a
     * retailer's page without having to find the product within the POPSUGAR Shopping results
     */
    public boolean isDeeplinkSupport()
    {
        return deeplinkSupport;
    }
}
