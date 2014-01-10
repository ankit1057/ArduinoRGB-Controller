package dk.cmol.arduinorgb_controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import android.util.Log;


public class ArduinoSocket {
	
	private ArduinoRGBActivity parent = null;
	private Socket sock = null;
	private OutputStream stream = null;
	private String ip = null;
	private int port;
	
	public ArduinoSocket(ArduinoRGBActivity parent) {
		this.parent = parent;
	}
	
	public void write(final byte[] buffer) {
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	connect();
				try {
					stream.write(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e("ArduinoSocket", e.getMessage());
					e.printStackTrace();
				}
				close();
		    }
		});

		thread.start();
	}
	
	private void connect() {
		getPreferences();
		try {
			sock = new Socket(ip, port);
			stream = sock.getOutputStream();
		} catch (IOException e) {
			// TODO: Write error to parent view
			e.printStackTrace();
			Log.e("ArduinoSocket", e.getMessage());
		}
	}
	
	private void getPreferences() {
		// TODO: Read prefs from key-stor values
		Log.i("ArduinoSocket", "Setting IP and PORT");
		ip = "172.16.0.26";
		port = 2000;
	}
	
	public void close() {
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Write error to parent view
			e.printStackTrace();
			Log.e("ArduinoSocket", e.getMessage());
		}
	}

}
