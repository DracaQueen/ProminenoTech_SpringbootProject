package com.promineotech.jeep.entity;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class OrderRequest {
  
  @NotNull
  @Length(max =30)
  @Pattern(regexp = "[\\w\\s]*")
  private String customer;
  
  @NotNull
  private JeepModel model;
  
  @NotNull
  @Length(max =30)
  @Pattern(regexp = "[\\w\\s]*")
  private String trim;
  
  @Positive
  @Min(2)
  @Max(4)
  private int doors;
  
  @NotNull
  @Length(max =30)
  @Pattern(regexp = "[\\w\\s]*")
  private String color;
  
  @NotNull
  @Length(max =30)
  @Pattern(regexp = "[\\w\\s]*")
  private String engine;
  
  @NotNull
  @Length(max =30)
  @Pattern(regexp = "[\\w\\s]*")
  private String tire;

  private List<@NotNull @Length(max = 30) @Pattern(regexp = "[\\w\\s]*")String> options;
}
