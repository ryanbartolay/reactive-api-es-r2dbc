package com.peplink.ecommerce.workflow.reactive.api.controller.cellularmodule;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, prefix = "m_")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CellularModuleApi implements Serializable {

    private static final long serialVersionUID = 1L;

    private String m_id;
    private String m_moduleNumber;
    private String m_moduleBrand;
    private String m_typeAllocationCode;
    private String m_fccId;
    private String m_fccIc;
    private String m_name;
    private String m_band;
    private String m_modifiedUser;
    private LocalDateTime m_modifiedDate;

}
