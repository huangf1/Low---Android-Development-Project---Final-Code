package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shopstyle.bo.Brand;

public class BrandHistogramEntry extends HistogramEntry
{
    @JsonUnwrapped
    private Brand brand;

    public Brand getBrand()
    {
        return brand;
    }

    public void setBrand(Brand brand)
    {
        this.brand = brand;
    }
}
