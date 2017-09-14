package nospy.com.autocheck.Impl;


import java.lang.annotation.ElementType;
import java.util.ArrayList;

import nospy.com.autocheck.Modes;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public interface CheckViewRuleImpl<T> {


     int relyMode = Modes.RELY_ON_MODE_WITH;
     String finalHint="";
     ArrayList<Object> relyOnList=new ArrayList<>();
     String tag="";
     String viewId="";
     String reCord="";
     String rule="";
     String changeHis="";
     boolean nullable=false;
     ArrayList<String> notBeList=new ArrayList<>();
     String name="";
     String nullHint = name + "内容不能为空";
     String errorHint = name + "内容格式错误";

     T setrelyMode(int relyMode);
    T setName(String name);
    T setTag(String tag);
    T setViewId(String viewId);
    T setRecord(String reCord);
    T setRule(String rule);
    T setNullable(boolean nullable);
    T addRely(Object rely);
    T addNotBe(String notBe);
    T setNullHint(String nullHint);
    T setErrorHint(String errorHint);
    String getFinalHint();
    String getChangeHis();
    boolean checkSelf();
    boolean check();
    void setHintView(Object object);
}
