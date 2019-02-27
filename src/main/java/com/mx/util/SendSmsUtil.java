package com.mx.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mx.collection.DataRow;
import com.mx.collection.DataTable;
import com.mx.html5.DataAuthResultDO;
import com.mx.reader.Reader;
import com.mx.sql.util.ISqlEngine;
import com.mx.sql.util.SqlEngine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 小米线儿
 * @date 2017/8/21
 */
public class SendSmsUtil {
    private static Logger logger = LoggerFactory.getLogger(SendSmsUtil.class);
    //添加应用
    private static final String addAppUrl = "https://sms-api.hzja.net/AddSMSApp.json";
    //发送短信
    private static final String sendSmsUrl = "https://sms-api.hzja.net/SendMsg.json";
    //黑名单校验
    private static final String blackCheckUrl = "https://sms-api.hzja.net/SMSBlackCheck.json";
    //添加短信签名
    private static final String addSignatureUrl = "https://sms-api.hzja.net/AddSignature.json";
    //删除短信签名
    private static final String delSignatureUrl = "https://sms-api.hzja.net/DelSignature.json";
    //添加短信模版
    private static final String addTemplateUrl="https://sms-api.hzja.net/AddTemplet.json";
    //删除短信模版
    private static final String delTemplateUrl="https://sms-api.hzja.net/DelTemplet.json";
    //获取短信报告
    private static final String getReportUrl="https://sms-api.hzja.net/Report.json";
    private static String DCS = "8";
    private static String encodeType = "utf-8";

    public static String addApp(String appName){
        Map<String, String> map = Maps.newHashMap();
        map.put("UserName", SmsConfig.getUserName());
        map.put("AppName",appName);
        long dateTime=new Date().getTime()/1000;
        String token= Md5_Util.getMD5(appName+dateTime+SmsConfig.getUserName()+Md5_Util.getMD5(SmsConfig.getPassword()));
        map.put("DateTime",dateTime+"");
        map.put("Token",token);
        String json = JSONObject.toJSONString(map);
        String ret = HttpUtil.doPost(addAppUrl, json);
        logger.info("------------添加短信应用返回信息：------------"+ret);
        return ret;
    }

    public static String addSignature(String appId, String smsSign,String notifyUrl) {
        Map<String, String> map = Maps.newHashMap();
        map.put("UserName", SmsConfig.getUserName());
        map.put("AppID",appId);
        map.put("SMSSign",smsSign);
        long dateTime=new Date().getTime()/1000;
        String token= Md5_Util.getMD5(appId+dateTime+smsSign+SmsConfig.getUserName()+Md5_Util.getMD5(SmsConfig.getPassword()));
        map.put("DateTime",dateTime+"");
        map.put("Notify_Url",notifyUrl);
        map.put("Token",token);
        String json = JSONObject.toJSONString(map);
        logger.info("------添加短信模版签名请求参数:{}",json);
        String ret = HttpUtil.doPost(addSignatureUrl, json);
        logger.info("------------添加短信签名返回信息：------------"+ret);
        JSONObject jsonObject = JSONObject.parseObject(ret);
        if(jsonObject.get("Status").equals(0)){
            return String.valueOf(jsonObject.getJSONObject("Info").get("id"));
        }else{
            logger.error("添加短信签名error",ret);
        }
        return null;
    }
    public static boolean delSignature(String appId,String smsSign){
        Map<String, String> map = Maps.newHashMap();
        map.put("UserName", SmsConfig.getUserName());
        map.put("AppID",appId);
        map.put("SMSSign",smsSign);
        long dateTime=new Date().getTime()/1000;
        String token= Md5_Util.getMD5(appId+dateTime+smsSign+SmsConfig.getUserName()+Md5_Util.getMD5(SmsConfig.getPassword()));
        map.put("DateTime",dateTime+"");
        map.put("Token",token);
        String json = JSONObject.toJSONString(map);
        String ret = HttpUtil.doPost(delSignatureUrl, json);
        logger.info("------------删除短信签名返回信息：------------"+ret);
        JSONObject jsonObject = JSONObject.parseObject(ret);
        if(jsonObject.get("Status").equals(0)){
            return true;
        }else{
            logger.error("删除短信签名error",ret);
        }
        return false;
    }

