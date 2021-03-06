package com.hbut.internship.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.hbut.internship.R;
import com.hbut.internship.broadcast.NetReceiver;
import com.hbut.internship.util.TwiceBackOutUtil;

public class MainActivity extends TabActivity {

	NetReceiver mReceiver = new NetReceiver();
	IntentFilter mFilter = new IntentFilter();
	
	 private long exitTime = 0;

	private TabHost tabhost;
	private RadioGroup main_radiogroup;
	private RadioButton schoolrecommend, myinternship, findinternship;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabhost);
		ActivityCollector.addActivity(this);

		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);

		main_radiogroup = (RadioGroup) findViewById(R.id.main_radiogroup);

		// 往TabWidget添加Tab
		schoolrecommend = (RadioButton) findViewById(R.id.tab_schrecommend);
		myinternship = (RadioButton) findViewById(R.id.tab_myintern);
		findinternship = (RadioButton) findViewById(R.id.tab_findintern);

		tabhost = getTabHost();
		tabhost.addTab(tabhost.newTabSpec("tag1").setIndicator("0")
				.setContent(new Intent(this, SchoolRecommendActivity.class)));
		tabhost.addTab(tabhost.newTabSpec("tag2").setIndicator("1")
				.setContent(new Intent(this, FindInternshipActivity.class)));
		tabhost.addTab(tabhost.newTabSpec("tag3").setIndicator("2")
				.setContent(new Intent(this, MyInternshipActivity.class)));

		// 设置监听事件
		main_radiogroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.tab_schrecommend:
							tabhost.setCurrentTab(0);
							break;
						case R.id.tab_findintern:
							tabhost.setCurrentTab(1);
							break;
						case R.id.tab_myintern:
							tabhost.setCurrentTab(2);
							break;
						}
					}
				});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
		ActivityCollector.removeActivity(this);
	}

	/**
	 * 两次返回退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 4000) {
			Toast.makeText(getApplicationContext(), "再按一次退出应用",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}
}