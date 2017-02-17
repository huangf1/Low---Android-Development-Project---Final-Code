package com.shopstyle.bo;

public class User
{
    private String id;
    private String handle;
    private String description;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getHandle()
    {
        return handle;
    }

    public void setHandle(String handle)
    {
        this.handle = handle;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        if (handle == null) {
            return "<anon>:" + id;
        }
        else {
            return handle + ":" + id;
        }
    }
}
