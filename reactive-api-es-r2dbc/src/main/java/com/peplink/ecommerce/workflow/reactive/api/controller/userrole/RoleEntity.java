package com.peplink.ecommerce.workflow.reactive.api.controller.userrole;

import java.util.Collection;
import java.util.UUID;

import org.javers.core.metamodel.annotation.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.peplink.ecommerce.workflow.reactive.api.controller.user.UserEntity;

import lombok.Data;

@Entity
@Data
@Table(value = "ops_api_role")
public class RoleEntity {

    @Id
//    @GeneratedValue(generator = "roleid_generator")
//    @GenericGenerator(
//        name = "roleid_generator",
//        strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

//    @Column(name = "code", length = 10, nullable = false, unique = true)
    private String code;

//    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

//    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

}
