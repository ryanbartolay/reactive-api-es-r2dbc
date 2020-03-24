package com.peplink.ecommerce.workflow.reactive.api.controller.user;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, prefix = "m_")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserApi implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID m_uuid;
    private String m_email;
    private String m_password;
    private Integer m_createdBy;
    private Date m_creationDate;
    private Integer m_lastUpdateBy;
    private Date m_lastUpdateDate;
    private String m_status;
    private Integer m_version;

}
