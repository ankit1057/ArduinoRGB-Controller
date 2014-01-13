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
		default:
			fragment = new ColorDotsFragment();
			break;
		}

		
		/*Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);*/
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 2; 
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Dots";
		case 1:
			return "Slider";
		}
		return null;
	}
}