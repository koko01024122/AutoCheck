package nospy.com.autocheck.Impl;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public interface CheckViewGroupImpl {
    boolean isOuting = false;
    String finalHint = "";
    boolean autoHint = false;
    Object hintView = null;

    boolean check();

    String getFianlHint();

    void setIsOutRing(boolean isOutRing);

    public Object getHintView();

    public void setHintView(Object hintView);


}
