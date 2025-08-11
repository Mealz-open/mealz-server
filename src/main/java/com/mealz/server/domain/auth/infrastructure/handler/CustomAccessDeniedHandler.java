//package com.mealz.server.domain.auth.infrastructure.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mealz.server.global.exception.ErrorCode;
//import com.mealz.server.global.exception.ErrorResponse;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//@Slf4j
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//
//  private final ObjectMapper mapper = new ObjectMapper();
//
//  @Override
//  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//    log.debug("FORBIDDEN 403 - uri: {}", request.getRequestURI());
//
//    ErrorCode code = ErrorCode.ACCESS_DENIED;
//    ErrorResponse errorResponse = new ErrorResponse(code, code.getMessage());
//
//    response.setStatus(code.getStatus().value());
//    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//    response.setCharacterEncoding("UTF-8");
//    mapper.writeValue(response.getWriter(), errorResponse);
//  }
//}
