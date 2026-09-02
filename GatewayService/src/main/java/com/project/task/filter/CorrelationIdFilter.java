package com.project.task.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorrelationIdFilter implements GlobalFilter, Ordered {
	
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String correlationId = exchange.getRequest().getHeaders().getFirst("X-Correlation-ID");
		if (correlationId == null) {
			correlationId = UUID.randomUUID().toString();
		}
		
		String finalCorrelationId = correlationId;
		
		System.out.println("Correlation ID: " + finalCorrelationId);
		
		ServerWebExchange modifiedExchange = exchange.mutate()
				.request(r -> r.header("X-Correlation-ID", finalCorrelationId)).build();
		
		modifiedExchange.getResponse().getHeaders().add("X-Correlation-ID", correlationId);
		
		return chain.filter(modifiedExchange);
	}

	@Override
	public int getOrder() {
		return -1;                 //run first than other filters
	}
	

}
