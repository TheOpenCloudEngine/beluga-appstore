package org.opencloudengine.serviceportal.bak;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.opencloudengine.serviceportal.db.entity.Organization;
import org.opencloudengine.serviceportal.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RequestMapping("/")
public class RestController {

//	private static Log successLogger = LogFactory.getLog("MeteringLog");
//	private static Log failLogger = LogFactory.getLog("MeteringFailLog");

//	private static org.apache.log4j.Logger successLogger = org.apache.log4j.Logger.getLogger("MeteringLog");
//	private static org.apache.log4j.Logger failLogger = org.apache.log4j.Logger.getLogger("MeteringLog");

	private static final String SUCCESS = "SUCCESS";
	private static final String POST_SUBSCRIBE_API = "%s/services/app/%s/subscribe";
	private static final String GET_APPINFO_API = "%s/services/app/%s";
	private static final String GET_INVOICE_API = "%s/services/app/%s/account/%s/invoice";
	private static final String GET_SUBSCRIPTION_API = "%s/services/app/%s/account/%s";
	private static final String GET_STATUS_API = "%s/services/app/%s/account/%s/service/%s/status";
	private static final String GET_UNBLOCK_API = "%s/services/app/%s/account/%s/service/%s/unblock";


    private static final String GET_TRUNCATE_USAGE_API = "%s/services/app/%s/settings/truncateUsageData";
    private static final String GET_TRUNCATE_ALL_API = "%s/services/app/%s/settings/truncateAllData";
	@Autowired
    private MainService mainService;

    @Value("#{systemProperties['bss.logPath']}")
    private String logPath;

	@Value("#{systemProperties['bss.appId']}")
	private String appId;

