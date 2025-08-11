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
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//@Slf4j
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//  private final ObjectMapper mapper = new ObjectMapper();
//
//  @Override
//  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//    log.debug("UNAUTHORIZED 401 - uri: {}", request.getRequestURI());
//
//    ErrorCode code = ErrorCode.UNAUTHORIZED; // 프로젝트 표준 401 코드 사용
//    ErrorResponse errorResponse = new ErrorResponse(code, code.getMessage());
//
//    response.setStatus(code.getStatus().value());
//    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//    response.setCharacterEncoding("UTF-8");
//    mapper.writeValue(response.getWriter(), errorResponse);
//  }
//}
