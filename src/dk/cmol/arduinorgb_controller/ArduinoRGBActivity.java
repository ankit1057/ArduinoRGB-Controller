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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arduino_rgb);
		sock = new ArduinoSocket(this);
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
			
			// Get color value
			String str_cols[] = v.getTag().toString().split("-");
			int cols[] = new int[3];
			for (int i = 0; i < str_cols.length; i++) {
				cols[i] = Integer.parseInt(str_cols[i]);
			}
			
			// Find out which lamp(s) to light
			boolean all_lamps = true;
			for (int i = 0; i < lamp_toggle.length; i++) {
				if (!lamp_toggle[i]) {
					all_lamps = false;
					break;
				}
			}
			
			int ctrl = 128;
			
			byte[] a = {(byte) ctrl,(byte) cols[0],(byte) cols[1],(byte) cols[2]};
			sock.write(a);
			
			tw.setText(str_cols[0]+"-"+str_cols[1]+"-"+str_cols[1]+((all_lamps) ? "TRUE" : "FALSE"));
			
		}
	}

}
