package com.reembox.helper;
import com.reembox.helper.Application;

public class ReemboxHelper {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Application theAppWindow = new Application();
		theAppWindow.setBounds(200, 200, 830, 285);
		theAppWindow.setResizable(false);
		theAppWindow.show();
	}

}
