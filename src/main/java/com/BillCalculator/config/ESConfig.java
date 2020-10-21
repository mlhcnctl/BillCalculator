package com.BillCalculator.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {

    @Value("${elasticsearch-url}")
    private String host;

    @Bean
    public RestHighLevelClient client() {
        String hostName = host.split(":")[0];
        int port = Integer.parseInt(host.split(":")[1]);

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostName, port, "http")));
        return client;
    }

}
