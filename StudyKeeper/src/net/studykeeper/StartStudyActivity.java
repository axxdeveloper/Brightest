package net.studykeeper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.studykeeper.time.StudyTime;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StartStudyActivity extends ActionBarActivity {

	private ScheduledExecutorService scheduler;
	private volatile StudyTime studyTime = StudyTime.newInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initStartStudyButton();
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				runOnUiThread(new Runnable(){
					@Override
					public void run() {
						if ( studyTime != null ) {
							TextView v = (TextView) findViewById(R.id.text_time);
							v.setText(studyTime.totalDurationAsString());							
						}
					}});
			}}, 0, 1, TimeUnit.SECONDS);
	}
	
	private void initStartStudyButton() {
		final Button b = (Button) findViewById(R.id.action_start);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ( "Start".equals(b.getText()) ) {
					studyTime.start();
					b.setText("Pause");
				} else if ( "Pause".equals(b.getText()) ) {
					studyTime.stop();
					b.setText("Start");
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_start_study, container, false);
		}
	}

}
