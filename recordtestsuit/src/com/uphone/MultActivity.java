package com.uphone;

import static android.content.Intent.ACTION_MAIN;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.longmaster.video.CameraSetListener;
import com.pcc.jni.PCRecorderStateListener;
import com.pcc.media.PhonePlusChannelController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.longmaster.video.lmvCamera;



public class MultActivity extends Activity{
	
	private FrameLayout mCaptureFrame = null;
	private FrameLayout mRenderFrame = null;
	private String mp4file;
	private boolean isPause = true;
	Button startButton = null;
	Button pauseButton = null;
	Button stopButton = null;
	TextView tips = null;
	Camera camera;
	private static final String LOG_TAG = "lei";
	
	private boolean isFirstStar = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mult_activity);

		mp4file = "/mnt/sdcard/recordfile.mp4";
		mRenderFrame = (FrameLayout) findViewById(R.id.renderFrame);
		mCaptureFrame = (FrameLayout) findViewById(R.id.captureFrame);
		startButton = (Button) findViewById(R.id.btnstart);
		pauseButton = (Button) findViewById(R.id.btnpause);
		stopButton = (Button) findViewById(R.id.btnstop);
		tips = (TextView) findViewById(R.id.textView1);
		tips.setText("请先初始化~~");
		boolean starFlag = false;
		pauseButton.setEnabled(starFlag);
		stopButton.setEnabled(starFlag);
		
		
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Log.v("record", "Initial Record ~~~");
				
				String strPath = getApplicationContext().getPackageName();
				Log.v("record","getPackageName : "+strPath);
				
				PhonePlusChannelController.openClient("/data/data/"+strPath+"/lib", 6);
				//PhonePlusChannelController.setParam(0, 30, 300000, "superfast", "baseline", "zerolatency", "27");

				PhonePlusChannelController.initRecord(mp4file, MultActivity.this, mCaptureFrame, new PCRecorderStateListener() {	
					public void OnInitFinish(int i) {
						Log.v("20130305", "initRecord return value: "+Integer.toString(i));
					}
				});
				
				boolean flag = false;
				startButton.setEnabled(flag);
				pauseButton.setEnabled(!flag);
				stopButton.setEnabled(!flag);
				isPause = true;
				isFirstStar = true;
				tips.setText("还没开始录制呢，别浪费表情了！！！！");
				pauseButton.setText("开始录制");
			}
		});
		
		
		pauseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// 初始值为true
				isPause = !isPause;
				if (isPause)
				{
					Log.v("record", "Pause record ~~~");
					pauseButton.setText("开始录制");
					tips.setText("录制暂停中...别浪费表情了~~~~");
					PhonePlusChannelController.pauseRecord();
					
				}
				else
				{
					Log.v("record", "Resume record ~~~");
					pauseButton.setText("暂停录制");
					tips.setText("录制进行中......");
					if (isFirstStar)
					{
						PhonePlusChannelController.startRecord();
						isFirstStar = false;
					}
					else
					{
						PhonePlusChannelController.resumeRecord();
					}					
				}
				
			}
		});
		
		
		stopButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Log.v("record", "Stop Record ~~~");
				
				PhonePlusChannelController.stopRecord();
				boolean flag = false;
				stopButton.setEnabled(flag);
				startButton.setEnabled(!flag);
				pauseButton.setEnabled(flag);
				tips.setText("恭喜，录制完成！！！！！");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PhonePlusChannelController.releaseCamera();
		PhonePlusChannelController.closeClient();
	}

}


















