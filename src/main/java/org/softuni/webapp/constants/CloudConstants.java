package org.softuni.webapp.constants;

public final class CloudConstants {
    public static final String REFRESH_TOKEN_NAME = "refresh_token";

    public static final String ACCESS_TOKEN_NAME = "access_token";

    public static final String AUTHORIZATION_URL = "https://login.microsoftonline.com/common/oauth2/v2.0/token";

    public static final String CONTENT_TYPE_NAME = "Content-Type";

    public static final String CONTENT_TYPE_APP = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE_IMG = "image/jpeg";

    public static final String CONTENT_UPLOAD_URL_END = ":/content";

    public static final String CONTENT_DOWNLOAD_URL_END = "/content";

    public static final String CONTENT_LOCATION_NAME = "Content-Location";

    public static final String ID_NAME = "id";

    public static final String CLIENT_ID_NAME = "client_id";

    public static final String CLIENT_ID_VALUE = "e98344a7-34a7-4190-acdc-39bf18bd3ef9";

    public static final String REDIRECT_URI_NAME = "redirect_uri";

    public static final String REDIRECT_URI_VALUE = "http://localhost:8080";

    public static final String CLIENT_SECRET_NAME = "client_secret";

    public static final String CLIENT_SECRET_VALUE = "aajRUZR69}cbhfPYG645=}+";

    public static final String GRANT_TYPE_NAME = "grant_type";

    public static final String GRANT_TYPE_VALUE = "refresh_token";

    public static final String AUTHORIZATION_NAME = "Authorization";

    public static final String AUTHORIZATION_VALUE_BEARER = "bearer";

    public static final String UPLOAD_ITEM_URL = "https://graph.microsoft.com/v1.0/me/drive/root:/rouzenaWebApp/";

    public static final String DOWNLOAD_ITEM_URL = "https://graph.microsoft.com/v1.0/me/drive/items/";

    public static final String DELETE_ITEM_URL = "https://graph.microsoft.com/v1.0/me/drive/items/";

    public static final String REFRESH_TOKEN_FILE_NOT_FOUND= "Cannot read or save refresh token file if existent. " +
            "Unable to connect to OneDrive.";

    public static final String REFRESH_TOKEN_FILE_LOCATION = System.getProperty("user.dir") +
            "\\src\\main\\resources\\static\\cloudData\\cloudRefreshToken.txt";

    public static final long TOKENS_REFRESH_DELAY = 3000L;

    public static final long TOKENS_REFRESH_INITIAL_DELAY = 0L;

}
