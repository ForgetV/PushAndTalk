package android.alex.com.pushandtalk.viewholder;

import android.alex.com.pushandtalk.BusProvider;
import android.alex.com.pushandtalk.R;
import android.alex.com.pushandtalk.event.ImTypeMessageResendEvent;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wli on 15/8/13.
 * * 聊天时居右的文本 holder
 */
public class RightTextHolder extends AVCommonViewHolder {

  protected TextView timeView;
  protected TextView contentView;
  protected TextView nameView;
  protected FrameLayout statusView;
  protected ProgressBar loadingBar;
  protected ImageView errorView;

  private AVIMMessage message;

  public RightTextHolder(Context context, ViewGroup root) {
    super(LayoutInflater.from(context).inflate(R.layout.chat_right_text_view,null));
    View v=LayoutInflater.from(context).inflate(R.layout.chat_right_text_view,null);
    timeView=(TextView)v.findViewById(R.id.chat_right_text_tv_time);
    contentView=(TextView)v.findViewById(R.id.chat_right_text_tv_content);
    nameView=(TextView)v.findViewById(R.id.chat_right_text_tv_name);
    statusView=(FrameLayout)v.findViewById(R.id.chat_right_text_layout_status);
    loadingBar=(ProgressBar)v.findViewById(R.id.chat_right_text_progressbar);
    errorView=(ImageView)v.findViewById(R.id.chat_right_text_tv_error);
    errorView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onErrorClick(v);
      }
    });
  }
  public void onErrorClick(View view) {
    ImTypeMessageResendEvent event = new ImTypeMessageResendEvent();
    event.message = message;
    BusProvider.getInstance().post(event);
  }

  @Override
  public void bindData(Object o) {
    message = (AVIMMessage)o;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String time = dateFormat.format(message.getTimestamp());

    String content = getContext().getString(R.string.unspport_message_type);;
    if (message instanceof AVIMTextMessage) {
      content = ((AVIMTextMessage)message).getText();
    }

    contentView.setText(content);
    timeView.setText(time);
    nameView.setText(message.getFrom());

    if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == message.getMessageStatus()) {
      errorView.setVisibility(View.VISIBLE);
      loadingBar.setVisibility(View.GONE);
      statusView.setVisibility(View.VISIBLE);
    } else if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusSending == message.getMessageStatus()) {
      errorView.setVisibility(View.GONE);
      loadingBar.setVisibility(View.VISIBLE);
      statusView.setVisibility(View.VISIBLE);
    } else {
      statusView.setVisibility(View.GONE);
    }
  }

  public void showTimeView(boolean isShow) {
    timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
  }
}
