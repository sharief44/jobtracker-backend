package com.sharief.jobtracker.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.sharief.jobtracker.config.RateLimitFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

class RateLimitFilterTest {

    private RateLimitFilter rateLimitFilter;

    @BeforeEach
    void setUp() {
        rateLimitFilter = new RateLimitFilter();
    }

    @Test
    void shouldBlockRequestAfterLimitExceeded() throws ServletException, IOException {

        FilterChain filterChain = mock(FilterChain.class);

        boolean blocked = false;

        for (int i = 1; i <= 100; i++) {

            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRemoteAddr("192.168.1.1");

            MockHttpServletResponse response = new MockHttpServletResponse();

            rateLimitFilter.doFilter(request, response, filterChain);

            if (response.getStatus() == 429) {
                blocked = true;
                break;
            }
        }

        assertEquals(true, blocked);
    }
}