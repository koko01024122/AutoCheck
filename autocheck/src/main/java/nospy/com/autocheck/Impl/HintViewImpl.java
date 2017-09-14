package nospy.com.autocheck.Impl;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public interface HintViewImpl {

    public void showError(String errorInfo);
    void  showHint(String hintInfo);
    void normalState(int isVisable);
    boolean isPublic();
    boolean setPub(boolean isPublic);
    boolean pub=true;

}
