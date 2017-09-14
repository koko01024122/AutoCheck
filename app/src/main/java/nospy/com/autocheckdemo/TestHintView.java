

package nospy.com.autocheckdemo;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import nospy.com.autocheck.Impl.HintViewImpl;


/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class TestHintView extends View implements HintViewImpl {
    public String errorInfo="";
    private Paint backgroundPaint;
    boolean pub=true;
    private Paint txtPaint;
    private Paint imagPaint;
    private Paint errorTextPaint;
    private Paint errorImgPaint;
    private Paint errorBackGround;
    private float width;
    private float height;
    private int hintColor= Color.parseColor("#71aaf7");
    private int backGround= Color.parseColor("#DEECFF");
    private int errorTextColor= Color.parseColor("#FFDEDE");
    private int errorHintColor= Color.parseColor("#F77171");
    private float myheight=-10;
    private String errorText="";
    public TestHintView(Context context) {
        super(context);
        initPaints();
    }

    public TestHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public TestHintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width=getWidth();
        height=getHeight();
        txtPaint.setTextSize(height/100*50);
        errorTextPaint.setTextSize(height/100*50);
        canvas.drawRect(0,height,width,0,backgroundPaint);
        canvas.drawText("测试",(width/9f),(height/100f*65f),txtPaint);
        canvas.drawRect(0,myheight,width,0,errorBackGround);
        canvas.drawText(errorInfo,(width/9f),(myheight/100f*65f),errorTextPaint);
        invalidate();
    }

    @Override
    public void showError(String errorInfo) {
        this.errorInfo=errorInfo;
        ValueAnimator animator= ValueAnimator.ofObject(new HeightEvaluator(),-height,height).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float a= (float) animation.getAnimatedValue();
                myheight=a;
                invalidate();
            }
        });
        animator.start();

    }

    @Override
    public void showHint(String hintInfo) {

    }

    @Override
    public void normalState(int isVisable) {

    }

    @Override
    public boolean isPublic() {
        return pub;
    }

    @Override
    public boolean setPub(boolean isPublic) {
        pub=isPublic;
        return false;
    }


    private void initPaints(){
        backgroundPaint=new Paint();
        backgroundPaint.setColor(backGround);
        txtPaint=new Paint();
        txtPaint.setColor(hintColor);
        errorImgPaint=new Paint();
        errorTextPaint=new Paint();
        errorTextPaint.setColor(errorHintColor);
        errorBackGround=new Paint();
        errorBackGround.setColor(errorTextColor);
    }

}
