package com.mealz.server.global.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(ErrorCode errorCode, String errorMessage) {

}
