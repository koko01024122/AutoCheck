
package nospy.com.autocheck.views;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import android.view.View;
import android.widget.TextView;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import nospy.com.autocheck.Impl.CheckViewRuleImpl;
import nospy.com.autocheck.Impl.HintViewImpl;
import nospy.com.autocheck.Modes;
import nospy.com.autocheck.R;
import nospy.com.autocheck.Tool;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class ACTextView extends TextView implements View.OnClickListener, TextWatcher, CheckViewRuleImpl<ACTextView> {
    Object hintView;
    private int relyMode = Modes.RELY_ON_MODE_WITH;
    private String finalHint;
    private ArrayList<Object> relyOnList;
    private String tag;
    private String viewId;
    private String reCord;
    private String rule;
    private String changeHis;
    private boolean nullable;
    private ArrayList<String> notBeList;
    private String notBe;
    private String name;
    private String nullHint = name + "内容不能为空";
    private String errorHint = name + "内容格式错误";
    private String TAG = "TextView";

    public ACTextView(Context context) {
        super(context);
        relyOnList = new ArrayList<>();
        notBeList = new ArrayList<>();
    }


    public ACTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        relyOnList = new ArrayList<>();
        notBeList = new ArrayList<>();
    }

    public ACTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        relyOnList = new ArrayList<>();
        notBeList = new ArrayList<>();
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ACTextView);
        this.name = typedArray.getString(R.styleable.ACTextView_ACTextName);
        this.relyMode = typedArray.getInteger(R.styleable.ACTextView_ACTextRelyMode, Modes.RELY_ON_MODE_WITH);
        this.nullable = typedArray.getBoolean(R.styleable.ACTextView_ACTextNullable, false);
        this.nullHint = typedArray.getString(R.styleable.ACTextView_ACTextNullHint);
        this.errorHint = typedArray.getString(R.styleable.ACTextView_ACTextErrorHint);
         this.rule = typedArray.getString(R.styleable.ACTextView_ACTextRule);
        this.tag = typedArray.getString(R.styleable.ACTextView_ACTextTag);
        this.viewId = typedArray.getString(R.styleable.ACTextView_ACTextViewId);
    }

    public String getFinalHint() {
        return finalHint;
    }

    public int getRelyMode() {
        return relyMode;
    }

    public ACTextView setRelyMode(int relyMode) {
        this.relyMode = relyMode;
        return this;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public ACTextView setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getViewId() {
        return viewId;
    }

    public ACTextView setViewId(String viewId) {
        this.viewId = viewId;
        return this;
    }

    @Override
    public ACTextView setRecord(String reCord) {
        return null;
    }

    public String getReCord() {
        return reCord;
    }

    public ACTextView setReCord(String reCord) {
        this.reCord = reCord;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public ACTextView setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public String getChangeHis() {
        return changeHis;
    }

    //todo://将notbelist添加到check中

    @Override
    public boolean checkSelf() {
        if (nullable) {
            //如果可以为空
            if (rule == null || rule.equals("")) {
                //如果未设定规则那就说明可以随便写
                return true;
            } else {
                if (getText().toString().equals("")) {
                    //如果设定了规则不填也可以
                    return true;
                } else {
                    if (rule == null || rule.equals("")) {
                        return true;
                    } else {
                        //如果设定了规则还乱填那就GG了
                        if (Tool.match(rule, getText().toString())) {

                            return true;
                        } else {
                            finalHint = errorHint;
                            return false;
                        }
                    }
                }
            }
        } else {
            if (getText().toString().equals("")) {
                //如果为空，则直接返回不能为空的提示
                  finalHint = nullHint;
                return false;
            } else {
                for (String not : notBeList) {
                    if (getText().toString().equals(not)) {
                        finalHint = errorHint;
                        return false;
                    } else {
                        continue;
                    }
                }
                //否则判断进行返回
                if (Tool.match(rule, getText().toString())) {
                    return true;
                } else {
                    finalHint = errorHint;
                    return false;
                }
            }

        }

    }

    public boolean isNullable() {
        return nullable;
    }

    public ACTextView setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    @Override
    public ACTextView addRely(Object rely) {
        return null;
    }

    @Override
    public ACTextView addNotBe(String notBe) {
        return null;
    }

    public String getName() {
        return name;
    }

    public ACTextView setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ACTextView setrelyMode(int relyMode) {
        return null;
    }

    public String getNullHint() {
        return nullHint;
    }

    @Override
    public ACTextView setNullHint(String nullHint) {
        this.nullHint = nullHint;
        return this;
    }

    public String getErrorHint() {
        return errorHint;
    }

    public ACTextView setErrorHint(String errorHint) {
        this.errorHint = errorHint;
        return this;
    }

    public ACTextView addRelyOn(Object relyOn) {

        this.relyOnList.add(relyOn);
        return this;
    }

    public void build() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public boolean check() {

        if (relyOnList.size() != 0) {
            for (int i = 0; i < relyOnList.size(); i++) {

                Object view = relyOnList.get(i);
                if (CheckViewRuleImpl.class.isAssignableFrom(view.getClass())) {
                    //判断其子view是否实现自动校验接口
                    //如果实现，通过反射来调用其check方法
                    try {
                        Class sonView = Class.forName(view.getClass().getName());
                        Method check = sonView.getDeclaredMethod("check");
                        Method getHintView = sonView.getDeclaredMethod("getHintView");
                        Method getFinalHint = sonView.getDeclaredMethod("getFinalHint");
                        if (((boolean) check.invoke(view))) {
                            //该项子view自检通过
                             continue;
                        } else {
                            if (getHintView.invoke(view) == null) {
                                if (getFinalHint.invoke(view) != null) {
                                    //如果子view没有自身的Hint控件，且提示信息不为空，则获取其提示信息
                                    finalHint = getFinalHint.invoke(view).toString();

                                }
                            } else {
                                  if (HintViewImpl.class.isAssignableFrom(getHintView.invoke(view).getClass())) {
                                    Class errorView = Class.forName(getHintView.invoke(view).getClass().getName());
                                    Method showError = errorView.getDeclaredMethod("showError", String.class);
                                    showError.invoke(getHintView.invoke(view), getFinalHint.invoke(view).toString());
                                    return false;
                                }

                                 //如果子view拥有其自身的hint显示控件，则不对其进行操作
                            }

                            return false;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //viewcheck操作完毕
                }
            }

            //如果我代码写错了，循环里没有判断出来，那就循环结束了进行本项的判断，反正只要有一个错的就跳出了。
            return checkThis();
        } else {
            //如果依赖长度为0,则直接返回本控件的检查。
            return checkThis();
        }
    }

    private boolean checkThis() {
        if (notBeList.size() != 0) {
            for (String text : notBeList) {
                if (getText().toString().equals(text)) {
                    return false;
                }
            }
        }


        if (nullable) {
            //如果可以为空
            if (rule == null || rule.equals("")) {
                //如果未设定规则那就说明可以随便写
                return true;
            } else {
                if (getText().toString().equals("")) {
                    //如果设定了规则不填也可以
                    return true;
                } else {
                    //如果设定了规则还乱填那就GG了
                    if (Tool.match(rule, getText().toString())) {

                        return true;
                    } else {

                        if (hintView != null && HintViewImpl.class.isAssignableFrom(hintView.getClass())) {
                            //判断其对象是否是实现了自定义Hint控件的对象，且不为空
                            try {
                                Class error = Class.forName(hintView.getClass().getName());
                                Method showError = error.getDeclaredMethod("showError", String.class);
                                showError.invoke(hintView, errorHint);

                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        } else {
                            finalHint = errorHint;
                        }
                        return false;
                    }

                }
            }
        } else {

            if (getText().toString().equals("")) {
                //如果为空，则直接返回不能为空的提示
                finalHint = nullHint;
                return false;
            } else {
                if (rule == null || rule.equals("")) {
                    return true;
                } else {
                    //否则判断进行返回
                    if (Tool.match(rule, getText().toString())) {
                        return true;
                    } else {
                        finalHint = errorHint;
                        return false;
                    }
                }
            }

        }

    }


    public Object getHintView() {
        return hintView;
    }

    @Override
    public void setHintView(Object object) {
        hintView = object;
    }
}

