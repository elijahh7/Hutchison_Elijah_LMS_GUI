/*
 * Elijah Hutchison
 * CEN 3024C-24667
 * 3/9/2024
 *
 * Class Name: Main
 *
 * Description:
 *   This is my Main class for my GUI. I am used JavaFX to design and implement it. This class implements the main
 *   GUI functionality to my LMS. It allows the user to add files containing books with a barcode and a title, It
 *   also allows users to interact with the library by removing books, checking in and out books, and displays the
 *   library contents.
 *
 *   This GUI system implements many of the same features as the last assignment the main difference is rather than the user using the terminal
 *   they instead use the GUI. The menu system is completely different from the case system I used last time as is the
 *   reading of files from the user.
 *
 * Absolute path: C:\Users\purpl\IdeaProjects\LMSGui\src\main\java\com\example\lmsgui\books.txt
 */
package com.example.lmsgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.Optional;

public class Main extends Application {

    // Create a library object to store the books
    libraryTwo myLibrary = new libraryTwo();

    private Stage primaryStage;
    private TextArea fileContentTextArea;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        // First scene: Upload file
        Label nameLabel = new Label("Please select a file:");
        TextArea nameTextArea = new TextArea();
        Button browseButton = new Button("Browse");
        Button submitButton = new Button("Upload File");

        // Apply CSS styles to the elements
        nameLabel.setStyle("-fx-text-fill: white;"); // Change text color to white
        nameTextArea.setStyle("-fx-background-color: #222; -fx-text-fill: black;"); // Change background color to dark gray and text color to white
        browseButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white"); // Change background color to crimson and text color to black
        submitButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white;"); // Change background color to crimson and text color to black

        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        root.setStyle("-fx-background-color: #36454F;"); // Change background color to charcole
        root.getChildren().addAll(nameLabel, nameTextArea, browseButton, submitButton);
        Scene scene = new Scene(root, 600, 500);

        // Second scene: Display file content
        fileContentTextArea = new TextArea();
        // Change background color to charcole and text color to white
        fileContentTextArea.setStyle("-fx-background-color: #333; -fx-text-fill: black;");
        Button backButton = new Button("Back");
        // Change background color to orange and text color to crimson
        backButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white;");

        // Create menu items
        MenuItem removeByBarcodeItem = new MenuItem("Remove Book by Barcode");
        MenuItem removeByTitleItem = new MenuItem("Remove Book by Title");
        MenuItem checkoutItem = new MenuItem("Checkout Book");
        MenuItem checkinItem = new MenuItem("Checkin Book");
        MenuItem exitItem = new MenuItem("Exit");

        // Create a menu
        Menu actionsMenu = new Menu("Actions");
        actionsMenu.setStyle("-fx-text-fill: white;");
        actionsMenu.getItems().addAll(
                removeByBarcodeItem,
                removeByTitleItem,
                checkoutItem,
                checkinItem,
                new SeparatorMenuItem(),
                exitItem
        );

        // Create a menu bar
        MenuBar menuBar = new MenuBar();
        // Change background color of the menu bar to charcole
        menuBar.setStyle("-fx-background-color: #DC143C;");
        menuBar.getMenus().add(actionsMenu);

        VBox secondSceneRoot = new VBox();
        secondSceneRoot.setPadding(new Insets(20));
        secondSceneRoot.setSpacing(10);
        secondSceneRoot.setStyle("-fx-background-color: #36454F;"); // Change background color to black
        secondSceneRoot.getChildren().addAll(menuBar, fileContentTextArea, backButton);
        Scene secondScene = new Scene(secondSceneRoot, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("LMS Upload File to Library");
        primaryStage.show();

        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                nameTextArea.setText(selectedFile.getAbsolutePath());
            }
        });

        submitButton.setOnAction(event -> {
            String filePath = nameTextArea.getText();
            try {
                String fileContent = readFileContent(filePath);
                fileContentTextArea.setText(fileContent);
                primaryStage.setScene(secondScene);

                // After setting the scene, add books to the library
                // Split the file content into lines and add books to the library
                Scanner scanner = new Scanner(fileContent);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String barcodeString = parts[0].trim();
                        String bookTitle = parts[1].trim();
                        myLibrary.addBook(barcodeString, bookTitle);
                    } else {
                        // Handle invalid format
                    }
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        backButton.setOnAction(event -> primaryStage.setScene(scene));

        // Set action event handler for removing a book using it's barcode
        removeByBarcodeItem.setOnAction(e -> {
            // Create a TextInputDialog to prompt the user for the barcode
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remove Book by Barcode");
            dialog.setHeaderText("Enter Barcode Number");
            dialog.setContentText("Barcode:");

            // Show the dialog and wait for the user's response
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(barcodeString -> {
                try {
                    // Parse the input barcode string as a string (not an integer)
                    String barcodeToRemove = barcodeString.trim();

                    // Remove the book from the library
                    myLibrary.removeBookWithBarcode(barcodeToRemove, secondSceneRoot);

                    // Display library
                    myLibrary.printLibraryContents(fileContentTextArea);
                } catch (NumberFormatException ex) {
                    // Handle invalid barcode input
                    System.out.println("Invalid barcode format. Please enter a valid integer.");
                }
            });
        });
        // Implement remove by title functionality
        removeByTitleItem.setOnAction(e -> {
            // Create a TextInputDialog to prompt the user for the barcode
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remove Book by Title");
            dialog.setHeaderText("Enter Book Title");
            dialog.setContentText("Title:");

            // Show the dialog and wait for the user's response
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(TitleString -> {
                try {
                    // Parse the input title string
                    String titleToRemove = TitleString;

                    // Remove the book from the library
                    myLibrary.removeBookWithTitle(titleToRemove, secondSceneRoot);

                    // Display library
                    myLibrary.printLibraryContents(fileContentTextArea);
                } catch (NumberFormatException ex) {
                    // Handle invalid barcode input
                    System.out.println("Invalid title format. Please enter a valid string.");
                }
            });
        });
        // Implement checkout functionality
        checkoutItem.setOnAction(e -> {
            // Create a TextInputDialog to prompt the user for the barcode
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Checkout Book");
            dialog.setHeaderText("Enter Book Title");
            dialog.setContentText("Title:");

            // Show the dialog and wait for the user's response
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(TitleString -> {
                try {
                    // Parse the input title string
                    String titleToCheckout = TitleString;

                    // Remove the book from the library
                    myLibrary.checkoutBook(titleToCheckout, secondSceneRoot);

                    // Display library
                    myLibrary.printLibraryContents(fileContentTextArea);
                } catch (NumberFormatException ex) {
                    // Handle invalid barcode input
                    System.out.println("Invalid title format. Please enter a valid string.");
                }
            });
        });
        // Implement checkin functionality
        checkinItem.setOnAction(e -> {
            // Create a TextInputDialog to prompt the user for the barcode
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Check Book in");
            dialog.setHeaderText("Enter Book Title");
            dialog.setContentText("Title:");

            // Show the dialog and wait for the user's response
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(TitleString -> {
                try {
                    // Parse the input title string
                    String titleToCheckin = TitleString;

                    // Remove the book from the library
                    myLibrary.checkInBook(titleToCheckin, secondSceneRoot);

                    // Display library
                    myLibrary.printLibraryContents(fileContentTextArea);
                } catch (NumberFormatException ex) {
                    // Handle invalid barcode input
                    System.out.println("Invalid title format. Please enter a valid string.");
                }
            });
        });

        exitItem.setOnAction(e -> primaryStage.close());
    }

    private String readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return new String(fileBytes);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
