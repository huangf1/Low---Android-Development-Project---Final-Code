java-shopstyle-api
==========================

## Usage

This package is currently hosted on clojars. To use with Maven, add the clojars resolver:
```xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>
```
Then add the depdency
```xml
<dependency>
  <groupId>com.shopstyle</groupId>
  <artifactId>shopstyle-api-client</artifactId>
  <version>0.1</version>
</dependency>
```

## Example

Java ShopStyle API.

Sample code:

    ShopStyle api = new ShopStyle("YOUR_KEY");
    ProductQuery query = new ProductQuery().withFreeText("red dresses");
    ProductSearchResponse response = api.getProducts(query);
    for (Product product : response.getProducts()) {
        System.out.println(product.getName());
    }

    ProductHistogramResponse histograms = api.getProductsHistogram(query, Category.class, Retailer.class);
    CategoryHistogramEntry[] categoryHistogram = histograms.getCategoryHistogram();
    RetailerHistogramEntry[] retailerHistogram = histograms.getRetailerHistogram();
