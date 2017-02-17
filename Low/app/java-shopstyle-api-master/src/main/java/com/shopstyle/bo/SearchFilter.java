package com.shopstyle.bo;

/**
 * Common interface to the objects used to refine a product search
 */
public interface SearchFilter
{
    /**
     * Returns the ID to use in a search query. This ID is made of a single letter followed by the
     * unique identifier of the filter. The letter identifies the type of filter (r for Retailer, b
     * for Brand, ...)
     */
    public String getFilterId();
}
