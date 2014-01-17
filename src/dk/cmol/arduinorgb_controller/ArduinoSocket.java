package dk.cmol.arduinorgb_controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class ArduinoSocket extends Thread{
	
	private ArduinoRGBActivity parent = null;
	private Socket sock = null;
	private OutputStream stream = null;
	private String ip = null;
	private int port;
	private Handler mHandler;
	private int timeout = 1000;
	private boolean connected;
	
	public ArduinoSocket(ArduinoRGBActivity parent) {
		this.parent = parent;
	}
	
	public void run() {
		
		Looper.prepare();
		connect();

        mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    try {
                    	if (stream != null) {
                    		stream.write((byte[]) msg.obj);
    						stream.write((byte) 10);
    						Log.i("ArduinoSocket", "Data written...");
						}
                    	else {
							throw new IOException("Sorry, phone not connected :-(\nPlase check your settings!");
						}
					} catch (IOException e) {
						Toast toast = Toast.makeText(parent.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
						toast.show();
					}
                }
        };
        Looper.loop();
	}
	
	public void writeMessage(final byte[] message) {
		if (connected) {
			Message msg = Message.obtain();
			msg.obj = message;
			mHandler.sendMessage(msg);
		} else {
			Log.w("ArduinoSocket", "Trying to send data without connection");
			Toast toast = Toast.makeText(parent.getApplicationContext(), "Sorry, phone not connected :-(\nPlase check your settings!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	private void connect() {
		getPreferences();
		try {
			sock = new Socket();
			sock.connect(new InetSocketAddress(ip, port), timeout);
			stream = sock.getOutputStream();
			
			if(sock == null) {
				throw new IOException();
			}
			connected = true;
		} catch (IOException e) {
			//e.printStackTrace();
			Log.w("ArduinoSocket", "Unable to connect");
			Toast toast = Toast.makeText(parent.getApplicationContext(), "Failed to connect :-(\nPlase check your settings!", Toast.LENGTH_LONG);
			toast.show();
			connected = false;
		}
	}
	
	private void getPreferences() {
		// Read settings from settings manager
		Log.i("ArduinoSocket", "Setting IP and PORT");
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(parent);
		try {
			ip = sharedPref.getString("server_addr", "");
			port = Integer.parseInt(sharedPref.getString("server_port", ""));
		} catch (Exception e) {
			Log.w("ArduinoSocket", "Failed setting IP and PORT");
			Toast toast = Toast.makeText(parent.getApplicationContext(), "Please input settings", Toast.LENGTH_LONG);
			toast.show();
		}
		
	}
	
	public void close() {
		try {
			mHandler = null;
			if (sock != null) {
				sock.close();
			}
		} catch (IOException e) {
			// TODO Write error to parent view
			//e.printStackTrace();
			Log.e("ArduinoSocket", e.getMessage());
		}
	}

}
