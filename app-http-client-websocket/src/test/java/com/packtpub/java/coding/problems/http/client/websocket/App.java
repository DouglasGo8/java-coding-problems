package com.packtpub.java.coding.problems.http.client.websocket;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class App {


    @Test
    @SneakyThrows
    public void triggeringAsyncGet() {

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("https://reqres.in/api/users/2"))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        out.println(response.statusCode());
        out.println(response.body());

        // HttpClient.newBuilder().proxy
        // HttpRequest.newBuilder().uri().header().build()
        // HttpRequest.newBuilder().GET()
        // HttpRequest.newBuilder().header("Content-Type", "application/json").POST()
        // HttpRequest.newBuilder().header(...).POST(HttpRequest.BodyPublishers.ofString("")).uri
        // HttpClient.newBuilder().authenticator(new Authenticator() {
        //    @Override
        //    public PasswordAuthentication getPasswordAuthentication() {
        //
        //    }
        //})
        // throws a HttpConnectionTimeoutException
        // HttpRequest.newBuilder().uri(...).timeout(Duration.of(5, ChronoUnit.MILLIS))


    }

    @Test
    @SneakyThrows
    public void requestAsync() {

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("https://reqres.in/api/users/2")).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(e -> "Exception " + e)
                .thenAccept(out::println)
                .get(30, TimeUnit.SECONDS);
    }

    @Test
    @SneakyThrows
    public void sendMultipleAsync() {

        var uris = List.of(new URI("https://reqres.in/api/users/2"),
                new URI("https://reqres.in/api/users?page=2"),
                new URI("https://reqres.in/api/unknown/2"),
                new URI("https://reqres.in/api/users/23"));

        var client = HttpClient.newHttpClient();

        var requests = uris.stream().map(HttpRequest::newBuilder)
                .map(HttpRequest.Builder::build)
                .collect(Collectors.toList());

        CompletableFuture.allOf(requests.stream().map(req -> client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply((res) -> res.uri() + " | " + res.body() + " \n")
                .exceptionally(e -> "Exception: " + e)
                .thenAccept(out::println))
                .toArray(CompletableFuture[]::new)).join();
    }
}
