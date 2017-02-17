package com.shopstyle.api;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.shopstyle.bo.Category;
import com.shopstyle.bo.SearchFilter;

public class ProductQuery
{
    private String categoryId;
    private List<String> textSearches = new ArrayList<String>(1);
    private List<String> filters = new ArrayList<String>(3);
    private long priceDropDate;

    public ProductQuery withCategory(Category category)
    {
        if (category != null) {
            this.categoryId = category.getId();
        }
        else {
            this.categoryId = null;
        }
        return this;
    }
    
    public ProductQuery withCategory(String categoryId)
    {
        if (categoryId != null && categoryId.length() > 0) {
            this.categoryId = categoryId;
        }
        else {
            this.categoryId = null;
        }
        return this;
    }
    
    public ProductQuery withFreeText(String text)
    {
        if (text != null && text.length() > 0) {
            textSearches.add(text);
        }
        return this;
    }
    
    public ProductQuery withFilter(SearchFilter filter)
    {
        if (filter != null) {
            filters.add(filter.getFilterId());
        }
        return this;
    }

    public ProductQuery withFilter(String filterId)
    {
        if (filterId != null & filterId.length() > 0) {
            filters.add(filterId);
        }
        return this;
    }
    
    public ProductQuery withPriceDropDate(Date date)
    {
        if (date != null) {
            priceDropDate = date.getTime();
        }
        return this;
    }

    public ProductQuery withPriceDropDate(long timestamp)
    {
        if (timestamp > 0) {
            priceDropDate = timestamp;
        }
        return this;
    }
    
    void addParameters(List<NameValuePair> parameters)
    {
        if (categoryId != null) {
            parameters.add(new BasicNameValuePair("cat", categoryId));
        }
        for (String text : textSearches) {
            parameters.add(new BasicNameValuePair("fts", text));
        }
        for (String filterId : filters) {
            parameters.add(new BasicNameValuePair("fl", filterId));
        }
        if (priceDropDate > 0) {
            parameters.add(new BasicNameValuePair("fl", "d200"));
            parameters.add(new BasicNameValuePair("pdd", String.valueOf(priceDropDate)));
        }
    }
}