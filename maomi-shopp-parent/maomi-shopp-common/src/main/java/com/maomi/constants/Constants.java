package com.maomi.constants;
public interface Constants {
	// 响应code
	String HTTP_RES_CODE_NAME = "code";
	// 响应msg
	String HTTP_RES_CODE_MSG = "msg";
	// 响应data
	String HTTP_RES_CODE_DATA = "data";
	// 响应请求成功
	String HTTP_RES_CODE_200_VALUE = "success";
	// 系统错误
	String HTTP_RES_CODE_500_VALUE = "fial";
	// error
	String ERROR = "error";
	// token
	String TOKEN = "token";
	// openId
	String OPEN_ID = "openId";
	// email
	String MSG_EMAIL = "email";
	// header
	String HEADER = "header";
	// interfaceType
	String INTERFACE_TYPE = "interfaceType";
	// 消息主题
	String CONTENT = "content";
	// token member
	String MEMBER_TOKEN = "member";
	// token pay
	String PAY_TOKEN = "pay";
	// username
	String USER_NAME = "username";
	// payInfo
	String PAY_INFO_HTML = "payInfoHtml";
	// token有效期
	Long TOKEN_TIME = (long) (60*60*24*30);
	// 响应请求成功code
	Integer HTTP_RES_CODE_200 = 200;
	// qq响应请求成功code
	Integer HTTP_RES_CODE_201 = 201;
	// 系统错误
	Integer HTTP_RES_CODE_500 = 500;
	// cookie token time
	Integer COOKIE_TOKEN_TIME = 60*60*24*30;
	// pay token time
	Long PAY_TOKEN_TIME = (long) 15*60;
;}