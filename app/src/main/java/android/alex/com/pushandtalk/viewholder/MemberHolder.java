package android.alex.com.pushandtalk.viewholder;

import android.alex.com.pushandtalk.Constants;
import android.alex.com.pushandtalk.R;
import android.alex.com.pushandtalk.activity.AVSingleChatActivity;
import android.alex.com.pushandtalk.adapter.MembersAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wli on 15/8/14.
 */
public class MemberHolder extends AVCommonViewHolder {

  public TextView mTextView;

  public MemberHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.activity_member_item);
    mTextView=(TextView)root.findViewById(R.id.member_item_name);
  }

  @Override
  public void bindData(Object o) {
    final MembersAdapter.MemberItem item = (MembersAdapter.MemberItem) o;
    mTextView.setText(item.content);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Activity host = (Activity) itemView.getContext();
        Intent intent = new Intent(host, AVSingleChatActivity.class);
        intent.putExtra(Constants.MEMBER_ID, item.content);
        host.startActivity(intent);
      }
    });
  }
}