package com.maomi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.feign.PayCallBackServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/alibaba/callback")
public class PayController {
	
	@Autowired
	private PayCallBackServiceFeign payCallBackServiceFeign;
	
	private static final String PAY_SUCCESS = "pay_success";

	@RequestMapping("/returnUrl")
	public void synCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		//拿到回掉的参数
		Map<String, String[]> requestParams = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		ResponseBase synCallBack = payCallBackServiceFeign.synCallBack(params);
		if(!synCallBack.getCode().equals(Constants.HTTP_RES_CODE_200)){
			writer.print("系统错误");;
		}
		LinkedHashMap resultData = (LinkedHashMap) synCallBack.getData();
		//转发post请求
		String htmlFrom = "<form name='punchout_form'"
				+ " method='post' action='http://127.0.0.1/callBack/synSuccessPage' >"
				+ "<input type='hidden' name='outTradeNo' value='" + resultData.get("out_trade_no") + "'>"
				+ "<input type='hidden' name='tradeNo' value='" + resultData.get("trade_no") + "'>"
				+ "<input type='hidden' name='totalAmount' value='" + resultData.get("total_amount") + "'>"
				+ "<input type='submit' value='立即支付' style='display:none'>"
				+ "</form><script>document.forms[0].submit();" + "</script>";
		writer.println(htmlFrom);
		writer.close();
	}
	
	// 同步回调,解决隐藏参数
	@RequestMapping(value = "/callBack/synSuccessPage", method = RequestMethod.POST)
	public String synSuccessPage(HttpServletRequest request, String outTradeNo, String tradeNo, String totalAmount) {
		request.setAttribute("outTradeNo", outTradeNo);
		request.setAttribute("tradeNo", tradeNo);
		request.setAttribute("totalAmount", totalAmount);
		return PAY_SUCCESS;
	}
	
	// 异步通知
	@RequestMapping("/notifyUrl")
	@ResponseBody
	public String asynCallBack(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String[]> requestParams = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return payCallBackServiceFeign.asynCallBack(params);
	}
}
