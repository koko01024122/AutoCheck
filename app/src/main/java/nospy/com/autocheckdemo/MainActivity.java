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
        setContentView(R.layout.activity_main);
        mCheckRoot=(ACLinearLayout) findViewById(R.id.check_root);
        mSubButton=(Button)findViewById(R.id.sub);
        mCheckRoot.setOutRing(true);
        mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckRoot.check()){
                    mCheckRoot.showError("校验通过");
                }

            }
        });
    }


}
