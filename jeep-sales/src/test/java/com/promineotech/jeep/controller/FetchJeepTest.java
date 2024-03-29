package com.promineotech.jeep.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
//import io.swagger.v3.oas.models.PathItem.HttpMethod;
import lombok.Getter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
    "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
    "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
config=@SqlConfig(encoding="utf-8"))
class FetchJeepTest {
  
  @LocalServerPort
  private int serverPort;
  
 @Autowired 
 @Getter
 private TestRestTemplate restTemplate;

  @Test
  void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
  // Given
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Sport";
    String uri = 
        String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
    
  // When
   ResponseEntity<List<Jeep>>response = 
       restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
  // Then  
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
   
   List<Jeep> actual = response.getBody();
   List<Jeep> expected = buildExpected();
   
   actual.forEach(jeep -> jeep.setModelPK(null));
   
   assertThat(actual).isEqualTo(expected);
  }
  /*
  @Test
  void testThatAnErrorMessageIsReturnedWhenAnUnknownTrimIsSupplied() {
  // Given
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Unknown Value";
    String uri = 
        String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
    
  // When: a connection is made to the URI
   ResponseEntity<Map<String, Object>>response = 
       restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
   
  // Then : a not found (404) status code is required 
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
   
   Map<String, Object> error = response.getBody();
   
   assertErrorMessageValid(error, HttpStatus.NOT_FOUND);
  }
  
  @ParameterizedTest
  @MethodSource("com.promineotech.jeep.FetchJeepTest#parametersForInvalidInput")
  void testThatAnErrorMessageIsReturnedWhenAnInvalidValueIsSupplied(
      String model, String trim, String reason) {
  // Given
    String uri = 
        String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);  
  // When: a connection is made to the URI
   ResponseEntity<Map<String, Object>>response = 
       restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
   
  // Then : a not found (404) status code is required 
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
   
   Map<String, Object> error = response.getBody();
   
   assertErrorMessageValid(error,HttpStatus.BAD_REQUEST );
  }
  
  static Stream<Arguments> parametersForInvalidInput(){
    return Stream.of(
        arguments("WRANGLER", "^**&*&", "Trim contains non-alpha-numeric chars"));
  }

  protected void assertErrorMessageValid(Map<String, Object> error, HttpStatus status) {
    assertThat(error)
     .containsKey("message")
     .containsEntry("status code", status.value())
     .containsEntry("uri", "/jeeps")
     .containsKey("timestamp")
     .containsEntry("reason", status.getReasonPhrase());
  }
*/
  protected List<Jeep> buildExpected() {
    List<Jeep> list = new LinkedList<>();
    
    // @formatter:off
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(2)
        .wheelSize(17)
        .basePrice(new BigDecimal("28475.00"))
        .build());
    
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(4)
        .wheelSize(17)
        .basePrice(new BigDecimal("31975.00"))
        .build());
    
    // @formatter:on
  
    Collections.sort(list);
    return list;
  }

}
