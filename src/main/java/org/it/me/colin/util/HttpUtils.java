package org.it.me.colin.util;

import org.it.me.colin.exception.ClientException;
import org.it.me.colin.exception.ServiceException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtils {

    /**
     * TODO: This would be certainly better handled by using an RPC framework or "HTTP" layer rather than static helper
     * TODO: Using Supplier<HttpResponse<String>> leads to ugly implementation because of checked exceptions
     * @param request
     * @param httpClient
     * @return
     */
    public static HttpResponse<String> handleHttpResponse(HttpRequest request, HttpClient httpClient) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400 && response.statusCode() <= 499) {
                throw new ClientException(response.body());
            } else if (response.statusCode() >= 500) {
                throw new ServiceException(response.body());
            }
            return response;
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }
}
