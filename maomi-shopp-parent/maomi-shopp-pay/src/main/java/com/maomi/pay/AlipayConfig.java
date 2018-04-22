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
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRtNXUGs2OFbkgaUVVrErv7Wgq4v8jV9dsXQnXKnyJIoFRohkGhT8wDaBjvHolPvLGqxEYc8wfystu7tszJCe+dXJ+e25Pe6pIDepUaULEnlUspfKevLV59A7ZPaBEGU4OO5GlD8F1AgKkboKHLeViGVeHXGrrYvshkGVIlxLFMehkmbcyjLJdtkwdnTj5UmmK83YIp+N7FrSLxTfASP/S8lr0yYFK8lcLBBg2JKVxFSfSiKAXcmGk8UcGzT61trw0dzMUg+j1xdHAlcHY9bOax+oaZvHx+0A1z9I6fw+Mry3Uhy2wF2J4wxICkqbudTALh8WQ9BsRYDjYxYBUCCf3AgMBAAECggEBAI6uyCoxqRM3YJdFnzj7RFLZaAV/TpucnH8oSMekelmMKZj4JIuHFk6HOYYRfodUskcsI8yPMNf9LePPEWZuRGE0/86uLmK1VhXDUrIgddq2te/H/ePGJexru9Kuj2/AW/iCCujZmfdr5YyCD14y6op7CISsUqN4xTWkAhrond/trMZNtNxgE34JeBzq74fVioZfIgZ8mg3fXRDmcMi3MacNicG1Le/5hESaLp/boSN4Hc+rNh078QygV8pasgJ31IG9GcPRkveIL5jqcVGETpLlGOXv9ESDbM+5QE5+qA5POWnxTCWIx6FZtwqm89gHev7titQWMEIG7tDA7z9cpkkCgYEA/BBAqytdV8HoB544HMuEqpblU4AZFKFwuyw/BvRGsYxKeJXtdpLvKeYAFx7jfwT9olUWzYApIMlow3A7khOYnS5DRDxlXWNoMTMzBcng+rdHZq8Aa3EsYR5v+yrSiOdwbD/hInZEN0isIb9sFwjPs6Qso613sRi66fqCkjODd/sCgYEAk/teNUL+7atg+ituVK35EpkuDpkcDBdGtryDUuTcQOdsI2TLneESskq4uDVPc1dTBQ/jNLa8JX61WjModLUQ3rQu6rZDetuH4LblDJy/JH2DbRbSr3nZO8Y+FNHG+oVqgh6XnpAlfQZmRwx/cDVxxuBKl1JCcunm2PKVDhfrIzUCgYBbCK+iYi37BM/8+FqBo8sNJSQ7rPbh6ZBiOQ+mPlDNzMIUnOk/Or/fnO6JayF0fWPw3dQ3ccLo0XThqMYK8sLaeZvXqAxf1wgJRNl3jLqRMeI6ppd7Nkt1491Dk+qngRasMcHohVgLs1Zfddfe1dLVqURp9LtWnXzcBhpkHPCtQwKBgBPAKfu73GCGTiQ1pb0hlyu3TCFgqBCpDqQXFwP6TbdP6+vxaQfCYYdwV6UmYFtJnWgAjdecSBJNDhFi4JRrEjxXGifzeaAtKr6ASqPbS9un8OGfeTE4dxj2RZTrxr3rDqMdSCk+nfJI51+2OFAWof/kMbBupzXFKC1uexxtbtG9AoGADHigIaphXNq8Z3PfCRxogrE2vQQiPpeyepwhaiSQrNp3cOn3QjOJD83LDRYHllna0wsP4yp+PUzNJjShneGsBNxGJNQngSfCW8DYMFyor8bxGJsBi0FUspSjn9S44idNSr+ufjUFmb4OZ4mzmHFRmIlqA7axIP0kSLjoeVnn9aI=";
    
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkbTV1BrNjhW5IGlFVaxK7+1oKuL/I1fXbF0J1yp8iSKBUaIZBoU/MA2gY7x6JT7yxqsRGHPMH8rLbu7bMyQnvnVyfntuT3uqSA3qVGlCxJ5VLKXynry1efQO2T2gRBlODjuRpQ/BdQICpG6Chy3lYhlXh1xq62L7IZBlSJcSxTHoZJm3MoyyXbZMHZ04+VJpivN2CKfjexa0i8U3wEj/0vJa9MmBSvJXCwQYNiSlcRUn0oigF3JhpPFHBs0+tba8NHczFIPo9cXRwJXB2PWzmsfqGmbx8ftANc/SOn8PjK8t1IctsBdieMMSApKm7nUwC4fFkPQbEWA42MWAVAgn9wIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://maomicoder.s3.natapp.cc/alibaba/callBack/notifyUrl";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://maomicoder.s3.natapp.cc/alibaba/callBack/notifyUrl";

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

