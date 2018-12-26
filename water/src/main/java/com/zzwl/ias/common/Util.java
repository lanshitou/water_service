package com.zzwl.ias.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by HuXin on 2017/12/2.
 */
public class Util {
    static public short byteToShort(byte[] data, int offset) {
        return (short) ((((data[offset] << 8) & 0xFF00) | (data[offset + 1] & 0xFF)) & 0xFFFF);
    }

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    static char hexdigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMD5(MultipartFile file) {
        InputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = file.getInputStream();
            byte[] buffer = new byte[2048];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return byteToHexString(b);
            // 16位加密
            // return buf.toString().substring(8, 24);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeFileInputStream(fis, logger);
        }
    }

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     *
     * @param tmp 要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexdigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexdigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }

    public static void closeFileInputStream(InputStream fis, Logger logger) {
        if (fis != null) {
            try {
                fis.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
