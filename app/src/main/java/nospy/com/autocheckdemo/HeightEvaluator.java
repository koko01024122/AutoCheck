package nospy.com.autocheckdemo;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class HeightEvaluator implements TypeEvaluator {


    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
    float start= (float) startValue;

    float end= (float) endValue;

        return fraction*end;
    }
}
