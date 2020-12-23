package com.ylzinfo.admin.config;

import com.ylzinfo.admin.service.IntervalRecorder;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
public class AdminExpandAutoConfiguration {


    @Bean
    public IntervalRecorder intervalRecorder(InstanceWebClient instanceWebClient,
                                             InstanceRepository repository,
                                             JdbcTemplate jdbcTemplate){
        return new IntervalRecorder(instanceWebClient, repository, jdbcTemplate);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
