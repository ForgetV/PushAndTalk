package android.alex.com.pushandtalk.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by wli on 15/8/23.
 */
public class ImTypeMessageEvent {
  public AVIMTypedMessage message;
  public AVIMConversation conversation;
}
