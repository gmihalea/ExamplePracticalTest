package ro.pub.cs.systems.eim.examplepracticaltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ExamplePracticalTestService extends android.app.Service{

	ProcessingThread pt = null;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		int number1 = intent.getIntExtra("first", -1);
		int number2 = intent.getIntExtra("second", -1);
		pt = new ProcessingThread(this, number1, number2);
		pt.start();
		return Service.START_REDELIVER_INTENT;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy(){
		
	}

}
