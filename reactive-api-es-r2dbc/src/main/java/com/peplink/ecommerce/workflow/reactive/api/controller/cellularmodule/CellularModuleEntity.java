package com.peplink.ecommerce.workflow.reactive.api.controller.cellularmodule;

import java.time.LocalDateTime;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
import javax.validation.constraints.Size;

//import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
//@Entity
//@Table(name = "ops_cellular_module")
@Accessors(chain = true)
public class CellularModuleEntity {

//    @Id
//    @ToString.Exclude
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false)
    private String id;

//    @Column(name = "module_num")
    private String moduleNumber;

//    @Column(name = "module_brand")
    private String moduleBrand;

//    @Column(name = "type_allocation_code")
    private String typeAllocationCode;

//    @Column(name = "fcc_id")
    private String fccId;

//    @Column(name = "fcc_ic")
    private String fccIc;

//    @Column(name = "name")
    private String name;

//    @Column(name = "band")
    @Size(max = 500)
    private String band;

//    @Column(name = "modified_user")
    private String modifiedUser;

//    @Column(name = "modified_dt")
    private LocalDateTime modifiedDate;

}
