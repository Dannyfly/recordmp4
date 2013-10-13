android下录制MP4文件的Demo

直接用Eclipse加载即可运行，android设备要有SD卡，因为录制的MP4将存放在SD卡根目录

当然，这只是比较粗略的Demo，在庞杂的Android家族中，兼容性方面可能无法保证。

本人手机LT26i，可以正常运行，主流机型应该无问题。

so库是已经打好了的，库的代码以后再提供


Mp4录制接口说明(so库接口)
接口介绍

1. 初始化操作
int initRecord(String path, Context context, FrameLayout preview, PCRecorderStateListener listener)	

功能：初始化音视频设备
返回值：无意义
参数说明：
		path: Mp4输出文件的绝对路径+文件名（必须以”.mp4”、”.3gp”或”.mov”为后缀名），如“/mnt/sdcard/recordfile.mp4”。
		context : preview所在的Activity的context
		preview : 用于显示预览的Layout
		listener: 初始化视频状态监听器，listener中接口OnInitFinish（int ret）的返回值ret : 
				  成功返回0，初始化失败返回-1，如果设备不支持，则返回-2 (ARMv6以下)

2. 启动录制
int startRecord()

功能   ：  创建Mp4文件并写入Mp4文件头，并且开始录制
返回值 ：  成功返回0，失败返回-1。
参数   ：  无
备注   ：  此函数应只调一次

3.暂停录制
int pauseRecord()

功能   ：  暂停录制
返回值 ：  成功返回0，失败返回-1。
参数   ：  无
备注   ：  此函数的第一次调用应在startRecord()之后，此后应与
resumeRecord()循环交替调用。

4.继续录制
int resumeRecord()

功能：继续录制
返回值：成功返回0，失败返回-1。
参数：无
备注：此函数应在pauseRecord()之后调用，且与其循环交替调用。

5.停止录制
int stopRecord()

功能：  完成一次录制操作
返回值：成功返回0，失败返回-1。
参数：  无
备注：  此函数可在initRecord()、startRecord()、pauseRecord()、
		resumeRecord()之后调用。若在initRecord()之后调用，则无Mp4
		文件输出。此函数的调用标志着一次完整的录制操作。


接口调用时序：
  	Demo流程比较简单，在此不再赘述，详见
	<svn  root>/trunk/sources/testsuit/record/androidMultActivity.java


