package ro.pub.cs.systems.eim.examplepracticaltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExamplePracticalTestSecondaryActivity extends Activity {

	private TextView textView;
	private Button cancelButton;
	private Button okButton;

	private ButtonListener bl = new ButtonListener();
	
	private class ButtonListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.ok:
				setResult(RESULT_OK, null);
				break;
			case R.id.cancel:
				setResult(RESULT_CANCELED, null);
				break;
			}
			finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example_practical_test_secondary);
		
		this.textView = (TextView)findViewById(R.id.secondaryText);
		this.okButton = (Button)findViewById(R.id.ok);
		this.cancelButton = (Button)findViewById(R.id.cancel);
		
		this.okButton.setOnClickListener(bl);
		this.cancelButton.setOnClickListener(bl);
		
		// Receive the total number of clicks
		
		// Get the intent that started this activity
		Intent intent = getIntent();
		if(intent != null && intent.getExtras().containsKey("numberOfClicks")){
			int nr = intent.getIntExtra("numberOfClicks", -1);
			this.textView.setText(String.valueOf(nr));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.example_practical_test_secondary, menu);
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
}
