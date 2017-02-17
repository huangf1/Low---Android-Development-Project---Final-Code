package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shopstyle.bo.Discount;

public class DiscountHistogramEntry extends HistogramEntry
{
    @JsonUnwrapped
    private Discount discount;
    
    public Discount getDiscount()
    {
        return discount;
    }
    
    public void setDiscount(Discount discount)
    {
        this.discount = discount;
    }
}
