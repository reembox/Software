package com.reembox.helper;

import java.io.*;
import java.net.*;

import javax.swing.JLabel;

public class TelnetConnector {

	private PrintWriter pw;
	private InputStream ins;
	private JLabel label;
	private boolean connected;

	private static TelnetConnector instance = null;

	private TelnetConnector() {
		this.connected = false;
	}

	public void connect(String ip, int port) {
		try {
			SocketAddress sockaddr = new InetSocketAddress(ip, port);
			Socket socket = new Socket();
			socket.connect(sockaddr, 5000);
			pw = new PrintWriter(socket.getOutputStream(), true);
			ins = socket.getInputStream();
			this.label.setText("connected");
			this.connected = true;
		} catch (SocketTimeoutException e) {
			// JFrame frame = new JFrame("Error");
			// JOptionPane.showMessageDialog(frame, "Connection timed out",
			// "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			this.connected = false;

			this.label.setText("not connected");
		} catch (ConnectException e) {
			// JFrame frame = new JFrame("Error");
			// JOptionPane.showMessageDialog(frame, "Connection refused",
			// "Error",
			// JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			this.connected = false;

			this.label.setText("not connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			this.connected = false;

			this.label.setText("not connected");
		} catch (IOException e) {
			e.printStackTrace();
			this.connected = false;

			this.label.setText("not connected");
		}
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public void sendCommand(String command) {
		if (connected) {
			pw.println(command);
		}
	}

	public String sendAndReceive(String command) {
		if (connected) {
			pw.println(command);

			// check return value
			String buffer = "";
			byte[] b = new byte[1];
			try {
				int i = 0;
				while (ins.available() != 1) {
					System.out.println(ins.available());
					i = ins.read();
					b[0] = (byte) i;
					buffer += (new String(b));
				}
				i = ins.read();
				b[0] = (byte) i;
				buffer += (new String(b));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return buffer;
		}
		return "OK\n";
		// ... handle return value here
	}
	public static TelnetConnector getInstance() {
		if (instance == null) {
			instance = new TelnetConnector();
		}
		return instance;
	}
}
