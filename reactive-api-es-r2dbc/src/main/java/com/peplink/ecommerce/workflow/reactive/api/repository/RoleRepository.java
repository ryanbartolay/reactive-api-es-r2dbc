package com.peplink.ecommerce.workflow.reactive.api.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.peplink.ecommerce.workflow.reactive.api.controller.user.UserEntity;
import com.peplink.ecommerce.workflow.reactive.api.controller.userrole.RoleEntity;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, UUID> {

    public Set<RoleEntity> findAllByUsers(UserEntity userEntity);
    public Set<RoleEntity> findAllByUsersUuid(UUID uuid);

}
