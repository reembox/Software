package com.reembox.helper;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;

import javax.swing.JScrollPane;

public class IrReadPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultListModel commands;
	private JList responseList;
	private JButton readCommand, copyToClipboard, testCommand;

	public IrReadPanel() {
		super(new FlowLayout());
		this.setMaximumSize(new Dimension(410, 130));
		this.setMinimumSize(new Dimension(410, 130));
		this.setPreferredSize(new Dimension(410, 130));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.commands = new DefaultListModel();
		this.responseList = new JList(commands);
		// this.responseList.setMinimumSize(new Dimension(200, 100));
		JScrollPane listScroller = new JScrollPane(this.responseList);
		listScroller.setPreferredSize(new Dimension(200, 100));
		this.readCommand = new JButton("read");
		this.readCommand.setPreferredSize(new Dimension(100, 35));
		this.readCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readAll();
			}
		});
		this.copyToClipboard = new JButton("to clipboard");

		this.copyToClipboard.setPreferredSize(new Dimension(100, 35));
		this.copyToClipboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					StringSelection sers = new StringSelection(
							(String) commands.getElementAt(responseList
									.getSelectedIndex()));
					Toolkit.getDefaultToolkit().getSystemClipboard()
							.setContents(sers, null);
				} catch (Exception e1) {
				}

			}
		});
		this.testCommand = new JButton("test");
		this.testCommand.setPreferredSize(new Dimension(100, 35));
		this.testCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TelnetConnector.getInstance().sendCommand(
							(String) commands.getElementAt(responseList
									.getSelectedIndex()));
				} catch (Exception e2) {
				}
			}
		});
		this.add(listScroller);
		JPanel buttons = new JPanel(new GridLayout(3, 1));
		buttons.add(readCommand);
		buttons.add(copyToClipboard);
		buttons.add(testCommand);
		this.add(buttons);

	}

	public void readAll() {
		this.commands.clear();
		String response;
		while (!(response = TelnetConnector.getInstance().sendAndReceive(
				"irmp receive")).equals("OK\n")) {
			response = response.replace(":", " ");
			response = response.replace("\n", "");
			System.out.println(response);
			this.commands.addElement("irmp send " + response);
		}
	}
}
