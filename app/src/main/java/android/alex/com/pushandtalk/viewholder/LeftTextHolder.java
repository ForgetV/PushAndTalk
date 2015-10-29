package android.alex.com.pushandtalk.viewholder;

import android.alex.com.pushandtalk.BusProvider;
import android.alex.com.pushandtalk.R;
import android.alex.com.pushandtalk.event.LeftChatItemClickEvent;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import java.text.SimpleDateFormat;

/**
 * Created by wli on 15/8/13.
 * 聊天时居左的文本 holder
 */

public class LeftTextHolder extends AVCommonViewHolder {

  protected TextView timeView;
  protected TextView contentView;
  protected TextView nameView;

  public LeftTextHolder(Context context, ViewGroup root) {
    super(LayoutInflater.from(context).inflate(R.layout.chat_left_text_view,null));
    View roots=LayoutInflater.from(context).inflate(R.layout.chat_left_text_view, null);
    timeView=(TextView)roots.findViewById(R.id.chat_left_text_tv_time);
    contentView=(TextView)roots.findViewById(R.id.chat_left_text_tv_content);
    nameView=(TextView)roots.findViewById(R.id.chat_left_text_tv_name);
    contentView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onNameClick(v);
      }
    });
    nameView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onNameClick(v);
      }
    });
  }

  public void onNameClick(View view) {
    LeftChatItemClickEvent clickEvent = new LeftChatItemClickEvent();
    clickEvent.userId = nameView.getText().toString();
    BusProvider.getInstance().post(clickEvent);
  }

  @Override
  public void bindData(Object o) {
    AVIMMessage message = (AVIMMessage)o;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    String time = dateFormat.format(message.getTimestamp());

    String content =  getContext().getString(R.string.unspport_message_type);
    if (message instanceof AVIMTextMessage) {
      content = ((AVIMTextMessage)message).getText();
    }

    contentView.setText(content);
    timeView.setText(time);
    nameView.setText(message.getFrom());
  }

  public void showTimeView(boolean isShow) {
    timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
  }
}