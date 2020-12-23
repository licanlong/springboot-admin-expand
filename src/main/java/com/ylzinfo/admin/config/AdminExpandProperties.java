package com.ylzinfo.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author licl
 * @date 2020/12/23
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.boot.admin.expand")
public class AdminExpandProperties {

}
