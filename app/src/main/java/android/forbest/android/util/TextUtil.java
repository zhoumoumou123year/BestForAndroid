package android.forbest.android.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 文字工具类
 * @Author : ZhouHui
 * @Date : 2017/12/26.
 */

public class TextUtil {

    /**
     * 判断字符串是否为空
     *
     * @param text 字符串
     * @return true空 false非空
     */
    public static boolean isEmpty(String text) {
        return !isNotEmpty(text);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null || "".equals(charSequence) || "null".equals(charSequence)) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    /**
     * 判断一个字符串是否为空
     *
     * @param text 字符串
     * @return true非空 false空
     */
    public static boolean isNotEmpty(String text) {
        if (text == null || text.trim().equals("null") || text.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 验证是否是有效的手机号
     *
     * @param mobile 手机号
     * @return true有效 false无效
     */
    public static boolean isMobile(String mobile) {
        Pattern pattern_phone = Pattern.compile("^[1]+\\d{10}"); //手机格式匹配
        Matcher matcher_phone = pattern_phone.matcher(mobile);
        return matcher_phone.matches();
    }

    /**
     * 验证是否是有效的邮箱
     *
     * @param email 邮箱
     * @return true有效 false无效
     */
    public static boolean isEmail(String email) {
        Pattern pattern_email = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\" +
                ".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");//邮箱格式匹配
        Matcher matcher = pattern_email.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证是否是数字
     *
     * @param number 数字
     * @return true是 false不是
     */
    public static boolean isNuM(String number) {
        Pattern pattern_num = Pattern.compile("^[+-]?[0-9]+$");
        Matcher matcher = pattern_num.matcher(number);
        return matcher.matches();
    }

    /**
     * 设置文本颜色并转化成Html标记语言
     *
     * @param color #ffffff
     * @param text  Hello
     * @return <font color="#ffffff">Hello</font>
     */
    public static String getNeedColorString(String color, String text) {
        String start = "<font color=\"";
        String center = "\">";
        String end = "</font>";
        return start + color + center + text + end;
    }

    /**
     * 设置手机号中间的部分改为*号
     */
    public static String changPnumberType(String pNumber) {
        if (!TextUtils.isEmpty(pNumber) && pNumber.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return null;
    }

}
