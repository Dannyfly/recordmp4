/*
 * prsclient JNI½Ó¿Ú
 */
package com.pcc.jni;

import java.io.File;

import android.content.Context;
import android.widget.FrameLayout;
import com.longmaster.video.*;



public class PcsClientJNI extends JNIBase
{
	
	public PcsClientJNI()
	{
	
	}
	
	
	public int load() {
		return loadLib("pccshell");
	}
	
    /**/
	public native int openClient(String path, int logLevel);
	public native int closeClient();
    public native boolean initialCamera(int type,int qos);
    public native void releaseCamera();
    public native boolean setImageviews(Context context, FrameLayout captureView, FrameLayout renderView, Object listener);
    /**/
	
    // Record
    public native int setFFMPEGParam(String path, int flag, int gop_size, int bitreate, String preset, String profile, String tune, String cq);
    public native int initRecord(String path, int recordSourceType, String strTemp);
    public native int stopRecord();
    public native int pauseRecord();
    public native int startRecord();
    public native int resumeRecord();
    

	
}