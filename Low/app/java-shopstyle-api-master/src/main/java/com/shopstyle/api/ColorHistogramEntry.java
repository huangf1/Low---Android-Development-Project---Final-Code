package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shopstyle.bo.Color;

public class ColorHistogramEntry extends HistogramEntry
{
    @JsonUnwrapped
    private Color color;
    
    public Color getColor()
    {
        return color;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }

}
