package ro.pub.cs.systems.eim.examplepracticaltest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExamplePracticalTestActivity extends Activity {

	final public static String[] actionTypes = {
			"actionType1",
			"actionType2",
			"actionType3"
		};
	
	private IntentFilter intentFilter = new IntentFilter();
	private static final int THRESHOLD = 10;
	private static final int SERVICE_STOPPED = 2;
	private static final int SERVICE_STARTED = 1;
	private int serviceStatus = SERVICE_STOPPED;
	private Button pressMe = null;
	private Button pressMeToo = null;
	private EditText editText1 = null;
	private EditText editText2 = null;
	private ButtonCListener buttonListener = new ButtonCListener();
	private Button navigate;
	
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

	public class MessageBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("[Message]", intent.getStringExtra("message"));
		}
	}
	
	private class ButtonCListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int number1 = Integer.parseInt(editText1.getText().toString());
			int number2 = Integer.parseInt(editText2.getText().toString());
			
			switch(v.getId()){
			case R.id.pressMe:
				++number1;
				editText1.setText(String.valueOf(number1));
				break;
			case R.id.pressMeToo:
				++number2;
				editText2.setText(String.valueOf(number2));
				break;
			case R.id.navigate:
				Intent intent = new Intent(getApplicationContext(), ExamplePracticalTestSecondaryActivity.class);
				int numClicks = number1 + number2;
				intent.putExtra("numberOfClicks", numClicks);
				startActivityForResult(intent, 1);
				break;
			}
			
			// If the THRESHOLD is reached and the service is stopped
			if(number1 + number2 > THRESHOLD && serviceStatus == SERVICE_STOPPED){
				Intent intent = new Intent(getApplicationContext(), ExamplePracticalTestService.class);
				intent.putExtra("first", number1);
				intent.putExtra("second", number2);
				getApplicationContext().startService(intent);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example_practical_test);
		
		this.navigate = (Button)findViewById(R.id.navigate);
		this.pressMe = (Button)findViewById(R.id.pressMe);
		this.pressMeToo = (Button)findViewById(R.id.pressMeToo);
		this.editText1 = (EditText)findViewById(R.id.editText1);
		this.editText2 = (EditText)findViewById(R.id.editText2);
		
		this.pressMe.setOnClickListener(buttonListener);
		this.pressMeToo.setOnClickListener(buttonListener);
		this.navigate.setOnClickListener(buttonListener);
		
		if (savedInstanceState != null) {
	      if (savedInstanceState.containsKey("leftCount")) {
	        editText1.setText(savedInstanceState.getString("leftCount"));
	      } else {
	    	  editText1.setText(String.valueOf(0));
	      }
	      if (savedInstanceState.containsKey("rightCount")) {
	    	  editText2.setText(savedInstanceState.getString("rightCount"));
	      } else {
	    	  editText2.setText(String.valueOf(0));
	      }
	    } else {
	    	editText1.setText(String.valueOf(0));
	    	editText2.setText(String.valueOf(0));
	    }
		
		for(int i = 0; i < 3; ++i){
			intentFilter.addAction(actionTypes[i]);
		}
	}
	
	@Override
	protected void onDestroy() {
		Intent intent = new Intent(this, ExamplePracticalTestService.class);
		stopService(intent);
		super.onDestroy();
	}
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    registerReceiver(messageBroadcastReceiver, intentFilter);
	  }
	 
	  @Override
	  protected void onPause() {
	    unregisterReceiver(messageBroadcastReceiver);
	    super.onPause();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.example_practical_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  // Save UI state changes to the savedInstanceState.
	  // This bundle will be passed to onCreate if the process is
	  // killed and restarted.
	  savedInstanceState.putInt("MyInt1", Integer.parseInt(this.editText1.getText().toString()));
	  savedInstanceState.putInt("MyInt2", Integer.parseInt(this.editText2.getText().toString()));
	  // etc.
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  // Restore UI state from the savedInstanceState.
	  // This bundle has also been passed to onCreate.
	  int myInt1 = savedInstanceState.getInt("MyInt1");
	  int myInt2 = savedInstanceState.getInt("MyInt2");
	  
	  this.editText1.setText(String.valueOf(myInt1));
	  this.editText1.setText(String.valueOf(myInt2));
	}
	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 1) {
	      Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
	    }
	  }
}
