package ro.pub.cs.systems.eim.examplepracticaltest;

import java.util.Date;
import java.util.Random;
 
import android.content.Context;
import android.content.Intent;
import android.util.Log;
public class ProcessingThread extends Thread{
	
	final public static String[] actionTypes = {
			"actionType1",
			"actionType2",
			"actionType3"
		};

    private Random random = new Random();
	private Context context;
	private double geometricMean;
	private int arithmeticMean;
	private boolean isRunning = true;
	
	 public ProcessingThread(Context context, int firstNumber, int secondNumber) {
	    this.context = context;
	 
	    arithmeticMean = (firstNumber + secondNumber) / 2;
	    geometricMean = Math.sqrt(firstNumber * secondNumber);
	  }
		 
	  @Override
	  public void run() {
	    Log.d("[ProcessingThread]", "Thread has started!");
	    while (isRunning) {
	      sendMessage();
	      sleep();
	    }
	    Log.d("[ProcessingThread]", "Thread has stopped!");
	  }
	 
	  private void sendMessage() {
	    Intent intent = new Intent();
	    intent.setAction(actionTypes[random.nextInt(actionTypes.length)]);
	    intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
	    context.sendBroadcast(intent);
	  }
	 
	  private void sleep() {
	    try {
	      Thread.sleep(10000);
	    } catch (InterruptedException interruptedException) {
	      interruptedException.printStackTrace();
	    }
	  }
	  
	  public void stopThread() {
		    isRunning = false;
		  }
}
