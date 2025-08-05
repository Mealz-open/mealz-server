package com.mealz.server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // GLOBAL

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),

  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

  ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),

  // AUTH

  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),

  MISSING_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 필요합니다."),

  INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),

  EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "엑세스 토큰이 만료되었습니다."),

  EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),

  REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),

  COOKIES_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠키가 존재하지 않습니다."),

  // OAUTH2

  INVALID_SOCIAL_PLATFORM(HttpStatus.BAD_REQUEST, "잘못된 소셜 플랫폼이 요청되었습니다."),

  INVALID_REDIRECT_URI(HttpStatus.BAD_REQUEST, "유효하지 않은 리다이렉트 URI가 요청되었습니다."),

  // MEMBER

  DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 가입된 이메일입니다"),

  MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),

  INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "잘못된 회원 자격입니다"),

  INVALID_MEMBER_ROLE_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 회원 권한 요청입니다."),

  // SHOP

  SHOP_NOT_FOUND(HttpStatus.BAD_REQUEST, "매장을 찾을 수 없습니다."),

  NOT_OWNED_SHOP(HttpStatus.BAD_REQUEST, "매장 소유주가 아닙니다."),

  // ITEM

  ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "물품을 찾을 수 없습니다."),

  INVALID_PICKUP_TIME(HttpStatus.BAD_REQUEST, "잘못된 픽업 시간이 요청되었습니다"),

  // MOCK DATA TODO: 출시 후 삭제

  GENERATE_MOCK_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Mock 데이터 생성 중 오류 발생"),

  SAVE_MOCK_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Mock 데이터 저장 중 오류 발생"),

  // S3

  INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 확장자입니다."),

  FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다."),

  S3_UPLOAD_AMAZON_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 서비스 에러로 인해 파일 업로드에 실패했습니다."),

  S3_UPLOAD_AMAZON_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 클라이언트 에러로 인해 파일 업로드에 실패했습니다."),

  S3_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 파일 업로드 중 오류 발생"),

  S3_DELETE_AMAZON_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 서비스 에러로 인해 파일 삭제에 실패했습니다."),

  S3_DELETE_AMAZON_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 클라이언트 에러로 인해 파일 삭제에 실패했습니다."),

  S3_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 파일 삭제 중 오류 발생"),

  INVALID_FILE_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 요청입니다."),

  INVALID_FILE_PATH(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 URL 요청입니다."),

  // REDIS_LOCK

  LOCK_ACQUISITION_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "락 획득에 실패했습니다."),

  LOCK_ACQUISITION_INTERRUPT(HttpStatus.INTERNAL_SERVER_ERROR, "락 획득 중 인터럽트가 발생했습니다."),

  // NOTIFICATION

  FCM_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "fcm 토큰 획득에 실패했습니다."),

  // CHAT

  CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾지 못했습니다."),

  ALREADY_EXIST_CHAT_ROOM(HttpStatus.CONFLICT, "이미 해당 콘서트, 대리인, 의뢰인 및 선예매/일반예매에 관한 채팅방이 존재합니다."),

  MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "메시지를 찾지 못했습니다."),

  NO_AUTH_TO_ROOM(HttpStatus.FORBIDDEN, "해당 사용자는 이 채팅방에 들어갈 수 없습니다."),

  CHAT_PICTURE_EMPTY(HttpStatus.NOT_FOUND, "전송할 이미지가 없습니다."),

  CHAT_PICTURE_SIZE_EXCEED(HttpStatus.BAD_REQUEST, "채팅 이미지는 최대 10장까지 보낼 수 있습니다."),

  CHAT_MESSAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "채팅 메시지 전송중 오류가 발생했습니다."),

  // EMBEDDING

  EMBEDDING_API_ERROR(HttpStatus.BAD_REQUEST, "Vertex AI API 호출에 실패했습니다."),

  EMBEDDING_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "반환된 임베딩 데이터가 존재하지 않습니다."),

  INSUFFICIENT_DATA_FOR_EMBEDDING(HttpStatus.INTERNAL_SERVER_ERROR, "임베딩 생성을 위한 데이터가 불충분합니다."),

  // PAGEABLE

  INVALID_SORT_FIELD(HttpStatus.BAD_REQUEST, "필터링 조회 시 정렬 필드 요청이 잘못되었습니다."),
  ;

  private final HttpStatus status;
  private final String message;
}
