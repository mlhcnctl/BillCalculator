package com.BillCalculator.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ElasticLog implements Serializable {

    private String logId;
    private String title;
    private String message;
    private String className;
    private int logCode;
    private String createdDate;

}
