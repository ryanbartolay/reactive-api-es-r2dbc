package com.peplink.ecommerce.workflow.reactive.api.controller.user;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


//@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(value = "ops_api_user")
public class UserEntity {

    @Id
//    @GeneratedValue(generator = "userid_generator")
//    @GenericGenerator(
//        name = "userid_generator",
//        strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

//    @Column(name = "email", length = 200, nullable = false, unique = true)
//    @NotBlank
//    @Size(max = 200)
//    @Email
    private String email;

//    @Column(name = "password", length = 200)
//    @Size(max = 200)
    private String password;

//    @Column(name = "created_by", updatable = false)
    private UUID createdBy;

//    @Column(name = "creation_date", columnDefinition = "timestamp null default null", updatable = false)
    private Date creationDate;

//    @Column(name = "last_update_by")
    private UUID lastUpdateBy;

//    @Column(name = "last_update_date", columnDefinition = "timestamp null default null")
    private Date lastUpdateDate;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", length = 10)
    private UserAccountStatus status;

//    @Version
//    @Column(name = "version", columnDefinition = "integer default 0")
//    @NotNull
    private Integer version;
    
    public UserEntity(UUID uuid) {
        this.uuid = uuid;
    }
    public UserEntity(UUID uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }
    public UserEntity(UUID uuid, String email, String password, Date creation_date) {
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.creationDate = creation_date;
    }

//    @ManyToMany(cascade=CascadeType.ALL)
//    @JoinTable(name="ops_api_user_role", schema= "public", joinColumns={@JoinColumn(referencedColumnName="uuid")}
//                                    , inverseJoinColumns={@JoinColumn(referencedColumnName="uuid")}) 
//    private Set<RoleEntity> roles;

//    @PrePersist
//    void onCreate() {
//        Date dateToday = new Date();
//        this.creationDate = dateToday;
//        this.lastUpdateDate = dateToday;
//    }

//    @PreUpdate
//    void onPersist() {
//        this.lastUpdateDate = new Date();
//    }

    public static enum UserAccountStatus {

        ACTIVE,
        DELETED,
        INACTIVE,
        SUSPENDED
        ;

    }

}
