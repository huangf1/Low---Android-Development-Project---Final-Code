package com.shopstyle.api;

import com.shopstyle.bo.Retailer;

public class RetailerListResponse
{
    private Retailer[] retailers;
    
    public Retailer[] getRetailers()
    {
        return retailers;
    }
    
    public void setRetailers(Retailer[] retailers)
    {
        this.retailers = retailers;
    }
}
