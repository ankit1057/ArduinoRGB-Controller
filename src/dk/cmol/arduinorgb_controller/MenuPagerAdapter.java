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
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
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
		default:
			fragment = new ColorDotsFragment();
			break;
		}

		return fragment;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 3; 
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
		}
		return null;
	}
}