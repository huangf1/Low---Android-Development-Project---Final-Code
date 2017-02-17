package com.shopstyle.api;

import com.shopstyle.bo.Product;

public class ProductSearchResponse
{
    private ProductSearchMetadata metadata;
    private Product[] products;

    public ProductSearchMetadata getMetadata()
    {
        return metadata;
    }
    
    public void setMetadata(ProductSearchMetadata metadata)
    {
        this.metadata = metadata;
    }
    
    public Product[] getProducts()
    {
        return products;
    }

    public void setProducts(Product[] products)
    {
        this.products = products;
    }

}
