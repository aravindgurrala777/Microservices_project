package com.project.task.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class PaymentClient {

    private final WebClient webClient;

    public PaymentClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Retry(
        name = "paymentService"
       
    )
    @CircuitBreaker(
        name = "paymentService",
        fallbackMethod = "paymentFallback"
    )
    @TimeLimiter(
        name = "paymentService"
       
    )
    public CompletableFuture<String> processPayment(Long orderId) {

        System.out.println(
            "Calling Payment Service for Order: " + orderId
        );

        return webClient
                .get()
                .uri(
                    "http://payment-service/task/payment/process/"
                    + orderId
                )
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();
    }

    public CompletableFuture<String> paymentFallback(
            Long orderId,
            Throwable t) {

        System.out.println(
            "CircuitBreaker Fallback triggered for Order: "
            + orderId
            + " Reason: "
            + t.getMessage()
        );

        return CompletableFuture.completedFuture(
            "PAYMENT PENDING: Order placed. Payment will be processed later."
        );
    }

    public CompletableFuture<String> paymentFallbackRetry(
            Long orderId,
            Throwable t) {

        System.out.println(
            "Retry Fallback triggered for Order: "
            + orderId
            + " Reason: "
            + t.getMessage()
        );

        return CompletableFuture.completedFuture(
            "PAYMENT PENDING: Order placed. Payment will be processed later."
        );
    }
}