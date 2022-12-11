package com.promineotech.jeep.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import lombok.Getter;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@ActiveProfiles("test")

@Sql(scripts = {
    "classpath:flyaway/migrations/V1.0_Jeep_Schema.sql",
    "classpath:flyaway/migrations/V1.0_Jeep_Data.sql"},
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
        String.format("http:localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
    
  // When
   ResponseEntity<List<Jeep>>response = 
       restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
  // Then  
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

}
