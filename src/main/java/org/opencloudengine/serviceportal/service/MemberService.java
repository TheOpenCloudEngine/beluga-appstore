package org.opencloudengine.serviceportal.service;

import org.opencloudengine.serviceportal.db.entity.User;
import org.opencloudengine.serviceportal.db.mapper.OrganizationMapper;
import org.opencloudengine.serviceportal.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean isUserExistsWithPassword(User user) {
        return userMapper.selectWithPassword(user) != null;
    }

    public boolean isOrgExists(String orgId) {
        return orgMapper.select(orgId) != null;
    }


}
