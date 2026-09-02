package com.project.task.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CorrelationIdFilter  extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		String correlationId = request.getHeader("X-Correlation-ID");
		
		if(correlationId == null || correlationId.isEmpty()) {
			correlationId = UUID.randomUUID().toString();
			
			System.out.println("No CID received.... generating new CID: " + correlationId);
		}
		
		MDC.put("correlationId", correlationId);
		MDC.put("X-Correlation-ID", correlationId);
		
		
		System.out.println(correlationId + " -- user service , cid received from order service");
		
		
		try {
			response.addHeader("X-Correlation-ID", correlationId);
			filterChain.doFilter(request, response);
		}
		finally {
			MDC.clear();
		}
		
		
	}

}
