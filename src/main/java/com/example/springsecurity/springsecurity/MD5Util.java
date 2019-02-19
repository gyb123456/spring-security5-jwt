package com.example.springsecurity.springsecurity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	
	
	 /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
  
    // 测试主函数  
    public static void main(String args[]) {  
        String s = new String("C5CE20157F9F700416000");  
        System.out.println("原始：" + s);  
        System.out.println("MD5后：" + string2MD5(s)); 
        String md5 = string2MD5(s);
        System.out.println("加密的：" + convertMD5(md5));  
        System.out.println("解密的：" + convertMD5(convertMD5(md5)));  
  
    }  
	
	

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	
	public static byte[] makeMD5(byte[] data) {
        if (data == null || data.length == 0)
            return null;

        byte[] res = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            res = md5.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 

        return res;
    }

    public static String formatMD5toHexString(byte[] md5, boolean isUpperCase) {
        if (md5 == null || md5.length == 0)
            return "";

        StringBuffer sb = new StringBuffer();
        for (byte b : md5) {
            sb.append(String.format("%02x", b));
        }

        return isUpperCase ? sb.toString().toUpperCase() : sb.toString();
    }
	
//	public static void main(String[] args) {
//		String sign = MD5Encode("2&9165DB2EC2DB27145A275B1AC63AA684&YAOH&F26D24135138C70C2C74026422D6CC3D&2&1234&&13666666667&1490679442&20021&1.0&EYJEZXZPY2VJZCI6IJG2NTK4MJAYNZQ4NDGZNCISILNFUKLBTCI6IJNJMZGYMJMWIIWIQK9BUKQIOIJNU004OTC0IIWIQLJBTKQIOIJYAWFVBWKILCJCT09UTE9BREVSIJOIDW5RBM93BIISIKHBUKRXQVJFIJOICWNVBSISIKNQVV9BQKKIOIJHCM1LYWJPLXY3YSISIKRFVKLDRSI6INZPCMDVIIWIRELTUEXBWSI6IK1NQJI5TSISIKHPU1QIOIJJMY1TAXVPLW90YS1IZDIXLMJQIIWISUQIOIJNTUIYOU0ILCJNQU5VRKFDVFVSRVIIOIJYAWFVBWKILCJNT0RFTCI6IK1JIE5PVEUGTFRFIIWIUFJPRFVDVCI6INZPCMDVIIWIVEFHUYI6INJLBGVHC2UTA2V5CYISILRZUEUIOIJ1C2VYIIWIVVNFUII6IMJ1AWXKZXIIFQ","utf-8");
//	    System.out.println(sign.substring(0,20));
//	}
	
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
