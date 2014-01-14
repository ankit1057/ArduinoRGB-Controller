package dk.cmol.arduinorgb_controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SliderFragment extends Fragment implements OnSeekBarChangeListener {

	ToolbarListener activityCallback;
	
	public interface ToolbarListener {
		public void seekChange(String colors);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityCallback = (ToolbarListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ToolbarListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_slider, container,
				false);
		
		SeekBar seekBarR = (SeekBar) rootView.findViewById(R.id.seekBarR);
		SeekBar seekBarG = (SeekBar) rootView.findViewById(R.id.seekBarG);
		SeekBar seekBarB = (SeekBar) rootView.findViewById(R.id.seekBarB);
		
		seekBarR.setOnSeekBarChangeListener(this);
		seekBarG.setOnSeekBarChangeListener(this);
		seekBarB.setOnSeekBarChangeListener(this);

		return rootView;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		String cols = Integer.toString(((SeekBar)getActivity().findViewById(R.id.seekBarR)).getProgress()*5)+"-";
		cols += Integer.toString(((SeekBar)getActivity().findViewById(R.id.seekBarG)).getProgress()*5)+"-";
		cols += Integer.toString(((SeekBar)getActivity().findViewById(R.id.seekBarR)).getProgress()*5);
		activityCallback.seekChange(cols);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}