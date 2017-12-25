package util;

import java.util.Date;
import java.util.List;

/**
 * Created by jerry on 11/3/17.
 */
public class CommonUtil {
    public static Boolean isBlank(Long l) {
        return null == l || l == 0L;
    }

    public static Boolean isBlank(List l) {
        return null == l || l.isEmpty();
    }

    public static Boolean isBlank(String s) {
        return null == s || s.equals("");
    }

    public static Date timestamp2Date(Long timestamp){
        if (timestamp < 2000000000L){
            timestamp *= 1000L;
        }
        return new Date(timestamp);
    }




    public static void main(String[] args) {
        System.out.println("hello");
    }

}
