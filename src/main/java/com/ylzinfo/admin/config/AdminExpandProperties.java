package com.ylzinfo.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author licl
 * @date 2020/12/23
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.boot.admin.expand")
public class AdminExpandProperties {
    /**
     * 默认每十秒记录一次
     */
    private Duration recordInterval = Duration.ofSeconds(10);
    /**
     * 是否开启指标记录
     */
    private Boolean enableRecord;
}
