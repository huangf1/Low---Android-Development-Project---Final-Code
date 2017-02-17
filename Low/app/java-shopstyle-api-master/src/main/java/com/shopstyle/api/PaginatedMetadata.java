package com.shopstyle.api;

public class PaginatedMetadata {

    private int offset;
    private int limit;
    private int total;

    public int getOffset()
    {
        return offset;
    }

    public int getLimit()
    {
        return limit;
    }

    public int getTotal()
    {
        return total;
    }

    public PageRequest getNextPageRequest()
    {
    	if (offset + limit >= total) {
    		return null;
    	}
    	else {
    		return new PageRequest().withOffset(offset + limit).withLimit(limit);
    	}
    }
}
