package com.shopstyle.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopstyle.bo.Favorite;
import com.shopstyle.bo.User;

public class FavoriteListResponse
{
    private Metadata metadata;
    private Favorite[] favorites;

    public Metadata getMetadata()
    {
        return metadata;
    }

    public Favorite[] getFavorites()
    {
        return favorites;
    }

    public static class Metadata extends PaginatedMetadata
    {
        @JsonProperty
        private User user;

        public User getUser()
        {
            return user;
        }
    }
}
