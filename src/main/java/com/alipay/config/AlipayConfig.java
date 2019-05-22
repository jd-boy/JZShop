package com.alipay.config;

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
	public static String app_id = "2016093000628629";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQClYHQui9y6HeTT6NG+zW3C+jfZ2ALZXtlSEtcqBvUQfiUPz5UILMcGw96ge1WZT6ObTKYTCAGO6mQufWhEt2Lom2sWl6J4vQsZNYVnvMKedK1CpWIyPVy/szE6p/aJ6pQepaGwHByJaDCkcVW/WasomVoE0YYE057JwtEIgvPckiH6lGq8LXrWQZQ9+QQbE16Ni3GHXVi8Z0HdbcA8VdgVuCmxORTV5HIjuFeA+dnVeLsbw63XOo3ZcEYEZSsUwznRUNIPyDUTvJ8kGqNhjk++ic+fy96dTrokSb4K1xM63LPgqd0mS/qD0NvijPpyTap/t/gIgAyPrDem7bQ9owRHAgMBAAECggEBAJqkZh/8cyuqtoadyDx2VnQjjMRtJMTk16zSp1bKaLDCdJ+jScX6MSYXVH+WtQWGngOjmgxjySk97u42IKFZJulCI9JdevUSOWWaUaQes5J+omGWywsVrDwqMRebZoOy0k0SLu7CasDmp82tmTQ0gkOVmsK0tnCe/nXGcbdW1PhkPEZvnLBW7zjFJbrxDynH3jlJC/+FYSNuuWt/R5919GFCEjLtF6n7pNhRbsGdZGlOkmindJXZIr3muz9+M7CQF0ecNieHzPDC6J/op+iT7hnZ0yu0JThg70y9p38+FO6ZZ1CRAoERKWHKFx5etXBzb6lDZqwTSDbRsXBin0eVw0kCgYEA8MFXmeONCphc5jtTGV6fpx0kVbPL+jpC8k6v/ChN2/Lg/rnSgRQgNWpXSIDjay1nUzRLLLZYjFgBS3VjnBRJg3s8TrzJ/anuT48ZKXsFFY/FY4xtIvH6sQa5lUl8OBNs764GgFRxAzWMErC9EeK4Wr7kqBuxU7J0dmR/+ss+k2MCgYEAr9k4yuGTfqRtwuJXaFUvD9slLkNKDQSAIi6wUY2n/6X6VtH1PUCQdKJCtKpGrA1aFIxvOs5KI+5QXdcuKMiGaXqFbXCvjh66KGMG4eYNWX0Uh+x9fs6wVdZ/ljGW29HP7dzb+Cy8GywvDTVHscqEh9suFgqzZItIp8XwfTaAas0CgYEAyc3mthGtKdJpHHthvoQ4YwXkbQoW0pp/f0hB76WBDfraaeos7xGFpgCLRTT0a+RVFcNhuAro0kr6Wtv9MNk4fcOQ25cX4U7NeVbDiqeZrWcj17vfx0j6D+VJLj+4pfBygb1nqa4eRMhfr1MCSLm4OiiE1fzQeQ2+3XaEaB5xR20CgYBvpdFjLrnA7JEr7R1mBr+sp40BxINdpH/eNBqKjRAfRridKw0z6KxPAT6tCnd4gbmx1kBA6/0ytvqbuiV6i2abzcitZaBw8Z1sSwNxpejP6pSAgGxleMGInnm/WJjZ1GraMSIQfgaokfMFxdBRV62xvD2UjRK9QZnNu+Um+tWp+QKBgQCdzKz8gXkYKCeuTojnw60XEjhTT0+zidzjrPCTh0/1sn6uxImtlGfiXZYcadr2P1qN4/guFEyNbcltrNM/Ytsggwcq4u4EHvBqHL/dDwKiHdDehg454CmadUfnihxXZ1vXaLf9mhkV06y99g2fKxmMRSMtBK577mSNk8ByJIf9Hw==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmq4Et28jFwGq7jUyvMxPUgun9GMgir+KSWKy/YFYmK8AUJ8I751JETidBjET1iNyQWlBCVn6xjUknArT20bp/NOk+FtfTucfW9Far3MX+Llv1Ykdjtx43N7Nkj0kwInZzQ0K6TotnnAukX9An9GT7N42DpdXq/8++8uwm0+XeEsTxYMb0KsMf5fdRgyGieXbZm7kydf1AAZR9R44P/HXNAW0yJ8YbaBqaRA4ayaHev6bq0CEiVnoLYHB7VNL+nppHphbzSvD2yJEAlspAx1T3wFI1x1kmCpKxEsVo6cIjKk5L/Jyx/vZNwcqePSbaAFaGHS4gghpFYZAJi/s64iA1QIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
	public static String notify_url = "http://localhost:8080/alipay/alipayNotifyNotice";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//public static String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
	public static String return_url = "http://localhost:8080/alipay/alipayReturnNotice";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	//public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
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