    public static String addTemplate(String appId, String smsType, String smsContent,String notifyUrl) {
        Map<String, String> map = Maps.newHashMap();
        map.put("UserName", SmsConfig.getUserName());
        map.put("AppID",appId);
        map.put("SMSType",smsType);
        smsContent= smsContent.replaceAll("\\{[\\u4e00-\\u9fa5]*}", "%");
        map.put("SMSContent",smsContent);
        long dateTime=new Date().getTime()/1000;
        String token=Md5_Util.getMD5(appId+dateTime+smsContent+smsType+SmsConfig.getUserName()+Md5_Util.getMD5(SmsConfig.getPassword()));
        map.put("DateTime",dateTime+"");
        map.put("Notify_Url",notifyUrl);
        map.put("Token",token);
        String json = JSONObject.toJSONString(map);
        logger.info("------添加短信模版请求参数:{}",json);
        String ret = HttpUtil.doPost(addTemplateUrl, json);
        logger.info("------添加短信模版返回信息：------"+ret);
        JSONObject jsonObject = JSONObject.parseObject(ret);
        if(jsonObject.get("Status").equals(0)){
            return String.valueOf(jsonObject.getJSONObject("Info").get("id"));
        }else{
            logger.error("添加短信模板error",ret);
        }
        return null;
    }
    public static boolean delTemplate(String appId,String smsContent){
        Map<String, String> map = Maps.newHashMap();
        map.put("UserName", SmsConfig.getUserName());
        map.put("AppID",appId);
        smsContent= smsContent.replaceAll("\\{[\\u4e00-\\u9fa5]*}", "%");
        map.put("SMSContent",smsContent);
        long dateTime=new Date().getTime()/1000;
        String token= Md5_Util.getMD5(appId+dateTime+smsContent+SmsConfig.getUserName()+Md5_Util.getMD5(SmsConfig.getPassword()));
        map.put("DateTime",dateTime+"");
        map.put("Token",token);
        String json = JSONObject.toJSONString(map);
        String ret = HttpUtil.doPost(delTemplateUrl, json);
        logger.info("------------删除短信模版返回信息：------------"+ret);
        JSONObject jsonObject = JSONObject.parseObject(ret);
        if(jsonObject.get("Status").equals(0)){
            return true;
        }else{
            logger.error("删除短信模板error",ret);
        }
        return false;
    }
    public static DataAuthResultDO smsBlackCheck(String text) {
        Map<String, String> map = Maps.newHashMap();
        map.put("Text", text);
        String json = JSONObject.toJSONString(map);
        String ret = HttpUtil.doPost(blackCheckUrl, json);
        logger.info("------------短信敏感词校验------------"+ret);
        JSONObject jsonObject = JSONObject.parseObject(ret);
        if (jsonObject != null && "0".equals(jsonObject.get("status"))) {
            List<String> forbiddenWords = (List<String>) jsonObject.get("ForbiddenWords");
            if (CollectionUtils.isEmpty(forbiddenWords)) {
                return new DataAuthResultDO(null, true);
            } else {
                return new DataAuthResultDO(forbiddenWords.toString(), false);
            }
        } else {
            return new DataAuthResultDO("违禁词校验接口异常：" + jsonObject.get("err").toString(), false);
        }
    }

    private static boolean validateParam(String mobile, String param) {
        if (StringUtils.isBlank(mobile)) {
            //logger.error(UserErrorCode.MOBILE_ISEMPTY.toString());
            return false;
        }
        if (StringUtils.isBlank(param)) {
           // logger.error(UserErrorCode.PARAM_ERROR.toString());
            return false;
        }
        return true;
    }

    public static String sendSMS(String appId,String appKey,String signature,String mobiles, String content) {
        if(StringUtils.isBlank(appId)){
            appId=SmsConfig.getAppId();
        }
        if(StringUtils.isBlank(appKey)){
            appKey=SmsConfig.getAppKey();
        }
        if(StringUtils.isBlank(signature)){
            signature=SmsConfig.getTitle();
        }
        if (validateParam(mobiles, content)) {
            try {
                String requestUrl = sendSmsUrl + "?AppID=" + URLEncoder.encode(appId, encodeType) + "&AppKey="
                        + URLEncoder.encode(appKey, encodeType) + "&DCS=" + URLEncoder.encode(DCS, encodeType)
                        + "&Callee=" + URLEncoder.encode(revertMobiles(mobiles), encodeType) + "&Text="
                        + URLEncoder.encode(signature + content, encodeType);
                logger.info(requestUrl);
                return HttpUtil.doGet(requestUrl);
            } catch (IOException e) {
                logger.error("------------短信发送失败-----------",e.toString());
            }
        }
        return null;
    }
    public static List<Map<String,String>> getReport(String appId,String appKey,String maxCount){
        Map<String, Object> map = Maps.newHashMap();
        map.put("AppID", appId);
        map.put("AppKey", appKey);
        map.put("MaxCount", maxCount);
        String requestUrl = getReportUrl + "?AppID=" +appId+ "&AppKey="+ appKey + "&MaxCount=" + maxCount;
        String ret = HttpUtil.doGet(requestUrl);
        JSONObject jsonObject = JSONObject.parseObject(ret);
        if ("0".equals(jsonObject.get("Status"))) {
            return (List<Map<String,String>>) jsonObject.get("Report");
        } else {
            logger.error("获取短信报告异常:{}",ret);
        }
        return null;
    }

