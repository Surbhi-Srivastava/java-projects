import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Note {
    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

public class NotesApp {
    private static ArrayList<Note> notes = new ArrayList<>();
    private static final String FILENAME = "notes.txt";         // File to save notes
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadNotes(); // Load saved notes from the file

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> createNote();
                case 2 -> viewNotes();
                case 3 -> editNote();
                case 4 -> deleteNote();
                case 5 -> {
                    saveNotes(); // Save notes to the file
                    scanner.close(); // Close scanner to avoid resource leaks
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nNotes Keeping App");
        System.out.println("1. Create a new note");
        System.out.println("2. View saved notes");
        System.out.println("3. Edit a note");
        System.out.println("4. Delete a note");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine();
        System.out.print("Enter note content: ");
        String content = scanner.nextLine();
        notes.add(new Note(title, content));
        System.out.println("Note created successfully.");
    }

    private static void viewNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() + " - " + notes.get(i).getContent());
        }
    }

    private static void editNote() {
        System.out.print("Enter note number to edit: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index < 1 || index > notes.size()) {
            System.out.println("Invalid note number.");
            return;
        }
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine();
        notes.get(index - 1).setContent(newContent);
        System.out.println("Note updated successfully.");
    }

    private static void deleteNote() {
        System.out.print("Enter note number to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index < 1 || index > notes.size()) {
            System.out.println("Invalid note number.");
            return;
        }
        notes.remove(index - 1);
        System.out.println("Note deleted successfully.");
    }

    private static void loadNotes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    notes.add(new Note(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous notes found. Starting fresh.");
        }
    }

    private static void saveNotes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Note note : notes) {
                writer.println(note.getTitle() + "|" + note.getContent());
            }
            System.out.println("Notes saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}