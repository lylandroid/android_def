package com.itheima.mobileguard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

/**
 * 代表的当前的应用程序，维护的是应用程序全部的状态信息。
 * 一个应用程序只会实例化一个application类
 * 单一实例 单态模式  singletons 
 * 
 * <p><b>注意:一定接的在清单文件配置</b></p>
 * @author Administrator
 *
 */
public class MobileGuardAppliation extends Application {
	/**
	 * 应用程序进程在第一次被创建的时候调用的方法，在任何其他对象创建之前执行的逻辑
	 */
	@Override
	public void onCreate() {
		//设置未捕获异常的处理器
		Thread.currentThread().setUncaughtExceptionHandler(new MyExecptionHandler());
		super.onCreate();
	}

	
	private class MyExecptionHandler implements UncaughtExceptionHandler{
		//当线程出现了未捕获的异常执行的方法。
		//不能阻止java虚拟机退出，只是在jvm退出之前， 留了一点时间， 留一个遗言
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				System.out.println("产生了异常，但是被哥给捕获了。");
				Field[] fileds = Build.class.getDeclaredFields();
				for(Field filed:fileds){
					System.out.println(filed.getName()+"--"+filed.get(null));
					sw.write(filed.getName()+"--"+filed.get(null)+"\n");
				}
				ex.printStackTrace(pw);
				File file = new File(Environment.getExternalStorageDirectory(),"log.txt");
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(sw.toString().getBytes());
				fos.close();
				pw.close();
				sw.close();
				//早死早超生
				android.os.Process.killProcess(android.os.Process.myPid());
				//原地复活
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
