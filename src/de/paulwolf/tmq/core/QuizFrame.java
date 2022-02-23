package de.paulwolf.tmq.core;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuizFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final int INGS_GAP = 10;
	private static final String title = "TooManyQuizzes";

	private JPanel wrapper;
	private JButton[] answerButtons = new JButton[4];
	private JLabel questionLabel;

	public QuizFrame(String question, String[] answers) {

		wrapper = new JPanel(new GridBagLayout());
		questionLabel = new JLabel(question);
		questionLabel.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 4; i++) {
			answerButtons[i] = new JButton(answers[i]);
			answerButtons[i].addActionListener(this);
		}

		GridBagConstraints gbc = createGBC(0, 0, GridBagConstraints.HORIZONTAL, 2, 1);
		wrapper.add(questionLabel, gbc);
		for (int i = 0; i < 4; i++) {
			gbc = createGBC(i / 2, i % 2 + 1, GridBagConstraints.HORIZONTAL, 1, 1);
			wrapper.add(answerButtons[i], gbc);
		}

		this.setTitle(title);
		this.add(wrapper);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(this.getSize());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static GridBagConstraints createGBC(int x, int y, int fill, int width, int height) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.fill = fill;

		gbc.weightx = 1.0;
		gbc.weighty = 0.0;

		gbc.insets = new Insets(INGS_GAP, INGS_GAP, INGS_GAP, INGS_GAP);
		return gbc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < 4; i++) {
			if (e.getSource() == answerButtons[i]) {
				if (Main.update(i))
					this.setVisible(false);
			}
		}
	}
}
