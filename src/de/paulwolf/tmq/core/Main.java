package de.paulwolf.tmq.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {

	private static int score;
	private static int currentQuestion;

	private static ArrayList<String> questions = new ArrayList<>();
	private static ArrayList<String[]> answers = new ArrayList<>();
	private static ArrayList<Integer> correctAnswers = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException {

		File questionFile = new File(System.getenv("Appdata") + "/TooManyQuizzes/questions.txt");
		if (Files.exists(questionFile.toPath())) {

			Scanner scanner = new Scanner(questionFile);
			String questionString = "";
			while (scanner.hasNextLine()) {
				questionString += scanner.nextLine() + "\n";
			}
			scanner.close();

			String[] singleQuestions = questionString.split("\\n\\n");
			for (int i = 0; i < singleQuestions.length; i++) {
				String[] qSingle = singleQuestions[i].split("\\n");
				questions.add(qSingle[0]);
				answers.add(new String[] { qSingle[1], qSingle[2], qSingle[3], qSingle[4] });
				correctAnswers.add(Integer.parseInt(qSingle[5]));
			}
			new QuizFrame(questions.toArray()[currentQuestion].toString(),
					(String[]) answers.toArray()[currentQuestion]);
		} else {
			System.out.println("Datei %Appdata%/TooManyQuizzes/questions.txt anlegen!");
		}
	}

	public static boolean update(int answer) {

		if (Integer.parseInt(correctAnswers.toArray()[currentQuestion].toString()) == answer) {
			currentQuestion++;
			score += 3;
			if (questions.size() > currentQuestion)
				new QuizFrame(questions.toArray()[currentQuestion].toString(),
						(String[]) answers.toArray()[currentQuestion]);
			else {
				JFrame frame = new JFrame("Du hast es geschafft!");
				JPanel wrapper = new JPanel();
				JLabel label = new JLabel(
						String.format("%d erreichte Punkte von %d möglichen Punkten.", score, questions.size() * 3));
				label.setHorizontalAlignment(JLabel.CENTER);
				wrapper.add(label);
				frame.add(wrapper);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setMinimumSize(frame.getSize());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}

			return true;
		}
		score -= 1;
		JOptionPane.showMessageDialog(null, "Das stimmt so nicht.", "Falsche Antwort", JOptionPane.ERROR_MESSAGE);
		return false;
	}
}
