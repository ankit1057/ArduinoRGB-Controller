package dk.cmol.arduinorgb_controller;

import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.PorterDuff;

public class ArduinoRGBActivity extends Activity {
	
	public boolean lamp_toggle[] = {false,false,false,false};
	private ArduinoSocket sock = null;
	private LampParser lp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arduino_rgb);
		sock = new ArduinoSocket(this);
		lp = new LampParser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.arduino_rgb, menu);
		return true;
	}
	
	
	// Toggle lamp sequence
	public void toggleLamp(View v) {
		Button button = (Button) v;
		int pos = Integer.parseInt(button.getText().toString()) - 1;
		
		if (lamp_toggle[pos]) {
			button.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
		}
		else {
			button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
		}
		
		// Change value of button
		lamp_toggle[pos] = !lamp_toggle[pos];
		
		TextView tw = (TextView) findViewById(R.id.debug_1);
		tw.setText(lamp_toggle[pos] ? "TRUE" : "FALSE");
		
	}
	
	// React to a pressed color
	public void colorPress(View v) {
		
		TextView tw = (TextView) findViewById(R.id.debug_1);
		
		// Check to see if any lamps are toggled on
		boolean go = false;
		for (int i = 0; i < lamp_toggle.length; i++) {
			if (lamp_toggle[i]) {
				go = true;
				break;
			}
		}
		
		if (go) {
			
			sock.write(lp.fade(lamp_toggle, v.getTag().toString(), 1));
			
		}
	}

}
