package cn.com.hwxt.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

public class MD5 {
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messageDigest = null;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5.class.getName() + "初始化失败，MessageDigest不支持MD5!");
            nsaex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //System.out.println(getMD5String("780adfb183724400"));
        String pure = "7830afdf5b1183072644b0005a92559483374732f072d341";
        System.out.println("摘要随机盐："+getRandomSalt(pure));
        /*
        String pure = "f4e58ff27436e9bff3d3c04e8e1d8ef84639bf6279c9de0e";

        String fileMd5 = "f458f243e9ffd3048ed8f863bf27c9e0";

        System.out.println("摘要："+pure);
        System.out.println("摘要长度："+pure.length());
        System.out.println("摘要随机盐："+getRandomSalt(pure));
        System.out.println("随机盐长度："+getRandomSalt(pure).length());
        System.out.println("是否和随机盐匹配："+"ef76b3ce1e4969de".equals(getRandomSalt(pure)));
        System.out.println("文件MD5值:"+fileMd5);
        System.out.println("文件MD5长度:"+fileMd5.length());
        String a = getSaltMD5(fileMd5,getRandomSalt(pure));
        System.out.println("重新添加随机盐后："+a);
        System.out.println("是否和原来的摘要相同："+a.equals(pure));
        */
    }

    /**
     * 返回值为null 暂时屏蔽了这个功能
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, numRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(null != fis){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferToHex(messageDigest.digest());
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        messageDigest.update(bytes);
        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    //转换成16进制
    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }
    /**
     * 按照规则，提取md5中的随机加密盐
     * @param strMd5
     * @return
     */
    public static String getRandomSalt(String strMd5)  {
        if(StringUtils.isNotEmpty(strMd5) && strMd5.length()==48)  {
            char[] strMd5Arr = strMd5.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for(int i =0 ;i <= strMd5Arr.length;i+=3){
                if(i>1) {
                    stringBuilder.append(strMd5Arr[i-1]);
                }
            }
            return stringBuilder.toString();
        }
        return "";

    }

    /**
     * 根据加盐规则混合MD5和加密盐
     * @param strMd5
     * @param saltStr
     * @return
     */
    public static String getSaltMD5(String strMd5,String saltStr)  {
        char[] strMd5Arr = strMd5.toCharArray();
        char[] saltStrArr = saltStr.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strMd5Arr.length; i++) {
            stringBuilder.append(strMd5Arr[i]);
            if(i>0 && (i+1)%2==0) {
                stringBuilder.append(saltStrArr[i/2]);
            }
        }
        return stringBuilder.toString();
    }
}