	@Value("#{systemProperties['bss.domain']}")
	private String domain;


	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public MainService getMainService() {
		return mainService;
	}

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
//	 * 로그인확인.
	 *
	 * @return 성공시 SUCCESS, 실패시 에러메시지.
	 */
	@ResponseBody
	@RequestMapping(value = "/user/loginCheck")
	public String loginCheck(String tenantId, String userId, String password, HttpSession session) {
		try {
			User user = mainService.getUserMapper().select(tenantId, userId, password);

			if (user != null) {
				Organization organization = mainService.getTenantMapper().select(tenantId);
//				if (user.getId().equals(organization.getAdminId())) {
//					//어드민 여부 확인.
//					user.setAdmin(true);
//				}
				session.setAttribute("user", user);
				return SUCCESS;
			} else {
				return "ERROR: Login information is not correct.";
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return t.getMessage();
		}
	}

	@RequestMapping(value = "/user/changeSubscription", method = RequestMethod.POST)
	@ResponseBody
	public String changeSubscription(String tenantId, String planId) {

		//TODO CALL to processcodi
		//restTemplate

		return null;
	}

	@RequestMapping("/user/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public String intertalExceptionHandler(Exception ex) {
		System.out.println(ex.getMessage());
		return ex.getMessage();
	}

	/**
	 * 관리자 회원가입. 소속사 정보까지 함께 입력.
	 * 일반사용자 회원가입. 테넌트 아이디만 정해서 가입함.
	 *
	 * @return 성공시 SUCCESS, 실패시 에러메시지.
	 */
//	@RequestMapping(value = "/user/signIn", method = RequestMethod.POST)
//	@ResponseBody
//	public String signIn(@RequestParam String tenantId, @RequestParam String userId
//			, @RequestParam(required = false) String tenantName, @RequestParam String userName, @RequestParam String userPassword
//			, @RequestParam(required = false) String admin) {
//
//		if (admin != null && admin.equals(User.ADMIN)) {
//			/*
//			* 관리자 가입로직.
//			* */
//			Organization organization = new Organization(tenantId, tenantName, userId);
//			User user = new User(userId, userName, userPassword, tenantId);
//			user.setAdmin(true);
//			try {
//				mainService.signInAdmin(organization, user);
//				return SUCCESS;
//			} catch (Exception e) {
//				//관리자 회원가입시 에러발생.
//				return "ERROR: " + e.getMessage();
//			}
//		} else {
//			/*
//			* 사용자 가입로직.
//			* */
//			User user = new User(userId, userName, userPassword, tenantId);
//			try {
//				mainService.signInUser(tenantId, user);
//				return SUCCESS;
//			} catch (Exception e) {
//				//관리자 회원가입시 에러발생.
//				return "ERROR: " + e.getMessage();
//			}
//		}
//	}

	/**
	 * 테넌트의 Plan subscribe
	 *
	 * @return 성공시 SUCCESS, 실패시 에러메시지.
	 */
	@ResponseBody
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public String subscribe(@RequestParam String tenantId, @RequestParam String planId) {
		try {
			String url = String.format(POST_SUBSCRIBE_API, domain, appId);
			return mainService.tenantSubscribe(url, tenantId, planId);
		} catch (Exception e) {
			return "Tenant subscribe request failed! " + e.getMessage();
		}
	}

	@RequestMapping("/login")
	public String login(HttpSession session) {
		return "login";
	}

	@RequestMapping("/memberRequest")
	public String memberRequest() {
		return "memberRequest";
	}

	@RequestMapping("/subscribe1")
	public String subscribe1(Model model) {
		return "subscribe1";
	}

	@RequestMapping("/subscribe2")
	public String subscribe2(String planId, Model model) {
		model.addAttribute("planId", planId);
		return "subscribe2";
	}


	@RequestMapping("/")
	public String main(HttpSession session, Model model) {

		try {
			//로그인체크.
			User user = (User) session.getAttribute("user");
			if (user != null) {
				//			//정보로딩.
				//			Map<String, Plan> planMap = mainService.getPlanMap(appId);
				//			String tenantId = user.getOrgId();
				//			Tenant tenant = mainService.getTenantMapper().select(tenantId);
				//			Subscription subscription = mainService.getSubscriptionMapper().select(tenantId);
				//			String planId = subscription.getPlanId();
				//			Plan plan = planMap.get(planId);
				//			model.addAttribute("plan", plan);
				//			model.addAttribute("tenant", tenant);
				//			model.addAttribute("subscription", subscription);

				String tenantId = user.getOrgId();
				Organization organization = mainService.getTenantMapper().select(tenantId);


				String restUrl = String.format(GET_SUBSCRIPTION_API, domain, appId, tenantId);
				//monthId 파라미터 추가로 전송한다.
//				String response = new RestTemplate().getForObject(restUrl, String.class);
				String response = callAjaxGet(restUrl);
                if(response.length() == 0) {
                    model.addAttribute("tenant", organization);
                } else{
                    JSONObject subscription = new JSONObject(response);
                    model.addAttribute("tenant", organization);
                    model.addAttribute("subscription", subscription);
                }


				return "main";
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		//로그인 서비스 소개 페이지로 보내어 회원가입을 유도한다.
		return "redirect:pricing";
	}

	@RequestMapping("/pricing")
	public String pricing(HttpSession session, Model model) {

		try {
			String restUrl = String.format(GET_APPINFO_API, domain, appId);
//			String response = new RestTemplate().getForObject(restUrl, String.class);
			String response = callAjaxGet(restUrl);

			JSONObject app = new JSONObject(response);
			Map<String, JSONObject> serviceMap = getServiceMap(app);
			User user = (User) session.getAttribute("user");
			if (user != null) {
//				Subscription subscription = mainService.getSubscriptionMapper().select(user.getOrgId());
//				String planId = subscription.getPlanId();
//				model.addAttribute("myPlanId", planId);
			}
			model.addAttribute("app", app);
			model.addAttribute("serviceMap", serviceMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "pricing";
	}

	private Map<String, JSONObject> getServiceMap(JSONObject app) throws JSONException {
		Map<String, JSONObject> serviceMap = new HashMap<String, JSONObject>();
		JSONArray serviceList = app.getJSONArray("serviceList");

		if (serviceList != null) {
			for (int i = 0; i < serviceList.length(); i++) {
				JSONObject service = serviceList.getJSONObject(i);
				serviceMap.put(service.getString("id"), service);
			}
		}
		return serviceMap;
	}

	private Map<String, JSONObject> getPlanMap(JSONObject app) throws JSONException {
		Map<String, JSONObject> planMap = new HashMap<String, JSONObject>();
		JSONArray planList = app.getJSONArray("planList");

		if (planList != null) {
			for (int i = 0; i < planList.length(); i++) {
				JSONObject service = planList.getJSONObject(i);
				planMap.put(service.getString("id"), service);
			}
		}
		return planMap;
	}

	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	public String invoice(Model model, HttpSession session, @RequestParam(required = false) String monthId) {
		try {
			User user = (User) session.getAttribute("user");
			if (user != null) {

				if (monthId == null) {
					SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
					monthId = f.format(new Date());
				}

				model.addAttribute("monthId", monthId);

				String restUrl = String.format(GET_APPINFO_API, domain, appId);
//				String response = new RestTemplate().getForObject(restUrl, String.class);
				String response = callAjaxGet(restUrl);
				JSONObject app = new JSONObject(response);

				String tenantId = user.getOrgId();
				Organization organization = mainService.getTenantMapper().select(tenantId);


				restUrl = String.format(GET_SUBSCRIPTION_API, domain, appId, tenantId);
				//monthId 파라미터 추가로 전송한다.
//				response = new RestTemplate().getForObject(restUrl, String.class);
				response = callAjaxGet(restUrl);
				JSONObject subscription = new JSONObject(response);
				model.addAttribute("tenant", organization);
				model.addAttribute("subscription", subscription);

				restUrl = String.format(GET_INVOICE_API, domain, appId, tenantId) + "?monthId=" + monthId;
//				response = new RestTemplate().getForObject(restUrl, String.class);
				response = callAjaxGet(restUrl);
				JSONObject invoice = new JSONObject(response);

				model.addAttribute("invoice", invoice);
			} else {
				return "redirect:/";
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "invoice";
	}

	@RequestMapping("/service")
	public String doService() {
		return "service";
	}

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	@RequestMapping("/call")
	@ResponseBody
	public String callService(@RequestParam String serviceId, HttpSession session) {

		User user = (User) session.getAttribute("user");
		String tenantId = user.getOrgId();
		String userId = user.getId();

		String resultTag = null;
		/*
		 * 블럭되었는지 먼저 판단한다.
		 */
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMM");
		String monthId = fm.format(new Date());
		try {
			String restUrl = String.format(GET_STATUS_API, domain, appId, tenantId, serviceId) + "?monthId=" + monthId;
			String response = callAjaxGet(restUrl);//restTemplate.getForObject(restUrl, String.class);
			if (response != null) {
				JSONObject serviceUsages = new JSONObject(response);

				int exceedValue = serviceUsages.getInt("isExceed");  //on이면 1.
				int blockValue = serviceUsages.getInt("isBlock");
                String message = serviceUsages.optString("msg");
				if (blockValue == 1) {
					//블럭됬으므로 무조건 리턴.
                    if("NOPLAN".equals(message) || "NOSERVICE".equals(message)) {
                        return message;
                    } else {
                        return "BLOCKED";
                    }
				}

				if (exceedValue == 1) {
					resultTag = "EXCEED";
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}


		////////////////////////////////////////////////////////////////////////////
		//
		//
		//
		//
		//            실제서비스 호출부분.
		try {
			System.out.println(String.format("%s %s,%s,%s,%s", "CALL", appId, tenantId, userId, serviceId));
			//
			// 미터링 로그를 기록한다.
			//
//			successLogger.info(String.format("\t%s\t%s\t%s\t%s", appId, tenantId, userId, serviceId ));
		}catch(Throwable t) {
//			failLogger.info(String.format("\t%s\t%s\t%s\t%s", appId, tenantId, userId, serviceId ));
		}
		//
		//
		//
		//
		////////////////////////////////////////////////////////////////////////////


		return "SUCCESS";
//		File f = new File(logPath);
//		if (!f.exists()) {
//			try {
//				f.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		OutputStreamWriter writer = null;
//		try {
//			writer = new OutputStreamWriter(new FileOutputStream(f, true), Charset.forName("utf-8"));
//			writer.write(String.format("%s\t%s\t{aid:'%s', cid:'%s', uid:'%s', sid:'%s'}"
//					, System.currentTimeMillis() + "", "metering.log", appId, tenantId, userId, serviceId));
//			writer.write("\n");
//			if(resultTag != null) {
//				return "SUCCESS/"+resultTag;
//			}else {
//				return "SUCCESS";
//			}
//		} catch (Exception e) {
//			return "ERROR: " + e.getMessage();
//		} finally {
//			if (writer != null) {
//				try {
//					writer.close();
//				} catch (IOException e) {
//				}
//			}
//		}
	}

	@RequestMapping("/unblock")
	@ResponseBody
	public String unblockService(@RequestParam String serviceId, HttpSession session) {

		User user = (User) session.getAttribute("user");
		String tenantId = user.getOrgId();
		String userId = user.getId();

		String resultTag = null;
		/*
		 * 블럭되었는지 먼저 판단한다.
		 */
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMM");
		String monthId = fm.format(new Date());
		try {
			String restUrl = String.format(GET_UNBLOCK_API, domain, appId, tenantId, serviceId) + "?monthId=" + monthId;
			String response = callAjaxGet(restUrl);//restTemplate.getForObject(restUrl, String.class);
			if (response != null) {
				JSONObject result = new JSONObject(response);

				if(result.getBoolean("success")){
					return "SUCCESS";
				}else{
					return result.getString("msg");
				}
			}

			return "FAIL";
		} catch (Throwable e) {
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}

	}

	private String callAjaxGet(String restUrl) throws IOException {
        System.out.format("MainController] restUrl : %s\n", restUrl);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(restUrl);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String buffer = "";
        while ((buffer = rd.readLine()) != null)
            result.append(buffer);

        return result.toString();
	}

    @RequestMapping("/settings")
    public String settings(){
        return "settings";
    }

	@RequestMapping("/members")
	public String members(Model model, HttpSession session){

		User user = (User) session.getAttribute("user");
		String tenantId = user.getOrgId();
		List<User> userList = mainService.getTenantUserList(tenantId);
		model.addAttribute("userList", userList);

		return "members";
	}


//	@RequestMapping("/invite")
//	@ResponseBody
//	public String inviteMember(HttpSession session, @RequestParam String userId, @RequestParam String userName, @RequestParam String userPassword) {
//		//로그인된 tenantId를 사용한다.
//		User userObj = (User) session.getAttribute("user");
//		String tenantId = userObj.getOrgId();
//
//		User user = new User(userId, userName, userPassword, tenantId);
//		try {
//			mainService.signInUser(tenantId, user);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return e.getMessage();
//		}
//		return "SUCCESS";
//	}
	/*
	* 테스트용. 모든 가입데이터를 지운다.
	* */
	@RequestMapping("/settings/truncateAllData")
	@ResponseBody
	public String truncateAllData() {
        mainService.getTenantMapper().truncate();
        mainService.getTenantUserMapper().truncate();
        mainService.getUserMapper().truncate();
        String restUrl = String.format(GET_TRUNCATE_ALL_API, domain, appId);
        try {
            return callAjaxGet(restUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    @RequestMapping("/settings/truncateUsageData")
    @ResponseBody
    public String truncateUsageData() {
        String restUrl = String.format(GET_TRUNCATE_USAGE_API, domain, appId);
        try {
            return callAjaxGet(restUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
