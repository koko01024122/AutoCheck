package nospy.com.autocheck.views;
import android.content.Context;
import android.util.AttributeSet;

import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nospy.com.autocheck.Impl.CheckViewGroupImpl;
import nospy.com.autocheck.Impl.CheckViewRuleImpl;
import nospy.com.autocheck.Impl.HintViewImpl;

/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class ACRelativeLayout extends RelativeLayout implements CheckViewGroupImpl {
    String TAG = "Relative";
    Object hintView;
    private String finalHint;
    private boolean autoHint;
    private boolean hasHintView = false;//是否拥有hintview
    private boolean couldTakeiHint = false;//hintView是否允许被夺取
    private boolean outRing = false;
    public ACRelativeLayout(Context context) {
        super(context);
    }

    public ACRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ACRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public String getFinalHint() {
        return finalHint;
    }

    public void takeHint() {
        if (getChildCount() != 0) {
            for (int i = 0; i < getChildCount(); i++) {
                Object view = getChildAt(i);
                if (HintViewImpl.class.isAssignableFrom(view.getClass())) {
                    //对HintView进行判断显示，如果此时本控件已经持有一个hintView，则不进行获取
                    if (hintView == null && !outRing) {
                        try {
                            Class hint = Class.forName(view.getClass().getName());

                            Method ispub = hint.getDeclaredMethod("isPublic");
                            if (((boolean) ispub.invoke(view))) {
                                hintView = view;
                                hasHintView = true;
                                couldTakeiHint = false;
                            } else {
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
                    }
                }
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        takeHint();

    }

    public boolean check() {

        if (getChildCount() != 0) {
            for (int i = 0; i < getChildCount(); i++) {
                Object view = getChildAt(i);
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
                                    showError();
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
                } else if (CheckViewGroupImpl.class.isAssignableFrom(view.getClass())) {
                    //对ViewGroup进行判断显示，如果此时本控件已经持有一个hintView，则不进行获取
                    //否则判断是否允许夺取子viewgrou的HintView

                    try {
                        Class viewgroup = Class.forName(view.getClass().getName());
                        Method check = viewgroup.getDeclaredMethod("check");
                        Method getHintView = viewgroup.getDeclaredMethod("getHintView");
                        Method getFinalHint = viewgroup.getDeclaredMethod("getFinalHint");
                        Method isCouldTakeiHint = viewgroup.getDeclaredMethod("isCouldTakeiHint");
                        Method getHasHintView = viewgroup.getDeclaredMethod("isHasHintView");
                        Method isOutRing = viewgroup.getDeclaredMethod("isOutRing");
                        if (getHintView.invoke(view) != null) {
                            if ((boolean) isCouldTakeiHint.invoke(view) == true) {
                                if (hintView == null) {
                                    //如果允许夺取且当前viewgroup为空的话，则获取
                                    hintView = getHintView.invoke(view);

                                } else {

                                }
                            }


                        } else {

                        }

                        if ((boolean) check.invoke(view) == true) {

                            continue;
                        } else {
                            if ((boolean) isOutRing.invoke(view)) {

                            } else {
                                finalHint = getFinalHint.invoke(view).toString();
                                showError();
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


                }
            }
            //如果循环结束还没有包含有错的VieW,则返回true
            return true;
        } else {
            //如果不包含任何子view，则直接返回
            return true;
        }


    }

    private void showError() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (hintView != null) {
            Class hint = Class.forName(hintView.getClass().getName());
            Method showError = hint.getDeclaredMethod("showError", String.class);
            showError.invoke(hintView, finalHint);
        } else {

        }


    }

    @Override
    public String getFianlHint() {
        return null;
    }

    @Override
    public void setIsOutRing(boolean isOutRing) {

    }

    @Override
    public Object getHintView() {
        return hintView;
    }

    @Override
    public void setHintView(Object hintView) {
        this.hintView = hintView;
    }

    public boolean isOutRing() {
        return outRing;
    }

    public ACRelativeLayout setOutRing(boolean outRing) {
        this.outRing = outRing;
        return this;
    }

}
