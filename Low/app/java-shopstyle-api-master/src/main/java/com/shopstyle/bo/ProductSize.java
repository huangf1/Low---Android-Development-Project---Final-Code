package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Size-variant as given by the retailer. The normalized size is available via
 * {@link #getCanonicalSize()}
 */
public class ProductSize
{
    private final String name;
    private final Size canonicalSize;

    @JsonCreator
    public ProductSize(@JsonProperty("name") String name, @JsonProperty("canonicalSize") Size canonicalSize)
    {
        this.name = name;
        this.canonicalSize = canonicalSize;
    }
    
    /**
     * Returns the retailer's name for this size
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the normalized representation of this size
     */
    public Size getCanonicalSize()
    {
        return canonicalSize;
    }
}
