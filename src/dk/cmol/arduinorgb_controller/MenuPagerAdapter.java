package dk.cmol.arduinorgb_controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MenuPagerAdapter extends FragmentPagerAdapter {

	public MenuPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		Fragment fragment;
		
		switch (position) {
		case 0:
			fragment = new ColorDotsFragment();
			break;
		case 1:
			fragment = new SliderFragment();
			break;
		case 2:
			fragment = new FaderFragment();
			break;
		case 3:
			fragment = new GyroFragment();
			break;
		default:
			fragment = new ColorDotsFragment();
			break;
		}

		return fragment;
	}

	@Override
	public int getCount() {
		// Show total pages.
		return 4; 
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Dots";
		case 1:
			return "Slider";
		case 2:
			return "Fader";
		case 3:
			return "Gyro";
		}
		return null;
	}
}