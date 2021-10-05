package com.bnp.developmentbookskata.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "pricing")
@Data
@Component
public class DiscountConfig {

    private Double bookPrice;
    private Map<Integer, Double> discountMap;

}
