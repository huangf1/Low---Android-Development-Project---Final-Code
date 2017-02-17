package com.shopstyle.api;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PageRequest {

    private int offset;
    private int limit;

    public PageRequest() {
        offset = 0;
        limit = -1;
    }

    public PageRequest(int limit)
    {
        offset = 0;
        this.limit = limit;
    }

    public int getOffset()
    {
        return offset;
    }

    public PageRequest withOffset(int offset)
    {
        this.offset = offset;
        return this;
    }

    public int getLimit()
    {
        return limit;
    }

    public PageRequest withLimit(int limit)
    {
        this.limit = limit;
        return this;
    }

    public void addParameters(List<NameValuePair> parameters)
    {
        if (offset > 0) {
            parameters.add(new BasicNameValuePair("offset", String.valueOf(offset)));
        }
        if (limit > -1) {
            parameters.add(new BasicNameValuePair("limit", String.valueOf(limit)));
        }
    }
}
