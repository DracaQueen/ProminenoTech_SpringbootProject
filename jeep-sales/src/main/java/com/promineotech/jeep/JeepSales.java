package com.promineotech.jeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.promineotech.ComponetScanMarker;

@SpringBootApplication(scanBasePackageClasses = {ComponetScanMarker.class})
public class JeepSales {

  public static void main(String[] args) {
    SpringApplication.run(JeepSales.class, args);

  }

}
