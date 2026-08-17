package com.project.task.service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class PaymentClient {

	private WebClient webClient = WebClient.create();
	
	
	
	@Retry(name="paymentService")
	@CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
	@TimeLimiter(name="paymentService")
	public CompletableFuture<String> processPayment(Long orderId) {
		
		return webClient.get().
				uri("http://localhost:9092/task/payment/process/"+ orderId)
				.retrieve()
				.bodyToMono(String.class)
			//	.timeout(Duration.ofSeconds(3))
			//	.onErrorReturn("Payment timeout. Order PENDING")
				.toFuture();
				
		
		
	}
	
	public CompletableFuture<String> paymentFallback(Long orderId, Throwable t) {
		
		System.out.println("Fallback triggered for Order: " + orderId + " Reason: " + t.getMessage());
		
		return  CompletableFuture.completedFuture("PAYMENT PENDING: Order placed. Palyment will be processed later. " + " |  Try again agter some time" + " | Final call back");
		
	}
	
     public CompletableFuture<String> paymentFallbackRetry(Long orderId, Throwable t) {
		
		System.out.println("Retry Fallback triggered for Order: " + orderId + " Reason: " + t.getMessage());
		
		return  CompletableFuture.completedFuture("PAYMENT PENDING: Order placed. Palyment will be processed later");
		
	}
	
	
}
