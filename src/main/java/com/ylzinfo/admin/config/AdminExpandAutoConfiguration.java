package com.ylzinfo.admin.config;

import com.ylzinfo.admin.service.IntervalRecorder;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author licl
 * @date 2020/12/23
 */
@SuppressWarnings("ALL")
@Configuration
@ConditionalOnBean({InstanceWebClient.class,InstanceRepository.class})
@ConditionalOnProperty(prefix = "spring.boot.admin.expand", name = "enableRecord", matchIfMissing = true)
@EnableConfigurationProperties(AdminExpandProperties.class)
public class AdminExpandAutoConfiguration {

    private AdminExpandProperties adminExpandProperties;

    public AdminExpandAutoConfiguration(AdminExpandProperties adminExpandProperties) {
        this.adminExpandProperties = adminExpandProperties;
    }

    @Bean
    public IntervalRecorder intervalRecorder(InstanceWebClient instanceWebClient,
                                             InstanceRepository repository,
                                             JdbcTemplate jdbcTemplate){
        return new IntervalRecorder(instanceWebClient, repository, jdbcTemplate,adminExpandProperties);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
