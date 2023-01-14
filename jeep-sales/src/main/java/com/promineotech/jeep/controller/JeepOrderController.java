package com.promineotech.jeep.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;



@Validated
@RequestMapping("/orders")
@OpenAPIDefinition(info = @Info(title = "Jeep Order Service"),
servers = {@Server(url = "http://localhost:8080",
description = "Local server.")}) 
public interface JeepOrderController {
  // @formatter:off
  @Operation(
      summary = "Create an order for a Jeep",
      description = "Returns the created Jeep",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "The created Jeep is Returned",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Order.class))),
          @ApiResponse(
              responseCode = "400",
              description = "request parameters are invalid",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "A Jeep component was not found with the input criteria",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occurred",
              content = @Content(mediaType = "application/json"))     
          
      },
      parameters = {
          @Parameter(
              name = "orderRequest",
              required = true,
              description = "The order as JSON")
      }
      )
@PostMapping
@ResponseStatus(code = HttpStatus.CREATED)
Order createOrder(@Valid @RequestBody OrderRequest orderRequest);
//@formatter:on
}
