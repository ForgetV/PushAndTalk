package android.alex.com.pushandtalk.activity;

import android.alex.com.pushandtalk.AVImClientManager;
import android.alex.com.pushandtalk.Constants;
import android.alex.com.pushandtalk.R;
import android.alex.com.pushandtalk.event.LeftChatItemClickEvent;
import android.alex.com.pushandtalk.fragment.ChatFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wli on 15/8/13.
 * 广场页面，即群组聊天页面
 *
 * 1、根据 clientId 获得 AVIMClient 实例
 * 2、根据 conversationId 获得 AVIMConversation 实例
 * 3、必须要加入 conversation 后才能拉取消息
 */
public class AVSquareActivity extends AVBaseActivity {

  private AVIMConversation squareConversation;
  private ChatFragment chatFragment;
  private Toolbar toolbar;

  /**
   * 上一次点击 back 键的时间
   * 用于双击退出的判断
   */
  private static long lastBackTime = 0;

  /**
   * 当双击 back 键在此间隔内是直接触发 onBackPressed
   */
  private final int BACK_INTERVAL = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_square);

    String conversationId = getIntent().getStringExtra(Constants.CONVERSATION_ID);
    String title = getIntent().getStringExtra(Constants.ACTIVITY_TITLE);

    chatFragment = (ChatFragment)getFragmentManager().findFragmentById(R.id.fragment_chat);
    toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    setTitle(title);

    getSquare(conversationId);
    queryInSquare(conversationId);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_square, menu);
    return true;
  }

  @Override
  public void onBackPressed() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastBackTime < BACK_INTERVAL) {
      super.onBackPressed();
    } else {
      showToast("双击 back 退出");
    }
    lastBackTime = currentTime;
  }

  /**
   * 根据 conversationId 查取本地缓存中的 conversation，如若没有缓存，则返回一个新建的 conversaiton
   */
  private void getSquare(String conversationId) {
    if (TextUtils.isEmpty(conversationId)) {
      throw new IllegalArgumentException("conversationId can not be null");
    }

    AVIMClient client = AVImClientManager.getInstance().getClient();
    squareConversation = client.getConversation(conversationId);
  }

  /**
   * 加入 conversation
   */
  private void joinSquare() {
    squareConversation.join(new AVIMConversationCallback() {
      @Override
      public void done(AVIMException e) {
        if (filterException(e)) {
          chatFragment.setConversation(squareConversation);
        }
      }
    });
  }

  /**
   * 先查询自己是否已经在该 conversation，如果存在则直接给 chatFragment 赋值，否则先加入，再赋值
   */
  private void queryInSquare(String conversationId) {
    final AVIMClient client = AVImClientManager.getInstance().getClient();
    AVIMConversationQuery conversationQuery = client.getQuery();
    conversationQuery.whereEqualTo("objectId", conversationId);
    conversationQuery.containsMembers(Arrays.asList(AVImClientManager.getInstance().getClientId()));
    conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
      @Override
      public void done(List<AVIMConversation> list, AVIMException e) {
        if (filterException(e)) {
          if (null != list && list.size() > 0) {
            chatFragment.setConversation(list.get(0));
          } else {
            joinSquare();
          }
        }
      }
    });
  }

  /**
   * 处理聊天 item 点击事件，点击后跳转到相应1对1的对话
   */
  @Subscribe public void onEvent(LeftChatItemClickEvent event) {
    Intent intent = new Intent(this, AVSingleChatActivity.class);
    intent.putExtra(Constants.MEMBER_ID, event.userId);
    startActivity(intent);
  }
}
