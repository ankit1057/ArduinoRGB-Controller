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
		if (!allLamps) {
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

		byte[] color = { (byte) cols[0], (byte) cols[1], (byte) cols[2] };
		return color;
	}

	// Change color values in the byte array
	public void addColor(byte[] packet, int offset, String colStr) {
		// TODO: Try using changeable arguments instead
		String[] colsStr = colStr.split("-");
		for (int i = 0; i < 3; i++) {
			packet[offset+i] = (byte) Integer.parseInt(colsStr[i]);
		}
	}

	// Create the fade packet
	public byte[] fade(boolean[] lamps, String cols, int fadeTime) {
		// (Re-)Initialize class
		init(lamps);
		byte[] ret;

		if (allLamps) {

			ret = new byte[5];
			ret[0] = (byte) (ALL | FADE); // CTRL byte

			// Set colors
			addColor(ret, 1, cols);

			// Set fadeTime
			ret[4] = (byte) fadeTime;
		} else {
			// Array pointer
			int pos = 0;

			// Initialize the array to
			ret = new byte[selectedLamps.size() * 5];
			for (int lamp : selectedLamps) {
				// Set control byte
				ret[pos] = ctrl(lamp, FADE);
				
				// Set colors
				addColor(ret, pos+1, cols);
				
				// Set fade
				ret[pos + 4] = (byte) fadeTime;
				
				// Go 5 bytes in
				pos += 5;
			}
		}

		return ret;

	}

}