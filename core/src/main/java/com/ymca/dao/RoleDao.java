package com.ymca.dao;

import com.ymca.dao.GenericDao;
import com.ymca.model.Role;


public interface RoleDao extends GenericDao<Role, Long> {
    Role getRoleByName(String rolename);
}
