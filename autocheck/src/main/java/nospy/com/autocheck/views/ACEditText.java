package nospy.com.autocheck.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import android.widget.EditText;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import nospy.com.autocheck.Impl.CheckViewRuleImpl;
import nospy.com.autocheck.Impl.HintViewImpl;
import nospy.com.autocheck.Modes;

import nospy.com.autocheck.R;
import nospy.com.autocheck.Tool;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class ACEditText extends EditText implements TextWatcher, CheckViewRuleImpl<ACEditText> {
    //依赖列表的依赖模式，默认为WITH，即多项依赖满足才进行判断，OR为单项满足即可
    private int relyMode = Modes.RELY_ON_MODE_WITH;
    private String finalHint;
    private ArrayList<Object> relyOnList;
    private String tag;
    private String viewId;
    private String reCord;
    private String rule;
    private String changeHis;
    private boolean nullable;
    private String name;
    private String nullHint = name + "内容不能为空";
    private String errorHint = name + "内容格式错误";
    private Object hintView;
    private String TAG = "ACEditText";

    public ACEditText(Context context) {
        super(context);
        relyOnList = new ArrayList<>();
    }


    public ACEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        relyOnList = new ArrayList<>();
    }


    public ACEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        relyOnList = new ArrayList<>();
    }

    public Object getHintView() {
        return this.hintView;
    }

    @Override
    public void setHintView(Object object) {

        this.hintView = object;
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ACEditText);
        this.name = typedArray.getString(R.styleable.ACEditText_ACEditName);
        this.relyMode = typedArray.getInteger(R.styleable.ACEditText_ACEditRelyMode, Modes.RELY_ON_MODE_WITH);
        this.nullable = typedArray.getBoolean(R.styleable.ACEditText_ACEditNullable, false);
        this.nullHint = typedArray.getString(R.styleable.ACEditText_ACEditNullHint);
        this.errorHint = typedArray.getString(R.styleable.ACEditText_ACEditErrorHint);

        setRule(typedArray.getString(R.styleable.ACEditText_ACEditRule));
        this.tag = typedArray.getString(R.styleable.ACEditText_ACEditTag);
        this.viewId = typedArray.getString(R.styleable.ACEditText_ACEditViewId);


    }

    public String getFinalHint() {
        return finalHint;
    }

    public int getRelyMode() {
        return relyMode;
    }

    public ACEditText setRelyMode(int relyMode) {
        this.relyMode = relyMode;
        return this;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public ACEditText setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getViewId() {


        return viewId;

    }

    public ACEditText setViewId(String viewId) {
        this.viewId = viewId;
        return this;
    }

    @Override
    public ACEditText setRecord(String reCord) {
        this.reCord = reCord;
        return this;
    }

    public String getReCord() {
        return reCord;
    }

    public String getRule() {
        return rule;
    }

    public ACEditText setRule(String rule) {
        this.rule = rule;
        if (rule.equals(R.string.address)) {
            setInputType(InputType.TYPE_CLASS_TEXT);
            setMaxLines(1);
        } else if (rule.equals(R.string.id_card) || rule.equals(R.string.phone_num) || rule.equals(R.string.mobel_phone) || rule.equals(R.string.just_number)) {
            setMaxLines(1);
            setInputType(InputType.TYPE_CLASS_NUMBER);
        }


        return this;
    }

    public String getChangeHis() {
        return changeHis;
    }

    @Override
    public boolean checkSelf() {
        return false;
    }

    public boolean isNullable() {
        return nullable;
    }

    public ACEditText setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    @Override
    public ACEditText addRely(Object rely) {
        relyOnList.add(rely);
        return this;
    }

    @Override
    public ACEditText addNotBe(String notBe) {
        notBeList.add(notBe);
        return this;
    }

    public String getName() {
        return name;
    }

    public ACEditText setName(String name) {
        this.name = name;
        nullHint = name + "内容不能为空";
        errorHint = name + "内容格式错误";
        return this;
    }

    @Override
    public ACEditText setrelyMode(int relyMode) {
        this.relyMode = relyMode;
        return this;
    }

    public String getNullHint() {
        return nullHint;
    }

    public ACEditText setNullHint(String nullHint) {
        this.nullHint = nullHint;
        return this;
    }

    public String getErrorHint() {
        return errorHint;
    }

    public ACEditText setErrorHint(String errorHint) {
        this.errorHint = errorHint;
        return this;
    }

    public ACEditText addRelyOn(Object relyOn) {

        this.relyOnList.add(relyOn);
        return this;
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (changeHis == null) {
            changeHis = getText().toString() + "," + new Date().getTime() + ";";
        } else {
            changeHis = changeHis + getText().toString() + "," + new Date().getTime() + ";";
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
