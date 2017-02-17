package com.shopstyle.api;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopstyle.bo.Category;
import com.shopstyle.bo.Product;
import com.shopstyle.bo.User;

public class ShopStyle
{

    /**
     * Default version of the API end point
     */
    public static final int DEFAULT_VERSION = 2;

    /**
     * Hostname for the POPSUGAR Shopping US end point
     */
    public static final String US_API_HOSTNAME = "api.shopstyle.com";

    /**
     * Hostname for the POPSUGAR Shopping UK end point
     */
    public static final String UK_API_HOSTNAME = "api.shopstyle.co.uk";

    /**
     * Hostname for the POPSUGAR Shopping Germany end point
     */
    public static final String DE_API_HOSTNAME = "api.shopstyle.de";

    /**
     * Hostname for the POPSUGAR Shopping France end point
     */
    public static final String FR_API_HOSTNAME = "api.shopstyle.fr";

    /**
     * Hostname for the POPSUGAR Shopping Japan end point
     */
    public static final String JP_API_HOSTNAME = "api.shopstyle.co.jp";

    /**
     * Hostname for the POPSUGAR Shopping Australia end point
     */
    public static final String AU_API_HOSTNAME = "api.shopstyle.com.au";

    /**
     * Hostname for the POPSUGAR Shopping Canada end point
     */
    public static final String CA_API_HOSTNAME = "api.shopstyle.ca";

    /**
     * Constant to use with {@link ShopStyle#setLocales(String[])} to request the results for all
     * the possible locales.
     */
    public static final String[] ALL_LOCALES = { "all" };

    protected static final Charset UTF8Charset = Charset.forName("UTF-8");
    protected static final ContentType JSONContentType = ContentType.APPLICATION_JSON;

    private final String scheme = "http";
    private final String host;
    private int port = 80;
    private final int connectionPoolSize = 100;
    private final String pathPrefix;
    private final String partnerId;

    private HttpClient httpClient;
    private String[] locales;
    private ObjectMapper mapper;
    private String secretKey;

    public ShopStyle(String partnerId)
    {
        this(partnerId, US_API_HOSTNAME, DEFAULT_VERSION);
    }

    public ShopStyle(String partnerId, String hostname)
    {
        this(partnerId, hostname, DEFAULT_VERSION);
    }

    public ShopStyle(String partnerId, String hostname, int version)
    {
        this.partnerId = partnerId;
        host = hostname;
        pathPrefix = "/api/v" + version;
        configure();
    }

