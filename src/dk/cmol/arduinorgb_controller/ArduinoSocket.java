package dk.cmol.arduinorgb_controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class ArduinoSocket extends Thread{
	
	private ArduinoRGBActivity parent = null;
	private Socket sock = null;
	private OutputStream stream = null;
	private String ip = null;
	private int port;
	public Handler mHandler;
	
	public ArduinoSocket(ArduinoRGBActivity parent) {
		this.parent = parent;
	}
	
	public void run() {
		connect();
		
		Looper.prepare();

        mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    try {
						stream.write((byte[]) msg.obj);
						stream.write((byte) 10);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
        };
        Looper.loop();
	}
	
	public void writeMessage(final byte[] message) {
		Message msg = Message.obtain();
		msg.obj = message;
		mHandler.sendMessage(msg);
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
		// Read settings from settings manager
		Log.i("ArduinoSocket", "Setting IP and PORT");
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(parent);
		ip = sharedPref.getString("server_addr", "");
		port = Integer.parseInt(sharedPref.getString("server_port", ""));
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
