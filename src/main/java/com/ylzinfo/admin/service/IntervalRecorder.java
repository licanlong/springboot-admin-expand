package com.ylzinfo.admin.service;

import com.ylzinfo.admin.enumerate.MetricsEnum;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author licl
 * @date 2020/11/10
 */

@SuppressWarnings({"rawtypes", "unchecked"})
@AllArgsConstructor
@Slf4j
public class IntervalRecorder {
    private InstanceWebClient instanceWebClient;
    private InstanceRepository repository;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void start() {
        log.info("start record service metrics");
        Duration duration = Duration.ofSeconds(10);
        Scheduler scheduler = Schedulers.newSingle("record");
        Flux.interval(duration)
                .doOnNext(s -> log.info("定时 {} 记录每个服务运行指标",duration))
                .subscribeOn(scheduler)
                .flatMap(i -> repository.findAll())
                .filter(instance -> instance.getStatusInfo().isUp())
                .flatMap(this::getMetrics)
                .onErrorResume(e -> {
                    log.error("record service metrics occurred error",e);
                    return Flux.empty();
                })
                .doFinally(s -> scheduler.dispose())
                .subscribe(this::recordMetrics);
    }


    private Flux<HashMap> getMetrics(Instance instance) {
       return Flux.fromArray(MetricsEnum.values())
                .flatMap(metricsEnum -> getResultMono(instance, metricsEnum));
    }

    private Mono<HashMap> getResultMono(Instance instance, MetricsEnum metricsEnum) {
        return instanceWebClient.instance(instance)
                .get()
                .uri("/metrics" + metricsEnum.getUri())
                .exchange()
                .onErrorResume(e -> {
                    log.error("服务（{}）异常",instance.getRegistration());
                    return Mono.empty();
                })
                .flatMap(clientResponse -> clientResponse.bodyToMono(HashMap.class))
                .doOnNext(r -> {
                    r.put("serviceUrl",instance.getRegistration().getServiceUrl());
                    r.put("appName", instance.getRegistration().getName());
                    r.put("description", metricsEnum.getDescription());
                    r.put("name", metricsEnum.name());
                });
    }

    private void recordMetrics(Map<String, Object> result) {
        log.info("service mertics save to db");
        Object value = ((List<Map>) result.get("measurements")).get(0).get("value");
        String sql = "insert into monitor_data (name,description,app_name,value,baseUnit,datatime,app_url) values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, (String) result.get("name"));
            ps.setString(2, (String) result.get("description"));
            ps.setString(3, (String) result.get("appName"));
            ps.setDouble(4, (Double) value);
            ps.setString(5, (String) result.get("baseUnit"));
            ps.setString(6, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setString(7, (String) result.get("serviceUrl"));
        });
    }
}