    private void configure()
    {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(connectionPoolSize);
        httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .build();
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void close()
    {
        if (httpClient != null) {
            ClientConnectionManager connectionManager = httpClient.getConnectionManager();
            if (connectionManager != null) {
                connectionManager.shutdown();
            }
            httpClient = null;
        }
        if (mapper != null) {
            mapper = null;
        }
    }

    /**
     * Changes the HTTP port used by the client. Default is 80
     *
     * @param newPort the new port number to use by this API clients
     */
    public void setPort(int newPort)
    {
        if (newPort <= 0) {
            throw new IllegalArgumentException("Invalid port: " + newPort);
        }
        port = newPort;
    }

    /**
     * Sets the list of locales of the results to be returned by the API calls.
     *
     * For the api calls made to the non-US end point, the default behavior is to only return the
     * results that match the locale of the end point. However one can also retrieve the results for
     * other locales as well. For instance, by default {@link #getProducts(ProductQuery)} will only
     * return canadian products if the end point is {@link #CA_API_HOSTNAME canadian one}. But if
     * the locales include "en_US" the US products available via this end point will also be
     * returned.
     *
     * To retrieve all the available products, one can use {@link #ALL_LOCALES}.
     */
    public void setLocales(String[] locales)
    {
        if (locales == null || locales.length == 0) {
            this.locales = null;
        }
        this.locales = locales;
    }

    /**
     * Sets the secret key used to authenticate secure API Calls
     */
    public void setSecretKey(String secretKey)
    {
        this.secretKey = secretKey;
    }

    public Product getProduct(long productId) throws APIException
    {
        return callGet("/products/" + productId, null, Product.class);
    }

    public ProductSearchResponse getProducts(ProductQuery request) throws APIException
    {
        return getProducts(request, null, null);
    }

    public ProductSearchResponse getProducts(ProductQuery request, PageRequest page)
        throws APIException
    {
        return getProducts(request, page, null);
    }

    public ProductSearchResponse getProducts(ProductQuery query, PageRequest page, ProductSort sort)
        throws APIException
    {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (page != null) {
            page.addParameters(parameters);
        }
        if (sort != null && sort != ProductSort.Relevance) {
            parameters.add(new BasicNameValuePair("sort", sort.name()));
        }
        query.addParameters(parameters);
        return callGet("/products", parameters, ProductSearchResponse.class);
    }

    public ProductHistogramResponse getProductsHistogram(ProductQuery query, Class... filters)
        throws APIException
    {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        StringBuilder filtersString = new StringBuilder();
        for (Class filter : filters) {
            if (filtersString.length() > 0) {
                filtersString.append(',');
            }
            filtersString.append(filter.getSimpleName());
        }
        parameters.add(new BasicNameValuePair("filters", filtersString.toString()));
        query.addParameters(parameters);
        return callGet("/products/histogram", parameters, ProductHistogramResponse.class);
    }

    // ----------------------
    // ----- Brand APIs -----
    // ----------------------

    public BrandListResponse getBrands() throws APIException
    {
        return callGet("/brands", null, BrandListResponse.class);
    }

    // -------------------------
    // ----- Category APIs -----
    // -------------------------

    public CategoryListResponse getCategories(Category root, int depth) throws APIException
    {
        return getCategories(root == null ? null : root.getId(), depth);
    }

    public CategoryListResponse getCategories(String rootId, int depth) throws APIException
    {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (rootId != null) {
            parameters.add(new BasicNameValuePair("cat", rootId));
        }
        if (depth > 0) {
            parameters.add(new BasicNameValuePair("depth", String.valueOf(depth)));
        }
        return callGet("/categories", parameters, CategoryListResponse.class);
    }

    // ----------------------
    // ----- Color APIs -----
    // ----------------------

    public ColorListResponse getColors() throws APIException
    {
        return callGet("/colors", null, ColorListResponse.class);
    }

    // -------------------------
    // ----- Retailer APIs -----
    // -------------------------

    public RetailerListResponse getRetailers() throws APIException
    {
        return callGet("/retailers", null, RetailerListResponse.class);
    }

    // ---------------------
    // ----- Size APIs -----
    // ---------------------

    public SizeListResponse getSizes(Category category) throws APIException
    {
        assert category != null;
        return getSizes(category.getId());
    }

    public SizeListResponse getSizes(String categoryId) throws APIException
    {
        assert categoryId != null;
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("cat", categoryId));
        return callGet("/sizes", parameters, SizeListResponse.class);
    }

    // ---------------------
    // ----- User APIs -----
    // ---------------------

    public User getUser(String userId) throws APIException
    {
        return callGet("/users/" + userId, null, User.class);
    }

    // -------------------------
    // ----- Favorite APIs -----
    // -------------------------

