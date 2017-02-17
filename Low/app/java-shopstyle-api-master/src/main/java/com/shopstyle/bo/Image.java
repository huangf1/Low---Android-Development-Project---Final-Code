package com.shopstyle.bo;

import java.util.Map;

/**
 * Wrapper around a specific image URL, providing metadata about the actual image. For a given
 * product, the same picture will be available in different {@link #getSizes() sizes}.
 */
public class Image
{
    private String id;
    private Map<ImageSize.SizeName, ImageSize> sizes;

    /**
     * Returns the unique identifier of an image
     */
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Returns a map of all the size variations keyed by the {@link ImageSize.SizeName size name}
     */
    public Map<ImageSize.SizeName, ImageSize> getSizes()
    {
        return sizes;
    }

    public void setSizes(Map<ImageSize.SizeName, ImageSize> sizes)
    {
        this.sizes = sizes;
    }
}
