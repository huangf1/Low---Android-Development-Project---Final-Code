package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shopstyle.bo.Retailer;

public class RetailerHistogramEntry extends HistogramEntry
{
    @JsonUnwrapped
    private Retailer retailer;
    
    public Retailer getRetailer()
    {
        return retailer;
    }
    
    public void setRetailer(Retailer retailer)
    {
        this.retailer = retailer;
    }
}
