package com.fh.shop.api.config;

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
	public static String app_id = "2021000117658360";

	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCyAgQE2zaB15kf1US2CnDEWy4/Hdj683s94rKE7TnX4wzjFmldeswNfC2LAFi//3JZscShcv/RvSMDiCkXofdMdpUMVunvYvsUQsqK+uG676z0thSdbu8p2oY89VZD7EVIJFh+vjzU0QGCYfjQRimAlPFwoTWwGMZIkQo0F6sWeRJpgnokGcs+mO7DIcbiVO6hevfImaGhk8nLQLBqfMJB7yZ8rbvafJkfDK+8NPwlKNEX1sWzKGURgpdD4vDrZaFJOOzqI9zXh1uTVPlLeqKM9gO/LVkqja/RV2wIv4V1dJTRiGQ7qfo3J4vi6/UNvEpROmBdSzxb54CnktKtRadpAgMBAAECggEAO1m2s1PPsa0LzfRSq4uBm8Z2/e5auRlTXwfl07Iw1g1K77A6Q0O9I0Xi/XSJIsLuAn/l+pqYNvsj4HqWkN2c0a679VhJOVq8Mj4uv/X0K1rcRN8ssm0ZatAEb/Cw6YauduY/Z0rQh3iEU0+NIQwzrD583buyZdtsyScqysGVdrEq0Nd1XLqnGZl3eBRqHsPGr8DR5bFb90HrbYZq0skJVjW7tfef2QBZomj0q+7FW8DFUViTLN1K+5q4XwWev5xnjXHm3s2NHnl2HbNBvk9p3UT/r940Paalso9qk4Byow+LHLZ0i5p8sevbJm7uBm2AdqPwOAgSVV5mKLSljX7E3QKBgQDjlO+qMjtglo39Jb4VJr2S+WesxjL6r8bwuLChU4xYNgRvc7B186xJv9xLulssXPurvEGz8WqLH0oOGajeaZH4RSKbTJt7xj+7k+SF/RtKRqaAfes4TzLiQexvlWRyFT2zxRg1oNtOjyNUtXQmbmCZhhOdoa3R2902tKXFyeGY7wKBgQDIPFwY93UAK1uGDuSBlEBIFcl89Yu8gYSlz6H32t/SyDkAEwmZo2Ax6N16RLULWaYZHQuLU1nHDMipqteOvOJHjgLL90snXgjJZjSB9LLUNQgSISuWBwI9ZV5v3+/LFX1M2oLLmDS++4IEm/U66xgUnBiuFxN4kMj6s9/R/eBVJwKBgAxZNHfjqd0fziyCM8IqC/dBmSKCzplTmFPPcaYPRnoy1CLIADcmNZ76zhU901yg3vb+jAIbg1BxLi99rPAEgmsaNTp/+Z/JrgObjrbGeQiatMYcDvtLMNOeCBo13PFjvalXdGzUgZHNqnkaL2c2bf9miwHul+PfLi2dt7D3t0n5AoGAPv3GF1CZrRD2vOioeB80TbkU5su0RvwTx7nXAUsIyJ2WWPCkP4IG/Ax+yD0fG0/7kcDyZcU15AmggsetAklngg0p0hKcwOizKPCPKibZrypzvIhU1uCjkr72A4nWPPQos+m15eifB0nF8D9D2WIl9C3bEcFOrcoWf8/gZIZScVcCgYEAjmoRZuZOc8GLZ+23T1W9cZgEmpw+YjytVHOQHisnLm2B4jHU8Q3l2eqbusONmMd7OrEsZqR7HMoDFhJFR8xSpNwQItzqlvIyOfnk+iTOrdppzQdHJdehYYuBRjUKmpoOKV1z17NqgETbyF7cU8gXMvA44Ewk0u0LBRCXnjNxhbU=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaj8OuYPg4g3GUAAgEEgwgH7qb7R/cLNM+Pyx7QYQSXNChq3Mae9bUdGKYPPcz7HMpyqzd9oeOG8Kz0Vm0XL+z9ujNbEiAkh1jUQukTHJNJOkXZU/Y92IVeyC7/qXt/Rj3zsgwgPtTLL2XL+8b3lwCq2VXQAZo1pnWJs9sOZ5nlM2T+7B1U1tl/nDDdnB87lzd2cZXFhS6QEaS9pkXjkyCBlIBwriX02ZVQtncl0/CHQVMGJJfn9wgCQXTJQru5fTZ2JWzit28IylpGlP7pDucqfNozrySV3cMGpxIJYAUvm51Ek54NEOW2kvGZ9ITsaL+KcQ41ohGLYTNAZ3JtZgQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://bdszyr.natappfree.cc/api/pay/notifyPay";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://bdszyr.natappfree.cc/api/pay/returnPay";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "\t\n" +"https://openapi.alipaydev.com/gateway.do";

//	// 支付宝网关
//	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
//    public static void logResult(String sWord) {
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
//            writer.write(sWord);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}

