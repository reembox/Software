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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class RFMIntertechnoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel codePanel;
	private JComboBox letterBox, numberBox;
	private JLabel rfm12Command;
	private JRadioButton rfm12on, rfm12off;
	private JButton copyToClipboard, testCommand;

	public RFMIntertechnoPanel() {
		super(new FlowLayout());
		this.setMaximumSize(new java.awt.Dimension(410, 130));
		this.setMinimumSize(new java.awt.Dimension(410, 130));
		this.setPreferredSize(new java.awt.Dimension(410, 130));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.codePanel = new JPanel(new GridLayout(2, 2));
		this.codePanel.setBorder(BorderFactory
				.createTitledBorder("Intertechno"));

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateCommand();
			}
		};
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P" };
		String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16" };

		this.letterBox = new JComboBox(letters);
		this.numberBox = new JComboBox(numbers);
		this.letterBox.addActionListener(listener);
		this.numberBox.addActionListener(listener);
		JLabel familyCode = new JLabel("  Familiy Code  ");
		familyCode.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel devicecode = new JLabel("  Device Code  ");
		devicecode.setHorizontalAlignment(SwingConstants.CENTER);
		this.codePanel.add(familyCode);
		this.codePanel.add(devicecode);
		this.codePanel.add(this.letterBox);
		this.codePanel.add(this.numberBox);

		this.rfm12on = new JRadioButton("ON");
		this.rfm12on.setSelected(true);
		this.rfm12off = new JRadioButton("OFF");
		ButtonGroup onOffgroup = new ButtonGroup();
		onOffgroup.add(rfm12off);
		onOffgroup.add(rfm12on);
		this.rfm12on.addActionListener(listener);
		this.rfm12off.addActionListener(listener);

		this.copyToClipboard = new JButton("to clipboard");
		this.copyToClipboard.setPreferredSize(new Dimension(100, 30));
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
		this.testCommand.setPreferredSize(new Dimension(100, 30));
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
		String command = "rfm12 intertechno ";
		int a = 0, b = 0, c = 0;
		a = this.letterBox.getSelectedIndex() + 1;

		b = this.numberBox.getSelectedIndex() / 4 + 1;

		c = this.numberBox.getSelectedIndex() % 4 + 1;

		command = command + a + " " + b + " " + c + " "
				+ ((this.rfm12on.isSelected()) ? 1 : 0);
		this.rfm12Command.setText(command);
	}
}
