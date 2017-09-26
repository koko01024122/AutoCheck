package nospy.com.autocheckdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nospy.com.autocheck.views.ACLinearLayout;

public class MainActivity extends AppCompatActivity {

    private ACLinearLayout mCheckRoot;
    private Button mSubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//
        mCheckRoot=(ACLinearLayout) findViewById(R.id.check_root);
        //需要校验的表单的根节点
        mSubButton=(Button)findViewById(R.id.sub);
        //用于触发校验的按钮
        mCheckRoot.setOutRing(true);
        //设置根节点为最外层
        mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckRoot.check()){
                  //校验通过
                }else {
                    //校验未通过
                    String hint=mCheckRoot.getFinalHint();//获取最终的提示信息
                }

            }
        });
    }


}
