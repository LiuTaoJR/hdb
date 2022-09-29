package com.xq.hdb.utils;


import com.xq.hdb.config.HdbConstantConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 赋值处理
 */
public class AssignUtils {


    private static String publicKey = "hdbsaltabcdefg";

    @Autowired
    private HdbConstantConfig hdbConstantConfig;



    public static String idAssign(Object value){
        String result = null;
        if(value==null || value.equals("")){
            result = UUID.randomUUID().toString();
        }else{
            result = String.valueOf(value);
        }
        return result;
    }


    public static String StringAssign(Object value){
        String result = "未知";
        try{
            if(value==null || value.equals("")){
                result = "未知";
            }else{
                result = String.valueOf(value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    /**
     * 加密操作
     * @param value
     * @return
     */
    public static String encrypt(Object value){
        String result = "";
        try{
            result = (value==null || value.equals(""))? null : DESUtils.encrypt(String.valueOf(value), publicKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    /**
     * 解密成String
     * @param value
     * @return
     */
    public static String decryptionToStr(Object value){
        String result = "";
        try{
            if(!(value==null || value.equals("") || value.equals("未知") || value.equals("null"))){
                result = DESUtils.decrypt(String.valueOf(value), publicKey);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 解密成Long
     * @param value
     * @return
     */
    public static Long decryptionToLong(String value){
        Long result = null;
        try{
            if(!(value==null || value.equals("") || value.equals("未知") || value.equals("null"))){
                String x = DESUtils.decrypt(value, publicKey);
                Boolean b = x.contains(".");
                if(b){
                    String str1=x.substring(0, x.indexOf("."));
                    result = Long.valueOf(str1);
                }else{
                    result = Long.valueOf(x);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }




    /**
     * 解密成Date
     * @param value
     * @return
     */
    public static Date decryptionToDate(String value){

        try{
            if(!(value==null || value.equals("") || value.equals("未知") || value.equals("null"))){
                String result = DESUtils.decrypt(value, publicKey);
                //处理带T时间
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = df.parse(result);

                return date;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 参数加密操作
     * @param value
     * @return
     */
    public static String paramEncrypt(Object value){
        String result = null;
        try{
            result = (value==null || value.equals(""))? null : DESUtils.encrypt(String.valueOf(value), publicKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public static String formatValue(String str){
        String result = str;

        //纯数字
        if(containNumber(result)){
            //格式化
            switch (result.length()){
                case 1:
                    result = "10000"+ result;
                    break;
                case 2:
                    result = "1000"+ result;
                    break;
                case 3:
                    result = "100"+ result;
                    break;
                case 4:
                    result = "10"+ result;
                    break;
                case 5:
                    result = "1"+ result;
                    break;
            }
            return result;
        }else if(str.contains("@")){
            //不为纯数字且有"@"
            result = str.replace("@", "");
            //格式化
            switch (result.length()){
                case 1:
                    result = "20000"+ result;
                    break;
                case 2:
                    result = "2000"+ result;
                    break;
                case 3:
                    result = "200"+ result;
                    break;
                case 4:
                    result = "20"+ result;
                    break;
                case 5:
                    result = "2"+ result;
                    break;
            }
            return result;
        }

        return result;
    }


    public static Boolean containNumber(String str){
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }




    /**
     * 生成不带"-"的uuid
     * @return
     */
    public static String getUUid(){
        String s = UUID.randomUUID().toString();
        return s.replace("-", "");
    }

    /**
     * 转为utf-8的字符串
     * @param string
     * @return
     */
    public  static String toUtf8String(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c <= 255) {
                stringBuilder.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes(StandardCharsets.UTF_8);
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int value : b) {
                    int k = value;
                    if (k < 0) k += 256;
                    stringBuilder.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return stringBuilder.toString();
    }
}
