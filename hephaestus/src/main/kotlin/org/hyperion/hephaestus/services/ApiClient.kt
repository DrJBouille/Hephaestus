package org.hyperion.hephaestus.services

import com.google.gson.Gson
import org.hyperion.hephaestus.exceptions.ApiException
import org.hyperion.hephaestus.exceptions.ProblemDetails
import java.lang.reflect.Type
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

class ApiClient(
    private val baseUrl: String,
    private val apiKey: String,
    private val httpClient: HttpClient = HttpClient.newHttpClient(),
    private val gson: Gson = Gson()
) {
    fun <T> get(path: String, responseType: Type): CompletableFuture<T> = send(buildRequest("GET", path), responseType)

    fun <T> post(path: String, body: Any, responseType: Type): CompletableFuture<T> = send(buildRequest("POST", path, body), responseType)

    fun <T> put(path: String, body: Any, responseType: Type): CompletableFuture<T> = send(buildRequest("PUT", path, body), responseType)

    private fun buildRequest(method: String, path: String, body: Any? = null): HttpRequest {
        val builder = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + path))
            .header("X-API-Key", apiKey)

        if (body != null) {
            builder.header("Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
        } else {
            builder.method(method, HttpRequest.BodyPublishers.noBody())
        }

        return builder.build()
    }

    private fun <T> send(request: HttpRequest, responseType: Type): CompletableFuture<T> = httpClient
        .sendAsync(request,HttpResponse.BodyHandlers.ofString())
        .thenApply { response ->
            val status = response.statusCode()

            if (status in 200..299) {
                gson.fromJson<T>(response.body(), responseType)
            } else {
                throw parseError(response)
            }
        }

    private fun parseError(
        response: HttpResponse<String>
    ): ApiException {
        return try {
            val problem = gson.fromJson(
                response.body(),
                ProblemDetails::class.java
            )

            val detail = problem?.detail
                ?: "Erreur inconnue (HTTP ${response.statusCode()})"

            ApiException(response.statusCode(), detail)
        } catch (e: Exception) {
            ApiException(
                response.statusCode(),
                "Erreur HTTP ${response.statusCode()}: ${response.body()}"
            )
        }
    }
}