    public FavoriteListResponse getFavorites(User user, PageRequest page) throws APIException
    {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("userId", user.getId()));
        if (page != null) {
            page.addParameters(parameters);
        }
        return callGet("/favorites", parameters, FavoriteListResponse.class);
    }

    // ------------------------
    // ----- Partner APIs -----
    // ------------------------

    public void downloadTransactions(Date startDate, Date endDate, File destination)
        throws APIException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("start", dateFormat.format(startDate)));
        parameters.add(new BasicNameValuePair("end", dateFormat.format(endDate)));
        download("/partner/transactions", parameters, destination);
    }

    protected <T> T callGet(String requestPath, List<NameValuePair> parameters,
        Class<T> responseType) throws APIException
    {
        URI uri = getCallURI(requestPath, parameters);
        HttpGet get = new HttpGet(uri);
        return call(get, responseType);
    }

    protected <T> T secureCallGet(String requestPath, List<NameValuePair> parameters,
        Class<T> responseType, String userId) throws APIException
    {
        URI uri = getCallURI(requestPath, parameters);
        HttpGet get = new HttpGet(uri);
        addSecureHeader(get, userId, null);
        return call(get, responseType);
    }

    protected <T> T callPost(String requestPath, List<NameValuePair> queryParameters,
        Object postDetails, Class<T> responseType) throws APIException
    {
        URI uri = getCallURI(requestPath, queryParameters);
        HttpPost post = new HttpPost(uri);
        addJsonEntity(post, postDetails);
        return call(post, responseType);
    }

    protected <T> T secureCallPost(String requestPath, List<NameValuePair> queryParameters,
        Object postDetails, Class<T> responseType, String userId) throws APIException
    {
        URI uri = getCallURI(requestPath, queryParameters);
        HttpPost post = new HttpPost(uri);
        String jsonBody = addJsonEntity(post, postDetails);
        addSecureHeader(post, userId, jsonBody);
        return call(post, responseType);
    }

    protected <T> T callPut(String requestPath, List<NameValuePair> queryParameters,
        Object postDetails, Class<T> responseType) throws APIException
    {
        URI uri = getCallURI(requestPath, queryParameters);
        HttpPut put = new HttpPut(uri);
        addJsonEntity(put, postDetails);
        return call(put, responseType);
    }

    protected <T> T secureCallPut(String requestPath, List<NameValuePair> queryParameters,
        Object postDetails, Class<T> responseType, String userId) throws APIException
    {
        URI uri = getCallURI(requestPath, queryParameters);
        HttpPut put = new HttpPut(uri);
        String jsonBody = addJsonEntity(put, postDetails);
        addSecureHeader(put, userId, jsonBody);
        return call(put, responseType);
    }

    protected void secureCallDelete(String requestPath, List<NameValuePair> queryParameters,
        String userId) throws APIException
    {
        URI uri = getCallURI(requestPath, queryParameters);
        HttpDelete delete = new HttpDelete(uri);
        addSecureHeader(delete, userId, null);
        call(delete, null);
    }

    private String getAuthorizationToken(String userId, String formattedDate)
    {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA");
            hasher.update(Hex.decodeHex(secretKey.toCharArray()));
            hasher.update(partnerId.getBytes(UTF8Charset));
            if (userId != null) {
                hasher.update(userId.getBytes(UTF8Charset));
            }
            hasher.update(formattedDate.getBytes(UTF8Charset));
            String token = new String(Base64.encodeBase64(hasher.digest()));
            if (userId == null) {
                return token;
            }
            else {
                return userId + ":" + token;
            }
        }
        catch (NoSuchAlgorithmException exp) {
            throw new RuntimeException(exp);
        }
        catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    private String addJsonEntity(HttpEntityEnclosingRequestBase request, Object bodyDetails)
        throws APIException
    {
        if (bodyDetails != null) {
            String jsonBody;
            try {
                jsonBody = mapper.writeValueAsString(bodyDetails);
            }
            catch (JsonProcessingException e) {
                throw new APIException("Unable to serialize the request", e);
            }
            request.setEntity(new StringEntity(jsonBody, JSONContentType));
            return jsonBody;
        }
        else {
            return null;
        }
    }

    /**
     * Creates a new API URI
     *
     * @param requestPath the path specific to the API call (i.e. /products to search products)
     * @param parameters the parameter specific to the API call (i.e. cat=dresses)
     * @return the absolute URI needed to make the API call.
     * @throws APIException if an error occurred when generating the URI. Unlikely to happen
     */
    protected final URI getCallURI(String requestPath, List<NameValuePair> parameters)
        throws APIException
    {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme).setHost(host).setPort(port);
        uriBuilder.setPath(pathPrefix + requestPath);
        uriBuilder.addParameter("pid", partnerId);
        if (locales != null) {
            for (String localeName : locales) {
                uriBuilder.addParameter("locales", localeName);
            }
        }
        if (parameters != null && !parameters.isEmpty()) {
            for (NameValuePair parameter : parameters) {
                uriBuilder.addParameter(parameter.getName(), parameter.getValue());
            }
        }
        URI uri;
        try {
            uri = uriBuilder.build();
        }
        catch (URISyntaxException e) {
            // unlikely to happen
            throw new APIException("Error while building URI", e);
        }
        return uri;
    }

    protected final <T> T call(HttpRequestBase get, Class<T> responseType) throws APIException
    {
        if (httpClient == null) {
            throw new IllegalStateException("Client was closed");
        }
        HttpResponse response;
        try {
            response = httpClient.execute(get);
        }
        catch (Exception e) {
            throw new APIException("Error while executing call to " + get.getURI(), e);
        }
        try {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode < 200 || responseCode >= 300) {
                // handle the error case
                HttpEntity errorResponse = response.getEntity();
                JsonNode errorDescription = mapper.readTree(errorResponse.getContent());
                JsonNode errorMessage = errorDescription.get("errorMessage");
                if (errorMessage == null || errorMessage.isNull()) {
                    throw new APIException("Undescribed error: " + errorDescription);
                }
                else {
                    throw new APIException(errorMessage.asText());
                }
            }
            else {
                // handle successful response
                if (responseType != null) {
                    // If the client expects a non-empty response, parse it into an object
                    HttpEntity successResponse = response.getEntity();
                    return mapper.readValue(successResponse.getContent(), responseType);
                }
                else {
                    return null;
                }
            }
        }
        catch (APIException e) {
            // pass through
            throw e;
        }
        catch (Exception e) {
            throw new APIException("Error while processing response", e);
        }
        finally {
            get.releaseConnection();
        }
    }

    protected void download(String requestPath, List<NameValuePair> queryParameters,
        File destination) throws APIException
    {
        File destinationDirectory = destination.getParentFile();
        if (!destinationDirectory.isDirectory()) {
            if (destinationDirectory.exists()) {
                throw new APIException(destinationDirectory + " is not a directory!");
            }
            else {
                // create the directory
                if (!destinationDirectory.mkdirs()) {
                    throw new APIException("Error creating " + destinationDirectory);
                }
            }
        }

        URI uri = getCallURI(requestPath, queryParameters);
        HttpGet get = new HttpGet(uri);
        addSecureHeader(get, null, null);

        if (httpClient == null) {
            throw new IllegalStateException("Client was closed");
        }
        HttpResponse response;
        try {
            response = httpClient.execute(get);
        }
        catch (Exception e) {
            throw new APIException("Error while executing call to " + get.getURI(), e);
        }
        try {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode < 200 || responseCode >= 300) {
                // handle the error case
                HttpEntity errorResponse = response.getEntity();
                JsonNode errorDescription = mapper.readTree(errorResponse.getContent());
                JsonNode errorMessage = errorDescription.get("errorMessage");
                if (errorMessage == null || errorMessage.isNull()) {
                    throw new APIException("Undescribed error: " + errorDescription);
                }
                else {
                    throw new APIException(errorMessage.asText());
                }
            }
            else {
                // handle successful response
                HttpEntity successResponse = response.getEntity();
                IOUtils.copy(successResponse.getContent(), new FileOutputStream(destination));
            }
        }
        catch (APIException e) {
            // pass through
            throw e;
        }
        catch (Exception e) {
            throw new APIException("Error while processing response", e);
        }
        finally {
            get.releaseConnection();
        }
    }

    protected void addSecureHeader(HttpRequestBase request, String userId, String body)
    {
        if (secretKey == null || secretKey.length() == 0) {
            throw new IllegalStateException("The secret key must be set!");
        }

        // Add the Date header
        Date requestDate = new Date();
        String formattedDate = DateUtils.formatDate(requestDate);
        request.addHeader("Date", formattedDate);

        // Add the Authorization header
        String authorizationToken = getAuthorizationToken(userId, formattedDate);
        request.addHeader("Authorization", "SHPST " + authorizationToken);
    }

    @SuppressWarnings("serial")
    public static class APIException extends Exception
    {
        public APIException(String message)
        {
            super(message);
        }

        public APIException(String message, Throwable cause)
        {
            super(message, cause);
        }
    }
}
