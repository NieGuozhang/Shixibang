package com.hbut.internship.view;

import com.hbut.internship.R;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.widget.TextView;


/**
 * 验证码倒计时
 */
public class CountDownTimerUtils extends CountDownTimer {

	private TextView mTextView;

	public CountDownTimerUtils(TextView textView, long millisInFuture,
			long countDownInterval) {
		super(millisInFuture, countDownInterval);
		this.mTextView = textView;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		mTextView.setClickable(false); // 设置不可点击
		mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送"); // 设置倒计时时间
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setBackgroundResource(R.drawable.shape_edit_huise); // 设置按钮为灰色，这时是不能点击的

		/**
		 * 超链接 URLSpan 文字背景颜色 BackgroundColorSpan 文字颜色 ForegroundColorSpan 字体大小
		 * AbsoluteSizeSpan 粗体、斜体 StyleSpan 删除线 StrikethroughSpan 下划线
		 * UnderlineSpan 图片 ImageSpan
		 * http://blog.csdn.net/ah200614435/article/details/7914459
		 */
		SpannableString spannableString = new SpannableString(mTextView
				.getText().toString()); // 获取按钮上的文字
		ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
		/**
		 * public void setSpan(Object what, int start, int end, int flags) {
		 * 主要是start跟end，start是起始位置,无论中英文，都算一个。
		 * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
		 */
		spannableString.setSpan(span, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);// 将倒计时的时间设置为红色
		mTextView.setText(spannableString);
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		mTextView.setText("重新获取验证码");
		mTextView.setClickable(true);// 重新获得点击
		mTextView.setBackgroundResource(R.drawable.shape2_blue); // 还原背景色
	}

}