    private static String revertMobiles(String mobiles) {
        String[] split = mobiles.split(",");
        if (split.length == 1) {
            return "86" + mobiles;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i < (split.length - 1)) {
                sb.append("86" + split[i] + ",");
            } else {
                sb.append("86" + split[i]);
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) throws IOException {
         List<Map<String,String>> list=  getReport("33MTD545EQSG","KUDMNRH5","10");
        System.out.println(list);
//        String s = SendSmsUtil.sendSMS("", "", "", "13598001346", "您的验证码是4444,请在5分钟内使用！");
//        System.out.println(s);
//        List<JSONArray> report = getReport("MN6RX2RXHUZW", "XE4R5KH3", "1");
//        System.out.println(report);
//        String str="测试模版添加，尊敬的客户{客户姓名}，很抱歉，由于{审批拒绝原因}，您的{贷款名字}贷款申请已被我行审批拒绝！";
//        sendSMS("MN6RX2RXHUZW","XE4R5KH3","【钱袋001】","18627175986","您的验证码是242311，请在三分钟内使用！");
//		  boolean flag = sendSMSService.addSignature("baihang4AUgE0xM", "助贷宝");
//        boolean flag =addTemplate("4ERKURCR9XQH", "1", "尊敬的客户%，您好！很抱歉，由于%，您的借款申请已被我行审批拒绝。祝您生活愉快！","");
//        System.out.println(flag);
//         DataAuthResultDO dataAuthResultDO =
//        DataAuthResultDO dataAuthResultDO = smsBlackCheck("尊敬的客户{客户姓名}，您好！我是{银行名字}的客户经理{客户经理姓名}，您的{贷款名字}现场调查时间为{预约调查时间}，地点为{预约地点}。\n" +
//                "      届时请您准备好相关材料：发票，你本人的身份证、户口本、收入情况证明材料；配偶的身份证、收入情况证明材料；经营场所营业执照、租赁合同、账本、进货票据、存货信息；车辆行驶证、产权证；房屋产权证、房屋合同。\n" +
//                "     有什么问题请随时拨打电话{客户经理手机号}，祝您生活愉快！");
//        System.out.println(dataAuthResultDO.getFlag() + ":" +
//		 dataAuthResultDO.getData());
    }
    
    
    static class SmsConfig {
    	private static String userName;
    	private static String password;
    	private static String appId;
    	private static String appKey;
    	private static String title;

        public static String getUserName(){
            ISqlEngine ise =  SqlEngine.instance();
            DataTable table = ise.getDataTable("getSmsConfig",new Object[0]);
            if(table!=null && table.rowSize()>0){
            	DataRow row = table.getRow(0);
            	userName = Reader.stringReader().getValue(row, "USER_NAME");
            }
            return userName;
        }
        
        public static String getPassword(){
            ISqlEngine ise =  SqlEngine.instance();
            DataTable table = ise.getDataTable("getSmsConfig",new Object[0]);
            if(table!=null && table.rowSize()>0){
            	DataRow row = table.getRow(0);
            	password = Reader.stringReader().getValue(row, "PASS_WORD");
            }
            return password;
        }
        
        public static String getAppId(){
            ISqlEngine ise =  SqlEngine.instance();
            DataTable table = ise.getDataTable("getSmsConfig",new Object[0]);
            if(table!=null && table.rowSize()>0){
            	DataRow row = table.getRow(0);
            	appId = Reader.stringReader().getValue(row, "APP_ID");
            }
            return appId;
        }
        
        public static String getAppKey(){
            ISqlEngine ise =  SqlEngine.instance();
            DataTable table = ise.getDataTable("getSmsConfig",new Object[0]);
            if(table!=null && table.rowSize()>0){
            	DataRow row = table.getRow(0);
            	appKey = Reader.stringReader().getValue(row, "APP_KEY");
            }
            return appKey;
        }
        
        public static String getTitle(){
        	ISqlEngine ise =  SqlEngine.instance();
            DataTable table = ise.getDataTable("getSmsConfig",new Object[0]);
            if(table!=null && table.rowSize()>0){
            	DataRow row = table.getRow(0);
            	title = Reader.stringReader().getValue(row, "SMS_TITLE");
            }
            return title;
        }


    }
}
