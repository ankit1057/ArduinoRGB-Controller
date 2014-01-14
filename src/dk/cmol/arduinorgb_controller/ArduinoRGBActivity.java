package dk.cmol.arduinorgb_controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ArduinoRGBActivity extends FragmentActivity implements
		SliderFragment.ToolbarListener, android.widget.PopupMenu.OnMenuItemClickListener {

	// Setting vars
	public boolean lamp_toggle[] = { false, false, false, false };
	private ArduinoSocket sock = null;
	private LampParser lp;

	// This is were all the grim menu stuff begins
	MenuPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new MenuPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Create the parser and the socket
		lp = new LampParser();
		sock = new ArduinoSocket(this);
		sock.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Toggle lamp sequence
	public void toggleLamp(View v) {
		Button button = (Button) v;
		int pos = Integer.parseInt(button.getText().toString()) - 1;

		if (lamp_toggle[pos]) {
			button.getBackground().setColorFilter(Color.LTGRAY,
					PorterDuff.Mode.MULTIPLY);
		} else {
			button.getBackground().setColorFilter(Color.GREEN,
					PorterDuff.Mode.MULTIPLY);
		}

		// Change value of button
		lamp_toggle[pos] = !lamp_toggle[pos];
	}

	// React to a pressed color
	public void colorPress(View v) {

		// Check to see if any lamps are toggled on
		boolean go = false;
		for (int i = 0; i < lamp_toggle.length; i++) {
			if (lamp_toggle[i]) {
				go = true;
				break;
			}
		}

		if (go) {
			sock.writeMessage(lp.set(lamp_toggle, v.getTag().toString()));
			//sock.write(lp.set(lamp_toggle, v.getTag().toString()));
		}
	}

	public void seekChange(String colors) {
		// Check to see if any lamps are toggled on
		boolean go = false;
		for (int i = 0; i < lamp_toggle.length; i++) {
			if (lamp_toggle[i]) {
				go = true;
				break;
			}
		}

		if (go) {
			sock.write(lp.set(lamp_toggle, colors));
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		return false;
	}
}
