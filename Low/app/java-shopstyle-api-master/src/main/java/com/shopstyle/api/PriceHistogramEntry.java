package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shopstyle.bo.Price;

public class PriceHistogramEntry extends HistogramEntry
{
    @JsonUnwrapped
    private Price price;
    
    public Price getPrice()
    {
        return price;
    }
    
    public void setPrice(Price price)
    {
        this.price = price;
    }
}
