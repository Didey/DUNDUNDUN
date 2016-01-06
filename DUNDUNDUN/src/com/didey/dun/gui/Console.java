package com.didey.dun.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.didey.dun.engine.Logger;
import com.didey.dun.objects.Player;

public class Console extends JPanel implements ActionListener {

	public JTextField textField;
	private JTextArea textArea;
	private String newline = "\n";

	public static String[] commands = { "help", "exit", "gravity", "speed" };

	public String consoleInput;

	public Console() {
		super(new GridBagLayout());

		textField = new JTextField(20);
		textField.addActionListener(this);

		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		textArea.setBackground(Color.PINK);
		JScrollPane scrollPane = new JScrollPane(textArea);

		textArea.append("Type \"commands\" for a list of commands.");
		textArea.append(newline + "Type \"help <command>\" for detailed instructions.");

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(textField, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}

	// Every command for the debug console, opened by pressing / in game

	// Every command for the debug console, opened by pressing / in game

	public void actionPerformed(ActionEvent evt) {

		String text = textField.getText();
		consoleInput = textField.getText();
		if (consoleInput.split(" ")[0].equals("help")) {
			if (consoleInput.split(" ")[1].equals("speed")) {
				textArea.append(newline + "Changes the horizontal speed of the player.");
				textArea.append(newline + "USAGE: \"speed <number>\"");

			}

		} else {
			if (consoleInput.equals("exit")) {
				System.exit(0);
			}

			if (consoleInput.contains("gravity")) {
				try {

					String toSplit = consoleInput;

					String[] parts = toSplit.split(" ");
					String part1 = parts[0]; // just "gravity"
					String grav = parts[1]; // the value we need

					Logger.debug(grav);

					Player.gravity = Float.valueOf(grav);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					textArea.append(newline + "Invalid gravity value, please try again.(format: gravity <number>)");
				}
			} else if (consoleInput.contains("speed")) {

				try {

					String toSplit = consoleInput;

					String[] parts = toSplit.split(" ");
					String part1 = parts[0]; // just "speed"
					String speed = parts[1]; // the value we need
					Logger.debug(speed);
					Player.MOVE_SPEED = Float.valueOf(speed);
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					textArea.append(newline + "Invalid speed value, please try again.(format: speed <number>)");
				}
			} else {
				textArea.append(newline + consoleInput);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(new Date());

			textArea.append(newline + "[" + time + "]  " + consoleInput);
			textField.selectAll();

			// Double check text visibility.
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

	public static void createConsole() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		int w = ((int) width / 2) - 450;
		int h = (int) height - 50;

		JFrame frame = new JFrame("DUNDUNDUN Console!");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(new Console());

		frame.pack();
		frame.setPreferredSize(new Dimension(w, h));
		frame.setMaximumSize(new Dimension(w, h));
		frame.setMinimumSize(new Dimension(w, h));

		frame.setVisible(true);

	}

	public static void displayConsole() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// createConsole();
			}
		});
	}

}
