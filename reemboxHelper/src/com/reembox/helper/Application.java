package com.reembox.helper;


import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Application extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField ipField,portField;
	private JLabel connectedLabel;

	private RFMIntertechnoPanel intertechnoPanel;
	private RFM2272Panel rfm12Panel;
	private IrReadPanel irPanel;
	
	ActionListener listener;

	Application() {

		this.initWindow();

		this.getContentPane().setLayout(null);

		this.initConnection();

		this.addWindowListener(new WindowListener() {

			public void windowClosed(WindowEvent arg0) {

			}

			public void windowActivated(WindowEvent e) {

			}

			public void windowClosing(WindowEvent e) {
				// JFrame frame = new JFrame("Error");
				// JOptionPane.showMessageDialog(frame,
				// "Restart VLC for normal Use!");
				System.exit(0);
			}

			public void windowDeactivated(WindowEvent e) {

			}

			public void windowDeiconified(WindowEvent e) {

			}

			public void windowIconified(WindowEvent e) {

			}

			public void windowOpened(WindowEvent e) {

			}

		});

	}

	private void initConnection() {
		TelnetConnector.getInstance().connect(ipField.getText(), 2701);
	}

	protected void initWindow() {
		ActionListener connectionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connectedLabel.setText("connecting");
				TelnetConnector.getInstance().connect(ipField.getText(), Integer.parseInt(portField.getText()));
			}
		};
		JPanel main = new JPanel(new GridLayout(2,2));
		JPanel hostPanel = new JPanel(new GridLayout(2,2));
		this.connectedLabel = new JLabel("not connected");
		this.connectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TelnetConnector.getInstance().setLabel(connectedLabel);
		
		this.ipField = new JTextField("192.168.0.90");
		this.ipField.addActionListener(connectionListener);
		this.portField = new JTextField("2701");
		this.portField.addActionListener(connectionListener);
		
		this.rfm12Panel = new RFM2272Panel();
		main.add(rfm12Panel);
		this.intertechnoPanel = new RFMIntertechnoPanel();
		main.add(intertechnoPanel);
		this.irPanel = new IrReadPanel();
		main.add(irPanel);
		
		JButton connectButton = new JButton("connect");
		connectButton.addActionListener(connectionListener);
		hostPanel.add(ipField);
		hostPanel.add(portField);
		hostPanel.add(connectedLabel);
		hostPanel.add(connectButton);
		main.add(hostPanel);
		
		
		
		this.getContentPane().add(main);
		this.pack();
		this.setVisible(true);
	}
}
