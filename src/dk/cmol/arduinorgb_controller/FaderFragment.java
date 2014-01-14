package dk.cmol.arduinorgb_controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class FaderFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_fader, container,
				false);

		// Add min and max to number picker elements
		NumberPicker[] nps = {(NumberPicker) rootView.findViewById(R.id.numberPicker1),
				(NumberPicker) rootView.findViewById(R.id.numberPicker2),
				(NumberPicker) rootView.findViewById(R.id.numberPicker3)
		};
		
		for (int i = 0; i < nps.length; i++) {
			nps[i].setMaxValue(255);
			nps[i].setMinValue(0);
		}
		

		return rootView;
	}
}