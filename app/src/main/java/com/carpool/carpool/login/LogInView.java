package com.carpool.carpool.login;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.carpool.carpool.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by petar on 20/02/2017. */
public class LogInView extends LinearLayout {

  @BindView(R.id.email)
  EditText email;

  @BindView(R.id.password)
  EditText password;

  @BindView(R.id.log_in)
  Button logIn;

  public LogInView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    ButterKnife.bind(this);
  }
}
