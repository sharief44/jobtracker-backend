package com.sharief.jobtracker.config;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    // Separate buckets for login and normal APIs
    private final Map<String, Bucket> loginBuckets = new ConcurrentHashMap<>();
    private final Map<String, Bucket> apiBuckets = new ConcurrentHashMap<>();


    // Login endpoint limit: 5 requests per minute
    @SuppressWarnings("deprecation")
	private Bucket newLoginBucket() {
        Bandwidth limit = Bandwidth.classic(
                5,
                Refill.intervally(5, Duration.ofMinutes(1))
        );

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }


    // Other APIs limit: 50 requests per minute
    @SuppressWarnings("deprecation")
	private Bucket newApiBucket() {
        Bandwidth limit = Bandwidth.classic(
                50,
                Refill.intervally(50, Duration.ofMinutes(1))
        );

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }


    private Bucket resolveLoginBucket(String ip) {
        return loginBuckets.computeIfAbsent(ip, k -> newLoginBucket());
    }

    private Bucket resolveApiBucket(String ip) {
        return apiBuckets.computeIfAbsent(ip, k -> newApiBucket());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        String path = request.getRequestURI();

        Bucket bucket;

        // Apply strict limit for login endpoint
        if (path.equals("/api/users/login")) {
            bucket = resolveLoginBucket(ip);
        } else {
            bucket = resolveApiBucket(ip);
        }

        if (bucket.tryConsume(1)) {

            filterChain.doFilter(request, response);

        } else {

            response.setStatus(429);
            response.getWriter().write("Too many requests. Please try again later.");
        }
    }
}