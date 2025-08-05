package com.mealz.server.domain.shop.core.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mealz.server.global.util.CommonUtil;
import com.mealz.server.global.util.SortField;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopSortField implements SortField {

  CREATED_DATE("createdDate"),

  NEAREST("nearest"),

  ;

  private final String property;

  /**
   * Jackson이 JSON -> Java 객체로 역직렬화 (deserialization)할 때 자동 호출
   * 컨트롤러에서 들어온 {"sortField": "TICKET_OPEN_DATE"} 같은 문자열을 변환
   * 만약 {"sortField": "ticketOpenDate"}와 같이 카멜케이스로 들어와도 f.property와 비교하여 자동 매칭
   */
  @JsonCreator
  public static ShopSortField from(String value) {
    return CommonUtil.stringToSortField(ShopSortField.class, value);
  }
}
