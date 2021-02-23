package com.BillCalculator.service;

import com.BillCalculator.config.ESConfig;
import com.BillCalculator.dto.ElasticLog;
import com.BillCalculator.entity.LogEntity;
import com.BillCalculator.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;
    private final ESConfig esConfig;


    @Async
    public void Log(String logId, String className, String title, String message, int code) {
        LogToDB(logId, className, title, message, code);
        LogToELK(logId, className, title, message, code);
    }

    private void LogToDB(String logId, String className, String title, String message, int code) {
        LogEntity log = new LogEntity();
        log.setLogId(logId);
        log.setClassName(className);
        log.setLogCode(code);
        log.setTitle(title);
        log.setMessage(message);
        logRepository.saveAndFlush(log);
    }

    private void LogToELK(String logId, String className, String title, String message, int code) {
        try {
            String indexName = "billcalculatorlog";
            boolean isIndex = esConfig.client().indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
            if (!isIndex) {
                AcknowledgedResponse acknowledgedResponse = esConfig.client().indices().create(new CreateIndexRequest(indexName), RequestOptions.DEFAULT);
                PutMappingRequest putMappingRequest = new PutMappingRequest(indexName);
                putMappingRequest
                        .source("{\n" +
                                "      \"properties\": {\n" +
                                "        \"logId\": {\n" +
                                "          \"type\": \"keyword\"\n" +
                                "        },\n" +
                                "        \"className\": {\n" +
                                "          \"type\": \"text\"\n" +
                                "        },\n" +
                                "        \"logCode\": {\n" +
                                "          \"type\": \"text\"\n" +
                                "        },\n" +
                                "        \"title\": {\n" +
                                "          \"type\": \"text\"\n" +
                                "        },\n" +
                                "        \"message\": {\n" +
                                "          \"type\": \"text\"\n" +
                                "        },\n" +
                                "        \"createdDate\": {\n" +
                                "          \"type\": \"date\"\n" +
                                "        }\n" +
                                "      }\n" +
                                "}", XContentType.JSON);

                AcknowledgedResponse acknowledgedPutResponse = esConfig.client().indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
            }
            ElasticLog elasticLog = new ElasticLog();
            elasticLog.setLogId(logId);
            elasticLog.setClassName(className);
            elasticLog.setLogCode(code);
            elasticLog.setTitle(title);
            elasticLog.setMessage(message);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
            elasticLog.setCreatedDate(ZonedDateTime.now().format(formatter));

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(elasticLog);

            IndexRequest request = new IndexRequest(indexName);
            request.source(jsonString, XContentType.JSON);

            IndexResponse response = esConfig.client().index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
