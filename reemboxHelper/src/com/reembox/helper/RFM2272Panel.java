package com.reembox.helper;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class RFM2272Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel codePanel;
	private JCheckBox cboxes[];
	private JLabel cboxLabels[];
	private JLabel rfm12Command;
	private JRadioButton rfm12on, rfm12off;
	private JButton copyToClipboard, testCommand;

	public RFM2272Panel() {
		super(new FlowLayout());
		this.setMaximumSize(new java.awt.Dimension(410, 130));
		this.setMinimumSize(new java.awt.Dimension(410, 130));
		this.setPreferredSize(new java.awt.Dimension(410, 130));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.codePanel = new JPanel(new GridLayout(2, 10));
		this.codePanel.setBorder(BorderFactory
				.createTitledBorder("Intertechno Settings"));
		this.cboxes = new JCheckBox[10];
		this.cboxLabels = new JLabel[10];
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateCommand();
			}
		};

		for (int i = 0; i < 10; i++) {
			this.cboxes[i] = new JCheckBox();
			this.cboxes[i].addActionListener(listener);
			this.codePanel.add(this.cboxes[i]);
		}
		this.rfm12on = new JRadioButton("ON");
		this.rfm12on.setSelected(true);
		this.rfm12off = new JRadioButton("OFF");
		ButtonGroup onOffgroup = new ButtonGroup();
		onOffgroup.add(rfm12off);
		onOffgroup.add(rfm12on);
		this.rfm12on.addActionListener(listener);
		this.rfm12off.addActionListener(listener);
		for (int i = 0; i < 5; i++) {
			this.cboxLabels[i] = new JLabel(i + 1 + "");
		}
		this.cboxLabels[5] = new JLabel("A");
		this.cboxLabels[6] = new JLabel("B");
		this.cboxLabels[7] = new JLabel("C");
		this.cboxLabels[8] = new JLabel("D");
		this.cboxLabels[9] = new JLabel("E");
		for (int i = 0; i < 10; i++) {
			this.cboxLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			this.codePanel.add(cboxLabels[i]);
		}
		this.copyToClipboard = new JButton("to clipboard");
		this.copyToClipboard.setPreferredSize(new Dimension(100, 35));
		this.copyToClipboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection sers = new StringSelection(rfm12Command
						.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(sers, null);
			}
		});
		this.testCommand = new JButton("test");
		this.testCommand.setPreferredSize(new Dimension(100, 35));
		this.testCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TelnetConnector.getInstance().sendCommand(
						rfm12Command.getText());
			}
		});
		JPanel onoff = new JPanel(new GridLayout(2, 1));
		onoff.setBounds(100, 0, 100, 100);
		onoff.add(rfm12on);
		onoff.add(rfm12off);
		this.rfm12Command = new JLabel();
		this.rfm12Command.setHorizontalAlignment(SwingConstants.CENTER);
		this.rfm12Command.setPreferredSize(new Dimension(170, 35));
		this.add(codePanel);
		this.add(onoff);
		this.add(rfm12Command);
		this.add(copyToClipboard);
		this.add(testCommand);
		this.calculateCommand();
	}

	private void calculateCommand() {
		String command = "rfm12 2272 ";
		int a = 0, b = 0, c = 0;
		for (int i = 0; i < 4; i++) {
			if (!this.cboxes[i].isSelected()) {
				a = a + (int) Math.pow(2, (6 - i * 2));
			}
		}
		for (int i = 0; i < 4; i++) {
			if (!this.cboxes[i + 4].isSelected()) {
				b = b + (int) Math.pow(2, (6 - i * 2));
			}
		}
		if (!this.cboxes[8].isSelected()) {
			c = c + 64;
		}
		if (!this.cboxes[9].isSelected()) {
			c = c + 16;
		}
		if (this.rfm12on.isSelected()) {
			c++;
		}
		command = command + a + "," + b + "," + c + " 76 3";
		this.rfm12Command.setText(command);
	}

}
