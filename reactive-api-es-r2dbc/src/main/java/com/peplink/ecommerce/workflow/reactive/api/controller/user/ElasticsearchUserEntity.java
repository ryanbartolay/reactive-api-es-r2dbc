package com.peplink.ecommerce.workflow.reactive.api.controller.user;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.peplink.ecommerce.workflow.reactive.api.controller.user.UserEntity.UserAccountStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Document(indexName = "users")
public class ElasticsearchUserEntity {

    @Id
    private UUID uuid;

    private String email;

    private String password;

    private UUID createdBy;

    private Date creationDate;

    private UUID lastUpdateBy;

    private Date lastUpdateDate;

    @Field(type = FieldType.Object)
    private UserAccountStatus status;

}
