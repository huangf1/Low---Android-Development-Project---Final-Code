package com.shopstyle.api;

import com.shopstyle.bo.Brand;

public class BrandListResponse
{
    private Brand[] brands;

    public Brand[] getBrands()
    {
        return brands;
    }

    public void setBrands(Brand[] brands)
    {
        this.brands = brands;
    }

}
