package com.pcc.jni;

public abstract class JNIBase {
	
	public JNIBase()
	{
		
	}
	
	public int init()
	{
		return load();
	}
	
	public abstract int load();

	
	protected int loadLib(String libName)
	{
		try {
			System.loadLibrary(libName);
		} catch (Throwable e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

}
