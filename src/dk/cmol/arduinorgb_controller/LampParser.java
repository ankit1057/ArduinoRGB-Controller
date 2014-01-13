package dk.cmol.arduinorgb_controller;

import java.util.ArrayList;

public class LampParser {

	// Define operations
	private final int SET = 0;
	private final int FADE = 1;
	
	private final int ALL = 128;

	boolean allLamps;
	ArrayList<Integer> selectedLamps;

	public LampParser() {
	}

	private void init(boolean[] lamps) {
		// Check if the array is the wrong length
		// TODO: Make the number of lamps dynamic (1-4)
		if (lamps.length != 4) {
			throw new IllegalArgumentException();
		}

		// Check if the all lamps flag should be set
		allLamps = true;
		for (int i = 0; i < lamps.length; i++) {
			if (!lamps[i]) {
				allLamps = false;
				break;
			}
		}
		
		// Build array list with lamps
		if(!allLamps) {
			selectedLamps = new ArrayList<Integer>();
			for (int i = 0; i < lamps.length; i++) {
				if (lamps[i]) {
					selectedLamps.add(i);
				}
			}
		}
		
	}

	// Create control byte, use on a per lamp basis
	public byte ctrl(int lamp, int mode) {
		return (byte) ((lamp << 5) | mode);
	}
	
	// Generate a byte array from the color string
	public byte[] col(String colStr) {
		// TODO: Try using changeable arguments instead
		String[] colsStr = colStr.split("-");
		int cols[] = new int[3];
		for (int i = 0; i < 3; i++) {
			cols[i] = Integer.parseInt(colsStr[i]);
		}
		
		byte[] color = {(byte) cols[0], (byte) cols[1], (byte) cols[2]};
		return color;
	}

	// Create the fade packet
	public byte[] fade(boolean[] lamps, String cols, int fadeTime) {
		// (Re-)Initialize class
		init(lamps);
		byte[] ret;
		
		if (allLamps) {
			byte[] color = col(cols); // TODO: Better names
			
			ret = new byte[5];
			ret[0] = (byte) (ALL | FADE); // CTRL byte
			
			// Set colors
			// TODO: Figure out if you can do something smarter than this
			ret[1] = color[0];
			ret[2] = color[1];
			ret[3] = color[2];
			
			// Set fadeTime
			ret[4] = (byte) fadeTime;
		}
		else {
			// Array pointer
			int pos = 0;
			
			byte[] color = col(cols); // TODO: Better names
			
			// Initialize the array to 
			ret = new byte[selectedLamps.size()*5];
			for (int lamp : selectedLamps) {
				ret[pos] 	= ctrl(lamp, FADE);
				ret[pos+1]	= color[0];
				ret[pos+2]	= color[1];
				ret[pos+3]	= color[2];
				ret[pos+4] = (byte) fadeTime;
				pos += 5;
			}
		}
		
		return ret;

	}

}
