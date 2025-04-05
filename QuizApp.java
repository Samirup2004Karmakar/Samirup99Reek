import java.util.*;

public class QuizApp {
    static Scanner scanner = new Scanner(System.in);
    static int score = 0;

    static class Question {
        String question;
        String[] options;
        int correctIndex;

        Question(String question, String[] options, int correctIndex) {
            this.question = question;
            this.options = options;
            this.correctIndex = correctIndex;
        }
    }

    static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?",
                new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Venus"}, 2));
        questions.add(new Question("Who wrote 'Romeo and Juliet'?",
                new String[]{"1. Charles Dickens", "2. William Shakespeare", "3. Mark Twain", "4. J.K. Rowling"}, 2));
        return questions;
    }

    static void askQuestion(Question q, int index) {
        System.out.println("Q" + (index + 1) + ": " + q.question);
        for (String option : q.options) {
            System.out.println(option);
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\nTime's up!");
                System.exit(0);  // Ends quiz if time runs out
            }
        };

        timer.schedule(task, 15000); // 15-second timer

        System.out.print("Your answer (1-4): ");
        int userAnswer = scanner.nextInt();

        timer.cancel();

        if (userAnswer == q.correctIndex) {
            System.out.println("Correct!\n");
            score++;
        } else {
            System.out.println("Wrong. Correct answer: " + q.correctIndex + "\n");
        }
    }

    public static void main(String[] args) {
        List<Question> quiz = getQuestions();

        System.out.println("===== Welcome to the Quiz =====");
        System.out.println("You have 15 seconds per question.\n");

        for (int i = 0; i < quiz.size(); i++) {
            askQuestion(quiz.get(i), i);
        }

        // Show result
        System.out.println("===== Quiz Over =====");
        System.out.println("Your final score: " + score + "/" + quiz.size());
    }
}
