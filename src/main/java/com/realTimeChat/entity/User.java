package com.realTimeChat.entity;

import jdk.jfr.Enabled;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class User {

    @Id
    private String nickName;
    private String fullName;
    private Status status;
}
