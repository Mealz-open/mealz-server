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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.warn("권한 거부(403) - URI: {}", request.getRequestURI());

    ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    mapper.writeValue(response.getWriter(), errorResponse);
  }
}
