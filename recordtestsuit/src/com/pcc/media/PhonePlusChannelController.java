package com.pcc.media;

import java.io.File;

import com.longmaster.video.CameraSetListener;
import com.pcc.jni.PCRecorderStateListener;
//import com.pcc.jni.PcsCallBackImpl;
import com.pcc.jni.PcsClientJNI;


import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.widget.FrameLayout;


public class PhonePlusChannelController {
	private static PcsClientJNI prsClientJNI = new PcsClientJNI();
	private static boolean prsInit = false;
	private static boolean sessionCreated = false;
//	private static PcsCallBackImpl pcsCallback = null;

	public synchronized static int openClient(String path, int logLevel)
	{
		prsClientJNI.init();
		return prsClientJNI.openClient(path, logLevel);
	}
	
	public synchronized static int closeClient()
	{
		return prsClientJNI.closeClient();
	}

    public static boolean initialCamera(int type, int qos)
    {
    	 return prsClientJNI.initialCamera(type,qos);
    }
    
    public static void releaseCamera()
    {
    	prsClientJNI.releaseCamera();
    }
 
    public static boolean setImageviews(Context context, FrameLayout captureView, FrameLayout renderViewe, Object listener)
    {
    	return prsClientJNI.setImageviews(context, captureView, renderViewe, listener);
    }
    
    public static int setParam(int flag, int gop_size, int bitreate, String preset, String profile, String tune, String cq)
    {
    	prsClientJNI.setFFMPEGParam("fang", flag, gop_size, bitreate, preset, profile, tune, cq);
    	return 0;
    }
    
    public static int startRecord()
    {
    	return prsClientJNI.startRecord();
    }
    
    public static int stopRecord()
    {
    	 prsClientJNI.stopRecord();
    	 prsClientJNI.releaseCamera();
    	 return 0;
    }
    
    public static int pauseRecord()
    {
    	return prsClientJNI.pauseRecord();
    }
    
    public static int resumeRecord()
    {
    	return prsClientJNI.resumeRecord();
    }
    
    public static int initRecord(final String path, final Context context, FrameLayout preview, final PCRecorderStateListener listener)
    {
    	initialCamera(1,1);
    	setImageviews(context, preview, null, new CameraSetListener(){

			@Override
			public void CameraSetDone() {
				// TODO Auto-generated method stub
				String strTmp = "/data/data/"+ context.getApplicationContext().getPackageName()+"/tmp/";
				File file = new File(strTmp);
				if (!file.exists())
				{
					if(!file.mkdir())
					{
						listener.OnInitFinish(-3);
						return ;
					}
				}
				int ret = prsClientJNI.initRecord(path, 1, strTmp);
				listener.OnInitFinish(ret);
			}
    	});
		return 0;
    }
    
}
