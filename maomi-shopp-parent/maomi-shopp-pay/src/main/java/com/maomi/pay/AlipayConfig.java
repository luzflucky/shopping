package com.maomi.pay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016091600521097";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDNKRr1OGgpfWIUBBtppW5rnZGb8HZ9VsTgYG/utHpjcGgs+JylB4ElrXm5RtyIUZG74coxkdLDsZtbMx2Ne4dUq1kiClKxJdJBOOINDvXzSReeQ0Fd1/NdM7PMXVTOu8fKOkrmaJhERsWIPSbAtWobShHZ/DaiK6YN1kHULDEm2d7LmZb5jVDVAqlFNtAscNLTxYuU3c69Y56TknNUtJxqxJEh673cVcqUlQ1ICtUmtzmh6ASp4m5ADnx1f6XJYPYZ6SXpcF95aZUoBDjIhexXQnlwYT31BHHQpgGQ9PYkmdGgGFmMETN2+RgFC00rR0+VYolKHOehcVmYYvWRgAg3AgMBAAECggEAY71JGtBKa2lGZj686PaBIRZgdLRJlpO5cO/RWvYozfEW5E4VMXHdimi01asqiadGN1YEy2Z+VZ8hzQujoWXdm/8VfSQRoYemmQjDdN/CGH1/WvSNcccLzFLXZVI2aa8G6Ty/rxZIeGEDpUE9dtB7fjrbROOF1/9qYi24oSK+3oiMCDDOP74LjUjZphmoQggVO4SRm4xzQ+iTAQrazPjb2DpiKgCLI13ZoqQhteh2UKLtywyytMgTCsx/5V4taqUJTJSg2kjNBSgWsOuVRGeVDtijlqTOt0pTPdUJj64iIYNTz20kPAiUxOK3n0W/+U6FruDTcCMBKOVx0Wg2uoI+WQKBgQDr5pILUjTczP/hwmeL6O3JeujTGH3QvZ/N3oZhDzwdO43KD0/7By7Y17v3sUYP4U70A8kErdZ6wrOTllX0wiJrDGZ02OM+Zv/Z787BbFh8Bx1G9NZA5ybl9Tlkl2ce1ZBw2phRJnWjkqYbMzcGrtARToYmKjg+wl0V5E3U+yIIfQKBgQDepAlVciLTcW7+cizIMPbuCBrgrOvfj1ugnQYgdl+z3EzMUwz60AB5IT72zINAkN6Ah5yD3ZaQCOB33rRLmN1/gnuyo+Pa//jXmUjJ5xgYJISqFn4pcjqiaOqpW98fplklWvlKTDoVkJarZN95WL8lzqu1kB8Dx3GTeaLrCXelwwKBgAhiHESMulYybtF+ECH3uCOV25pM34PAiWJOiyX93ZwIVsScgLIiEjcgi4yE30GHN0AStl0VNG8OZkVTbWCSWZjGSwnR/1ljXapIDGfxSr+1nQO9hbOuw0Ie0dhSTRbabhSkoA8aN8oFVyPWURlKsxg6WygWDgOk8pvpjh6IgjAdAoGANonMV4gMC+u/msjxO2DzPD/0BZGWfj8p3OBaPd2BeBfBIFQ/27jURNfmCqLyjABw4N9Xcd34IfRquDJvzAz4RWH7Vzlaea02kenUsNDRUCix7tBvu0tmHZ7BzRbaQ3CGaeYtJwwhNmMi5X1iWOFqgaJs4Z8aWdyjb9LeG+FH0+0CgYBRn2srZBfmHzVfCQzlXVLoPSfUFdqLZizAr4Jv6Nwx2bFZkfrmpbmAorsIhpisVlDjbbBBFCc6rxjmRP36mXjVvtk1e08vlLsIFELwPnbK6vmFuBHlHR8+iUZnBqDhw+Elq1NSTdHOUC3UtBhlSwJccKMjt290ibm9BZiNqKQj5w==";
    
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzSka9ThoKX1iFAQbaaVua52Rm/B2fVbE4GBv7rR6Y3BoLPicpQeBJa15uUbciFGRu+HKMZHSw7GbWzMdjXuHVKtZIgpSsSXSQTjiDQ7180kXnkNBXdfzXTOzzF1UzrvHyjpK5miYREbFiD0mwLVqG0oR2fw2oiumDdZB1CwxJtney5mW+Y1Q1QKpRTbQLHDS08WLlN3OvWOek5JzVLScasSRIeu93FXKlJUNSArVJrc5oegEqeJuQA58dX+lyWD2Gekl6XBfeWmVKAQ4yIXsV0J5cGE99QRx0KYBkPT2JJnRoBhZjBEzdvkYBQtNK0dPlWKJShznoXFZmGL1kYAINwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://maomicoder.s3.natapp.cc/alibaba/callback/notifyUrl";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://maomicoder.s3.natapp.cc/alibaba/callback/returnUrl";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

