/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package com;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.ucpaas.restDemo.client.AbsRestClient;
import com.ucpaas.restDemo.client.JsonReqClient;
import com.ucpaas.restDemo.client.XmlReqClient;

public class RestTest {
	private String accountSid;
	private String authToken;
	
	public String getAccountSid() {
		return accountSid;
	}
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	static AbsRestClient InstantiationRestAPI(boolean enable) {
		if(enable) {
			return new JsonReqClient();
		} else {
			return new XmlReqClient();
		}
	}
	public static void testFindAccount(boolean json,String accountSid,String authToken){
		try {
			String result=InstantiationRestAPI(json).findAccoutInfo(accountSid, authToken);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testCreateClient(boolean json,String accountSid,String authToken,String appId,String clientName
			,String chargeType,String charge,String mobile){
		try {
			String result=InstantiationRestAPI(json).createClient(accountSid, authToken, appId, clientName, chargeType, charge,mobile);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testfindClients(boolean json,String accountSid,String authToken,String appId,String start
			,String limit){
		try {
			String result=InstantiationRestAPI(json).findClients(accountSid, authToken, appId, start, limit);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testfindClientByNbr(boolean json,String accountSid,String authToken,String clientNumber,String appId){
		try {
			String result=InstantiationRestAPI(json).findClientByNbr(accountSid, authToken, clientNumber,appId);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testCloseClient(boolean json,String accountSid,String authToken,String clientNumber,String appId){
		try {
			String result=InstantiationRestAPI(json).closeClient(accountSid, authToken, clientNumber,appId);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testChargeClient(boolean json,String accountSid,String authToken,String clientNumber,
			String chargeType,String charge,String appId){
		try {
			String result=InstantiationRestAPI(json).charegeClient(accountSid, authToken, clientNumber, chargeType, charge,appId);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testBillList(boolean json,String accountSid,String authToken,String appId,String date){
		try {
			String result=InstantiationRestAPI(json).billList(accountSid, authToken, appId, date);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testClientBillList(boolean json,String accountSid,String authToken,String appId,String clientNumber,String date){
		try {
			String result=InstantiationRestAPI(json).clientBillList(accountSid, authToken, appId, clientNumber, date);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testCallback(boolean json,String accountSid,String authToken,String appId,String fromClient,String to,String fromSerNum,String toSerNum){
		try {
			String result=InstantiationRestAPI(json).callback(accountSid, authToken, appId, fromClient, to,fromSerNum,toSerNum);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testVoiceCode(boolean json,String accountSid,String authToken,String appId,String to,String verifyCode){
		try {
			String result=InstantiationRestAPI(json).voiceCode(accountSid, authToken, appId, to, verifyCode);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testTemplateSMS(boolean json,String accountSid,String authToken,
	        String appId,String templateId,String to,String param){
		try {
			String result=InstantiationRestAPI(json).templateSMS(accountSid, authToken, appId, templateId, to, param);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void testfindClientByMobile(boolean json,String accountSid,String authToken,String mobile,String appId){
		try {
			String result=InstantiationRestAPI(json).findClientByMobile(accountSid, authToken, mobile, appId);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void testDispalyNumber(boolean json,String accountSid,String authToken,String appId,String clientNumber,String display){
		try {
			String result=InstantiationRestAPI(json).dispalyNumber(accountSid, authToken, appId, clientNumber, display);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 测试说明 参数顺序，请参照各具体方法参数名称
	 * 参数名称含义，请参考rest api 文档
	 * @author Glan.duanyj
	 * @date 2014-06-30
	 * @param params[]
	 * @return void
	 * @throws IOException 
	 * @method main
	 */
	public static String  ACCOUNTSID ="987e6a016f37fc8b7bf5f7ffee8e281b";
	public static String  TOKEN ="fcb80731d79de90dd116efdab30d150b";
	public static String  APPID ="94ad846bd460493c87036255c8b90673";
	
	//发送验证码
	public static void sendVCode(String phone,String vCode) {
		String templateId="64292";
		testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,vCode);
	}
	

	
	
	//预约2小时前提醒
	public static void orderAppointment2HourBefore(String phone,String orderId) {
		String templateId="241036";
		testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,orderId);
	}
	
	//预约成功，提醒
    public static void orderAppointmentSuccess(String phone,String orderId,java.sql.Timestamp appointmentTime) {
        String templateId="98210";
        String t = "";
        if(appointmentTime != null) {
            t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appointmentTime);
        }
        testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,orderId + "," + t);
    }
	
	
	//取消订单
	public static void cancelOrder(String phone,String orderId) {
        String templateId="241026";
        testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,orderId);
    }
	
	//订单借戴成功 订单id + 截止日期
	public static void orderSuccess(String phone,Integer orderId,java.sql.Timestamp endTime) {
		String templateId="75977";
		String e = "";
		if(endTime != null) {
			e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime);
		}
		testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, 
		        templateId,phone,orderId + "," + e);
	}
	
	
	
	//生日
    public static void shengri(String phone) {
        String templateId="237692";
        testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,"");
    }
    
    
    //到期前一天
    public static void orderYuqiBefore1Day(String phone,String orderId) {
        String templateId="241043";
        testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,orderId);
    }
    //到期当天
    public static void orderYuqiDay(String phone,String orderId) {
        String templateId="241044";
        testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,orderId);
    }
    //到期后每天
    public static void orderYuqiDayAfter(String phone,String orderId,String days) {
        String templateId="241045";
        testTemplateSMS(true, ACCOUNTSID,TOKEN,APPID, templateId,phone,orderId+","+days);
    }
    
	
	public static void main(String[] args) throws IOException {
		
		
	    orderAppointment2HourBefore("13323718530","2017000023");
	    
//		String accountSid="987e6a016f37fc8b7bf5f7ffee8e281b";
//		String token="fcb80731d79de90dd116efdab30d150b";
//		String appId="94ad846bd460493c87036255c8b90673";
//		String templateId="64292";
//		String to="15999627307";
//		String para="1234";
//		testTemplateSMS(true, accountSid,token,appId, templateId,to,para);
		
		
		/*testTemplateSMS(json, accountSid,token,appId, templateId,to,para);
		
		
//		String jsonStr="{\"client\":\"1\"}";
//		JSONObject obj=JSONObject.fromObject(jsonStr);
//		System.out.println(obj.getInt("client"));
		System.out.println("请输入参数，以空格隔开...");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String param=br.readLine();
		String [] params=param.split(" ");
		String method = params[0];
		boolean json=true;
		if (params[1].equals("xml")) {
			json=false;
		}
		if (method.equals("1")) {
			String accountSid="";
			String token="";
			testFindAccount(json,accountSid,token);
		}else if (method.equals("2")) {
			testCreateClient(json, params[2],params[3], params[4], params[5], params[6], params[7], params[8]);
		}else if (method.equals("3")) {
			String accountSid="";
			String token="";
			String appId="";
			testfindClients(json,accountSid,token,appId,"0","5");
		}else if (method.equals("4")) {
			testfindClientByNbr(json,params[2],params[3], params[4], params[5]);
		}else if (method.equals("5")) {
			testCloseClient(json, params[2],params[3], params[4], params[5]);
		}else if (method.equals("6")) {
			testChargeClient(json, params[2],params[3], params[4], params[5], params[6], params[7]);
		}else if (method.equals("7")) {
			testBillList(json, params[2], params[3],params[4], params[5]);
		}else if (method.equals("8")) {
			testClientBillList(json, params[2], params[3],params[4],params[5], params[6]);
		}else if (method.equals("9")) {
			String accountSid = "";// 主账户Id
			String authToken="";
			String appId="";
			accountSid="";
			authToken="";
			appId="";
			String fromClient="";
			String to="";
			String fromSerNum="";
			String toSerNum="";
			testCallback(json, accountSid, authToken, appId, fromClient, to, fromSerNum, toSerNum);
		}else if (method.equals("10")) {
			String to="";
			String accountSid="";
			String token="";
			String appId="";
			String para = "";
			testVoiceCode(json, accountSid, token, appId, to, para);
		}else if (method.equals("11")) { //短信验证码 
			String accountSid="";
			String token="";
			String appId="";
			String templateId="";
			String to="";
			String para="";
			testTemplateSMS(json, accountSid,token,appId, templateId,to,para);
		}else if (method.equals("12")) {
			testfindClientByMobile(json, params[2],params[3], params[4], params[5]);
		}else if (method.equals("13")) {
			String accountSid="";
			String token="";
			String clientNumber="";
			String appId="";
			String display="1";
			testDispalyNumber(json, accountSid, token, appId, clientNumber, display);
		}*/
	}
}
