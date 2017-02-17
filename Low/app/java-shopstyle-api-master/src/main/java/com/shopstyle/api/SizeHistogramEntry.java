package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shopstyle.bo.Size;

public class SizeHistogramEntry extends HistogramEntry
{
    @JsonUnwrapped
    private Size size;
    
    public Size getSize()
    {
        return size;
    }
    public void setSize(Size size)
    {
        this.size = size;
    }
}
