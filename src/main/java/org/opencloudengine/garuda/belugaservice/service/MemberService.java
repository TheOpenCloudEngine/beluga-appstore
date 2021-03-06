package org.opencloudengine.garuda.belugaservice.service;

import org.opencloudengine.garuda.belugaservice.db.entity.Organization;
import org.opencloudengine.garuda.belugaservice.db.entity.User;
import org.opencloudengine.garuda.belugaservice.db.mapper.OrganizationMapper;
import org.opencloudengine.garuda.belugaservice.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Service
public class MemberService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrganizationMapper orgMapper;

    public MemberService(){ }

    public User getUser(String userId) {
        return userMapper.select(userId);
    }

    public List<User> getUsers(String orgId) {
        return userMapper.selectInOrganization(orgId);
    }

    public boolean isUserExistsWithPassword(User user) {
        return userMapper.selectWithPassword(user) != null;
    }

    public boolean isOrgExists(String orgId) {
        return orgMapper.select(orgId) != null;
    }

    public Organization getOrganization(String id) {
        return orgMapper.select(id);
    }

    public void addOrganization(Organization organization) {
        orgMapper.insert(organization);
    }

    public void addUser(User user) {
        userMapper.insert(user);
    }

    public void deleteUser(String userId) {
        userMapper.delete(userId);
    }

    public void deleteOrganization(String orgId) {
        orgMapper.delete(orgId);
    }
}
