package android.alex.com.pushandtalk.activity;

import android.alex.com.pushandtalk.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by wli on 15/8/20.
 * Launch 页面
 */
public class AVLaunchActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launch);

    /**
     * 默认等待 1.5 秒后跳转到登陆页面
     */
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent=new Intent(AVLaunchActivity.this,AVLoginActivity.class);
        startActivity(intent);
        finish();
      }
    }, 1500);
  }

}
