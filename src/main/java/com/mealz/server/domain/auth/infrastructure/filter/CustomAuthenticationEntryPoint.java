package com.mealz.server.domain.auth.infrastructure.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mealz.server.global.exception.ErrorCode;
import com.mealz.server.global.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    log.warn("인증 필요(401) - URI: {}", request.getRequestURI());

    ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED, ErrorCode.UNAUTHORIZED.getMessage());

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    mapper.writeValue(response.getWriter(), errorResponse);
  }
}
