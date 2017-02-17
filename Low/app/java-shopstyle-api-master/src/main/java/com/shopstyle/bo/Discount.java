package com.shopstyle.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopstyle.api.ProductQuery;

/**
 * Filter to select products that are on sale. 
 * 
 * <p>There's one Discount object for each discount percentage 
 * from 10% to 70%:
 * <ul>
 * <li>0 - products on sale</li>
 * <li>1 - products with a discount of at least 20%</li>
 * <li>2 - products with a discount of at least 30%</li>
 * <li>3 - products with a discount of at least 40%</li>
 * <li>4 - products with a discount of at least 50%</li>
 * <li>5 - products with a discount of at least 60%</li>
 * <li>6 - products with a discount of at least 70%</li>
 * </ul>
 * </p>
 * <p>There are also 3 special discounts to select the products that had a price drop since a certain date:
 * <ul>
 * <li>100 - price drop within the past 24 hours</li>
 * <li>110 - price drop within the past 7 days</li>
 * <li>200 - price drop since a custom date specified with {@link ProductQuery#withPriceDropDate(java.sql.Date)}</li>
 * </ul>
 * </p>
 */
public class Discount implements SearchFilter
{
    private final long id;
    private final String name;
    private final String url;

    @Override
    public String getFilterId()
    {
        return "d" + getId();
    }

    @JsonCreator
    public Discount(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("url") String url)
    {
        this.id = id;
        this.name = name;
        this.url = url;
    }
    
    /**
     * Returns the unique identifier of this discount filter
     */
    public long getId()
    {
        return id;
    }

    /**
     * Returns the display name of this discount filter
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the URL of the search page on POPSUGAR Shopping displaying all the products matching
     * this discount filter
     */
    public String getUrl()
    {
        return url;
    }
}
