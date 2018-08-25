package org.softuni.webapp.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.softuni.webapp.constants.CloudConstants;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
@EnableScheduling
public final class CloudManager {

    private String accessToken;

    private String receiveCurrentRefreshTokenValue() {
        File refreshTokenFile = new File(CloudConstants.REFRESH_TOKEN_FILE_LOCATION);

        String refreshToken = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(refreshTokenFile)));
            refreshToken = reader.readLine();
            reader.close();
        } catch (IOException e) {
            System.out.println(CloudConstants.REFRESH_TOKEN_FILE_NOT_FOUND);
            e.printStackTrace();
        }

        return refreshToken;
    }

    private void saveNewRefreshToken(String newRefreshToken) {
        try {
            Writer fileWriter = new BufferedWriter(new FileWriter(CloudConstants.REFRESH_TOKEN_FILE_LOCATION));

            fileWriter.write(newRefreshToken);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject receiveJsonFromResponse(HttpResponse response) {
        String resultAsString = "";

        try {
            InputStream responseContent = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseContent));

            StringBuilder result = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                result.append(line + System.lineSeparator());
                line = reader.readLine();
            }

            resultAsString = result.toString();

            reader.close();
            responseContent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JsonParser().parse(resultAsString).getAsJsonObject();
    }

    @Scheduled(fixedDelay = CloudConstants.TOKENS_REFRESH_DELAY, initialDelay = CloudConstants.TOKENS_REFRESH_INITIAL_DELAY)
    private void requestNewAccessToken() {
        HttpPost httpPost = new HttpPost(CloudConstants.AUTHORIZATION_URL);
        httpPost.addHeader(CloudConstants.CONTENT_TYPE_NAME, CloudConstants.CONTENT_TYPE_APP);

        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair(CloudConstants.CLIENT_ID_NAME, CloudConstants.CLIENT_ID_VALUE));
        pairs.add(new BasicNameValuePair(CloudConstants.REDIRECT_URI_NAME, CloudConstants.REDIRECT_URI_VALUE));
        pairs.add(new BasicNameValuePair(CloudConstants.CLIENT_SECRET_NAME, CloudConstants.CLIENT_SECRET_VALUE));
//        pairs.add(new BasicNameValuePair("code", "Mc479de32-fa80-a016-38a0-44c79d70ba2f"));
        pairs.add(new BasicNameValuePair(CloudConstants.REFRESH_TOKEN_NAME, this.receiveCurrentRefreshTokenValue()));
        pairs.add(new BasicNameValuePair(CloudConstants.GRANT_TYPE_NAME, CloudConstants.GRANT_TYPE_VALUE));

        String accessToken = "";

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));

            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httpPost);

            JsonObject responseJson = this.receiveJsonFromResponse(response);

            accessToken = responseJson.get(CloudConstants.ACCESS_TOKEN_NAME).getAsString();

            String newRefreshToken = responseJson.get(CloudConstants.REFRESH_TOKEN_NAME).getAsString();
            this.saveNewRefreshToken(newRefreshToken);

            ((CloseableHttpClient) httpClient).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.accessToken = accessToken;
    }

    public String uploadFileAndReceiveCloudId(MultipartFile file) {
        String newAccessToken = this.accessToken;

        HttpPut httpPut = new HttpPut(CloudConstants.UPLOAD_ITEM_URL
                + file.getOriginalFilename()
                + CloudConstants.CONTENT_UPLOAD_URL_END);

        httpPut.addHeader(CloudConstants.AUTHORIZATION_NAME, CloudConstants.AUTHORIZATION_VALUE_BEARER
                + " "
                + newAccessToken);
        httpPut.addHeader(CloudConstants.CONTENT_TYPE_NAME, CloudConstants.CONTENT_TYPE_IMG);

        List<NameValuePair> pairs = new ArrayList<>();

        String cloudFileId = null;

        try {
            httpPut.setEntity(new ByteArrayEntity(file.getBytes()));

            HttpClient connectionClient = HttpClients.createDefault();
            HttpResponse response = connectionClient.execute(httpPut);

            JsonObject responseJson = this.receiveJsonFromResponse(response);

            cloudFileId = responseJson.get(CloudConstants.ID_NAME).getAsString();

            ((CloseableHttpClient) connectionClient).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cloudFileId;
    }

    public String receivePictureContentAsBase64(String pictureCloudId) {
        String accessToken = this.accessToken;

        HttpGet httpGet = new HttpGet(CloudConstants.DOWNLOAD_ITEM_URL
                + pictureCloudId
                + CloudConstants.CONTENT_DOWNLOAD_URL_END);

        httpGet.addHeader(CloudConstants.AUTHORIZATION_NAME, CloudConstants.AUTHORIZATION_VALUE_BEARER
                + " "
                + accessToken);

        HttpClient connectionClient = HttpClients.createDefault();

        String result = "";

        try {
            HttpResponse response = connectionClient.execute(httpGet);

            String locationUrl = response.getHeaders(CloudConstants.CONTENT_LOCATION_NAME)[0].getValue();

            ((CloseableHttpClient) connectionClient).close();

            HttpGet getContent = new HttpGet(locationUrl);

            HttpClient contentClient = HttpClients.createDefault();
            HttpResponse responseContent = contentClient.execute(getContent);

            result = Base64.getEncoder().encodeToString(responseContent.getEntity().getContent().readAllBytes());
        } catch (IOException e) {
            System.out.println("Unable to download file from cloud.");
            e.printStackTrace();
        }

        return result;
    }

    public void deletePictureByCloudId(String cloudId) {
        String accessToken = this.accessToken;

        HttpDelete httpDelete = new HttpDelete(CloudConstants.DELETE_ITEM_URL + cloudId);

        httpDelete.addHeader(CloudConstants.AUTHORIZATION_NAME, CloudConstants.AUTHORIZATION_VALUE_BEARER
                + " "
                + accessToken);

        HttpClient httpClient = HttpClients.createDefault();
        try {
            httpClient.execute(httpDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
