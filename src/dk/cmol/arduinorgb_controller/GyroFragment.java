package dk.cmol.arduinorgb_controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GyroFragment extends Fragment implements SensorEventListener, OnClickListener {
	// a TextView
	private TextView tv;
	private ToggleButton btn;
	// the Sensor Manager
	private SensorManager sManager;
	boolean sensor = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_gyro, container,
				false);

		sManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
		tv = (TextView) rootView.findViewById(R.id.gyroText);
		
		btn = (ToggleButton) rootView.findViewById(R.id.gyroButton);
		btn.setOnClickListener((OnClickListener) this);
		
		return rootView;
	}


	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// Do nothing.
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// if sensor is unreliable, return void
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			return;
		}

		// else it will output the Roll, Pitch and Yawn values
		tv.setText("Orientation X (Roll) :" + Double.toString((event.values[2]/90.0*255.0))
				+ "\n" + "Orientation Y (Pitch) :"
				+ Double.toString(event.values[1]/180.0*255.0) + "\n"
				+ "Orientation Z (Yaw) :" + Double.toString(event.values[0]/360.0*255.0));
	}

	@Override
	public void onClick(View v) {
		if (sensor) {
			sManager.unregisterListener(this);
		} else {
			sManager.registerListener(this,
					sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					SensorManager.SENSOR_DELAY_FASTEST);
		}
		sensor = !sensor;
	}

}