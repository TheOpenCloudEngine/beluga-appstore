package org.opencloudengine.service.web.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.opencloudengine.service.web.db.dao.TenantMapper;
import org.opencloudengine.service.web.db.dao.TenantUserMapper;
import org.opencloudengine.service.web.db.dao.UserMapper;
import org.opencloudengine.service.web.db.entity.Organization;
import org.opencloudengine.service.web.db.entity.TenantUser;
import org.opencloudengine.service.web.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainService {

	@Autowired
	private UserMapper userMapper;

    @Autowired
    private TenantUserMapper tenantUserMapper;

    @Autowired
    private TenantMapper tenantMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public TenantUserMapper getTenantUserMapper() {
		return tenantUserMapper;
	}

	public TenantMapper getTenantMapper() {
		return tenantMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setTenantUserMapper(TenantUserMapper tenantUserMapper) {
		this.tenantUserMapper = tenantUserMapper;
	}

	public void setTenantMapper(TenantMapper tenantMapper) {
		this.tenantMapper = tenantMapper;
	}

	/**
	 * 테넌트 관리자 회원가입.
	 * */
	public void signInAdmin(Organization organization, User user) throws Exception {
		Organization prevOrganization = tenantMapper.select(organization.getId());
		//테넌트 이미 존재시 에러. Admin으로 가입불가.
		if(prevOrganization != null) {
			throw new RuntimeException("Tenant already exists : " + organization.getId());
		}

		User prevUser = userMapper.selectById(organization.getId(), user.getId());
		//테넌트에 동일 사용자 존재시 에러.
		if(prevUser != null) {
			throw new RuntimeException("User already exists : " + organization.getId() + "/" + user.getId());
		}
		TenantUser prevTenantUser = tenantUserMapper.select(organization.getId(), user.getId());
		//테넌트-사용자 매핑에 동일 사용자 존재시 에러.
		if(prevTenantUser != null) {
			throw new RuntimeException("Tenant-User already exists : " + organization.getId() + "/" + user.getId());
		}

		TenantUser tenantUser = new TenantUser(organization.getId(), user.getId());
		//1.테넌트추가.
		tenantMapper.insert(organization);
		//2.사용자 추가.
		userMapper.insert(user);
		//3.매핑추가.
		tenantUserMapper.insert(tenantUser);
	}

	/**
	 * 테넌트별 사용자 회원가입.
	 * */
	public void signInUser(String tenantId, User user) throws Exception {
		Organization prevOrganization = tenantMapper.select(tenantId);
		//테넌트 미 존재시 에러. User로 가입불가
		if(prevOrganization == null) {
			throw new RuntimeException("No such tenant : " + tenantId);
		}

		User prevUser = userMapper.selectById(tenantId, user.getId());
		//테넌트에 동일 사용자 존재시 에러.
		if(prevUser != null) {
			throw new RuntimeException("User already exists : " + tenantId + "/" + user.getId());
		}
		TenantUser prevTenantUser = tenantUserMapper.select(tenantId, user.getId());
		//테넌트-사용자 매핑에 동일 사용자 존재시 에러.
		if(prevTenantUser != null) {
			throw new RuntimeException("Tenant-User already exists : " + tenantId + "/" + user.getId());
		}

		TenantUser tenantUser = new TenantUser(tenantId, user.getId());
		//1.사용자 추가.
		userMapper.insert(user);
		//2.매핑추가.
		tenantUserMapper.insert(tenantUser);
	}

	public User selectUser(String tenantId, String userId, String password){
		return userMapper.select(tenantId, userId, password);
	}

	public void signIn(User user, String planId, String domain, String appId) throws Exception {
		userMapper.insert(user);

//		if(user.getAdmin().equals("1"))
//			return;

		int status = 0 ;
		try{
			String tenantId  = user.getOrgId();
//			status = tenantSubscribe(domain, appId, planId, user.getOrgId());
			String url = String.format("%s/services/app/%s/tenant/%s", domain, appId, tenantId);

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(url);

			request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("tenantId",tenantId));
			nameValuePairs.add(new BasicNameValuePair("planId",planId));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String buffer = "";
			while ((buffer = rd.readLine()) != null)
				result.append(buffer);

			status = response.getStatusLine().getStatusCode();
			if(status != 200) {
				throw new Exception("Tenant subscribe failed!" + response.getStatusLine().getReasonPhrase());
			}

		}catch(Exception e){
			throw new Exception("Tenant subscribe request failed! " + e.getMessage());
		}

	}


	public String tenantSubscribe(String url, String tenantId, String planId) throws IllegalStateException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);

		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		List<NameValuePair> nameValuePairs =
				new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("accountId",tenantId));
		nameValuePairs.add(new BasicNameValuePair("planId",planId));
		request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String buffer = "";
		while ((buffer = rd.readLine()) != null)
			result.append(buffer);

		return result.toString();
	}

	public List<User> getTenantUserList(String tenantId) {
		return userMapper.selectByTenant(tenantId);
	}

}
