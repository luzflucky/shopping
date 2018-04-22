package com.maomi.api.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.maomi.api.service.PayCallBackService;
import com.maomi.base.BaseController;
import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.domain.PaymentInfo;
import com.maomi.fegin.OrderServiceFegin;
import com.maomi.mapper.PaymentInfoMapper;
import com.maomi.pay.AlipayConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PayCallBackServiceImpl extends BaseController implements PayCallBackService{
	
	@Autowired
	private PaymentInfoMapper paymentInfoMapper;
	
	@Autowired
	private OrderServiceFegin orderServiceFegin;

	@Override
	public ResponseBase synCallBack(@RequestParam Map<String, String> params) {
		// 获取支付宝GET过来反馈信息
		try {
			log.info("####同步回调开始####params:",params);
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
			if (!signVerified) {
				return setResultError("验签失败!");
			}
			// 商户订单号
			String out_trade_no = params.get("out_trade_no");
			// 支付宝交易号
			String trade_no = params.get("trade_no");
			// 付款金额
			String total_amount = params.get("total_amount");
			JSONObject data = new JSONObject();
			data.put("out_trade_no", out_trade_no);
			data.put("trade_no", trade_no);
			data.put("total_amount", total_amount);
			return setResultSuccess(data);
		} catch (Exception e) {
			log.info("######PayCallBackServiceImpl##ERROR:#####", e);
			return setResultError("系统错误!");
		}finally{
			log.info("####同步回调结束####params:",params);
		}
	}

	@Override
	public String asynCallBack(@RequestParam Map<String, String> params) {
		//此处为post回调
		try {
			log.info("####异步回调开始####params:",params);
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
			//延签失败，需要支付宝重试
			if (!signVerified) {
				return Constants.PAY_FIAL;
			}
			//如果需要解决并发问题此处加锁
			// 商户订单号
			String orderNumber = params.get("out_trade_no");
			PaymentInfo payInfo = paymentInfoMapper.queryPaymentInfoByOrderNumber(orderNumber);
			//如果交易状态为1，说明已经成功交易，提示支付宝无需再次重试
			if(payInfo.getState()==1){
				return Constants.PAY_SUCCESS;
			}
			// 支付宝交易号
			String tradeNo = params.get("trade_no");
			// 付款金额
			String totalAmount = params.get("total_amount");
			//此处需要判断回调金额和我们查询的支付金额是否一致
			//如果不一致需要异常 退款
			payInfo.setPlatformorderid(tradeNo);
			payInfo.setState(1);
			payInfo.setPayMessage(params.toString());
			//更新支付表
			int result = paymentInfoMapper.updatePayMentInfo(payInfo);
			if(result<=0){
				return Constants.PAY_FIAL;
			}
			//更新下单表
			ResponseBase resultOrder = orderServiceFegin.updateOrderId(1, tradeNo, orderNumber);
			if(!resultOrder.getCode().equals(Constants.HTTP_RES_CODE_200)){
				//如果此处更新下单表失败 此处应该事物回滚
				return Constants.PAY_FIAL;
			}
			return Constants.PAY_SUCCESS;
		} catch (Exception e) {
			log.info("######asynCallBack##ERROR:#####", e);
			return Constants.PAY_FIAL;
		}finally{
			log.info("####异步回调结束####params:",params);
		}
	}

}
