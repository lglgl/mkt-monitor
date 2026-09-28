package cn.dahouzi.mktmonitor.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Metrics demonstration controller.
 *
 * <p>
 * This controller exposes custom application metrics
 * for Prometheus and Grafana monitoring.
 * </p>
 *
 * <p>
 * The metrics are intentionally simple because this project
 * is a DevSecOps / SRE laboratory rather than a production
 * market-data service.
 * </p>
 */
@RestController
public class MetricsController {

    /**
     * Counts requests to the metrics test endpoint.
     */
    private final Counter requestCounter;

    /**
     * Simulated market price.
     *
     * This is only test data used to demonstrate
     * application metrics.
     */
    private final AtomicInteger currentPrice =
            new AtomicInteger(100);

    public MetricsController(MeterRegistry meterRegistry) {

        /*
         * Custom Counter:
         *
         * Prometheus metric:
         *
         * market_monitor_requests_total
         */
        requestCounter = Counter.builder(
                        "market_monitor_requests"
                )
                .description(
                        "Number of market monitor requests"
                )
                .tag(
                        "application",
                        "mkt-monitor"
                )
                .register(meterRegistry);

        /*
         * Custom Gauge:
         *
         * Prometheus metric:
         *
         * market_monitor_price
         */
        Gauge.builder(
                        "market_monitor_price",
                        currentPrice,
                        AtomicInteger::get
                )
                .description(
                        "Simulated market price"
                )
                .tag(
                        "application",
                        "mkt-monitor"
                )
                .register(meterRegistry);
    }

    /**
     * Test endpoint for generating application metrics.
     *
     * Every request:
     *
     * 1. Increments request counter.
     * 2. Increments simulated market price.
     * 3. Returns current metric values.
     */
    @GetMapping("/api/metrics/test")
    public Map<String, Object> metricsTest() {

        requestCounter.increment();

        currentPrice.incrementAndGet();

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "requests",
                requestCounter.count()
        );

        response.put(
                "price",
                currentPrice.get()
        );

        return response;
    }
}