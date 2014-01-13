package dk.cmol.arduinorgb_controller;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ColorDotsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_color_dots,
				container, false);

		boolean[] lamps = ((ArduinoRGBActivity) getActivity()).lamp_toggle;

		Button btns[] = { (Button) getActivity().findViewById(R.id.lamp1),
				(Button) getActivity().findViewById(R.id.lamp2),
				(Button) getActivity().findViewById(R.id.lamp3),
				(Button) getActivity().findViewById(R.id.lamp4) };

		for (int i = 0; i < lamps.length; i++) {
			if (lamps[i]) {
				btns[i].getBackground().setColorFilter(Color.GREEN,
						PorterDuff.Mode.MULTIPLY);
			}
		}

		return rootView;
	}

}
