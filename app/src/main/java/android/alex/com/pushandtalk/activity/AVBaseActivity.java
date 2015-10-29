package android.alex.com.pushandtalk.activity;

import android.alex.com.pushandtalk.BusProvider;
import android.alex.com.pushandtalk.event.EmptyEvent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Subscribe;


/**
 * Created by wli on 15/8/13.
 * 基类，封装了一些常用方法以及 ButterKnife
 */
public class AVBaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    BusProvider.getInstance().register(this);
    super.onCreate(savedInstanceState);
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    onViewCreated();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    onViewCreated();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    onViewCreated();
  }



  @Override
  protected void onPause() {
    super.onPause();
    BusProvider.getInstance().unregister(this);
  }

  protected void onViewCreated() {
  }

  protected boolean filterException(Exception e) {
    if (e != null) {
      e.printStackTrace();
      toast(e.getMessage());
      return false;
    } else {
      return true;
    }
  }

  protected void toast(String str) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
  }

  protected void showToast(String content) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
  }

  protected void showToast(int resId) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
  }


  protected void startActivity(Class<?> cls) {
    Intent intent = new Intent(this, cls);
    startActivity(intent);
  }

  protected void startActivity(Class<?> cls, String... objs) {
    Intent intent = new Intent(this, cls);
    for (int i = 0; i < objs.length; i++) {
      intent.putExtra(objs[i], objs[++i]);
    }
    startActivity(intent);
  }
  @Subscribe
  public void handle(EmptyEvent event) {}

}
