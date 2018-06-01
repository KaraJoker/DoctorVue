package com.chero.client.dao;

import org.apache.ibatis.annotations.Mapper;

import com.chero.client.domain.SystemRole;

@Mapper
public interface SystemRoleRepository extends BaseRepository<SystemRole>{

    String findSystemRoleByRoleId(Integer roleId);

    void saveSystemRole(Integer roleId);
}
