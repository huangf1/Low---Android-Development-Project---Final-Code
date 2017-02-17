package com.shopstyle.api;

/**
 * Enum of the different sort algorithms.
 */
public enum ProductSort {
    /**
     * Sort by price in ascending order
     */
    PriceLoHi, 
    
    /**
     * Sort by price in descending order
     */
    PriceHiLo, 
    
    /**
     * Sort by popularity
     */
    Popular, 
    
    /**
     * Sort by the date the products were added to the database
     */
    Recency, 
    
    /**
     * Default sort algorithm
     */
    Relevance
}