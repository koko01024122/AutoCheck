package nospy.com.autocheck;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class Tool {
    public static boolean match(String regex, String str) {
        Log.d("TAG", "match: 正则为"+regex);
        Log.d("TAG", "match: 待校验内容为："+str);
        try{
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
           return matcher.matches();
        }catch (Exception e){
            Log.d("TAG", "match: 正则内"+e);
        }

        return false;
    }

}
