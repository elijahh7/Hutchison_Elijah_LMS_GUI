/*
 * Elijah Hutchison
 * CEN 3024C-24667
 * 3/9/2024
 *
 * Class Name: libraryTwo
 *
 * Description:
 *   This is my library class, it only contains one variable, a bookList where I store the
 *   data from the file the user imputed. This class has an addBook function which is used
 *   initially to add the books to the bookList. There are also separate methods to remove
 *   books using either their title or barcode number. Finally there are methods to check
 *   in and out books. The methods that require it test to make sure what the user is
 *   imputing makes sense. Each method also has detailed error messages and print statements
 *   so there should be no unnecessary code in the main class.
 *
 *   UPDATE: In order to implement this class into my GUI I had to change a few parts of my
     methods. In all methods I had to update the confirmation and error messages to be
     formatted as labels and display inside the GUI, not in the terminal. I also
     changed the variable type in the removeWithBarcode method from a int to a String
     this was to help avoid formatting errors. I also updated the printLibraryContents to
     correctly format the information and display it in the GUI.
 *
 */
package com.example.lmsgui;

import com.example.lmsgui.booksTwo;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class libraryTwo {
    // Create variable
    private List<booksTwo> bookList;

    // Constructor
    public libraryTwo() {
        bookList = new ArrayList<>();
    }

    /**
     * Adds a new book to the library with the specified barcode number and title.
     *
     * @param barcodeNumber The barcode number of the new book.
     * @param bookTitle The title of the new book.
     * @return void
     */
    public void addBook(String barcodeNumber, String bookTitle) {
        // Create a new book object with the provided barcode number and title
        booksTwo newBook = new booksTwo(barcodeNumber, bookTitle);

        // Add the new book to the library
        bookList.add(newBook);
    }

    /**
     * Removes a book from the library based on its barcode number.
     * Prints a confirmation message if the book is successfully removed.
     * Prints an error message if the book is not found in the library.
     *
     * @param barcodeNumber The barcode number of the book to be removed.
     * @return void
     */
    public void removeBookWithBarcode(String barcodeNumber , VBox root) {
        // Iterate through the library to find the book
        for (booksTwo book : bookList) {
            // Check if the barcode number of the current book matches the provided barcode number
            if (String.valueOf(book.getBarcodeNumber()).equals(String.valueOf(barcodeNumber))) {
                // Remove the book from the library
                bookList.remove(book);
                // Print a confirmation message if the book is successfully removed
                Label messageLabel = new Label("Book with barcode number " + barcodeNumber + " has been removed from the library.");
                messageLabel.setStyle("-fx-text-fill: white;");
                root.getChildren().add(messageLabel);
                // Exit the method once the book is removed
                return;
            }
        }
        // If the book is not found in the library, display an error message
        Label errorLabel = new Label("Book with barcode number " + barcodeNumber + " was not found in the library.");
        errorLabel.setStyle("-fx-text-fill: white;");
        root.getChildren().add(errorLabel);
        }
    /**
     * Removes a book from the library based on its title.
     * Prints a confirmation message if the book is successfully removed.
     * Prints an error message if the book is not found in the library.
     *
     * @param bookTitle The title of the book to be removed.
     * @return void
     */
    public void removeBookWithTitle(String bookTitle, VBox root) {
        // Iterate through the library to find the book
        for (booksTwo book : bookList) {
            // Check if the title of the current book matches the provided title
            if (book.getBookTitle().equals(bookTitle)) {
                // Remove the book from the library
                bookList.remove(book);
                // Print a confirmation message if the book is successfully removed
                Label messageLabel = new Label("Book titled " + bookTitle + " has been removed from the library.");
                messageLabel.setStyle("-fx-text-fill: white;");
                root.getChildren().add(messageLabel);
                // Exit the method once the book is removed
                return;
            }
        }
        // If the book is not found in the library, display an error message
        Label errorLabel = new Label("Book titled " + bookTitle + " was not found in the library.");
        errorLabel.setStyle("-fx-text-fill: white;");
        root.getChildren().add(errorLabel);
    }

    /**
     * Checks out a book from the library based on its title.
     * Prints a confirmation message if the book is successfully checked out.
     * Prints an error message if the book is not found in the library or is already checked out.
     *
     * @param bookTitle The title of the book to be checked out.
     * @return void
     */
    public void checkoutBook(String bookTitle, VBox root) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the due date (4 weeks from the current date)
        LocalDate dueDate = currentDate.plusWeeks(4);

        // Iterate through the library to find the book
        for (booksTwo book : bookList) {
            // Check if the title of the current book matches the provided title
            if (book.getBookTitle().equals(bookTitle)) {
                // Check if the book is already checked out
                if (book.isCheckedOut()) {
                    // Print an error message if the book is already checked out
                    Label errorLabel = new Label("Book titled " + bookTitle + " has already been checked out.");
                    errorLabel.setStyle("-fx-text-fill: white;");
                    root.getChildren().add(errorLabel);
                    return; // Exit the method
                } else {
                    // Set the book as checked out
                    book.setCheckedOut(true);
                    book.setDueDate(dueDate); // Reset due date
                    // Print a confirmation message if the book is successfully checked out
                    Label messageLabel = new Label("Book titled " + bookTitle + " has been checked out.");
                    messageLabel.setStyle("-fx-text-fill: white;");
                    root.getChildren().add(messageLabel);
                    return; // Exit the method
                }
            }
        }
        // If the loop completes without finding the book, print an error message
        Label errorLabel = new Label("Book titled " + bookTitle + " was not found in the library.");
        errorLabel.setStyle("-fx-text-fill: white;");
        root.getChildren().add(errorLabel);
    }

    /**
     * Checks out a book from the library based on its title.
     * Prints a confirmation message if the book is successfully checked out.
     * Prints an error message if the book is not found in the library or is already checked out.
     *
     * @param bookTitle The title of the book to be checked in.
     * @return void
     */
    public void checkInBook(String bookTitle, VBox root) {
        // Iterate through the library to find the book
        for (booksTwo book : bookList) {
            // Check if the title of the current book matches the provided title (case-insensitive)
            if (book.getBookTitle().equalsIgnoreCase(bookTitle)) {
                // Check if the book is already checked out
                if (book.isCheckedOut()) {
                    // Set the book as checked in
                    book.setCheckedOut(false);
                    // Print a confirmation message if the book is successfully checked in
                    Label messageLabel = new Label("Book titled " + bookTitle + " has been checked in.");
                    messageLabel.setStyle("-fx-text-fill: white;");
                    root.getChildren().add(messageLabel);

                    return;
                } else {
                    // Set the book as checked in and reset its due date
                    book.setCheckedOut(false);
                    book.setDueDate(null);

                    // Print an error message if the book is already checked in
                    Label errorLabel = new Label("Book titled " + bookTitle + " has already been checked in.");
                    errorLabel.setStyle("-fx-text-fill: white;");
                    root.getChildren().add(errorLabel);
                    return;
                }
            }
        }
        // If the loop completes without finding the book, print an error message
        Label errorLabel = new Label("Book titled " + bookTitle + " was not found in the library.");
        errorLabel.setStyle("-fx-text-fill: white;");
        root.getChildren().add(errorLabel);
    }

    /**
     * Prints the contents of the library.
     *
     * @return
     */
    public void printLibraryContents(TextArea textArea) {
        // Clear the existing content of the TextArea
        textArea.clear();

        // Append library contents to the TextArea
        if (bookList.isEmpty()) {
            textArea.appendText("The library is empty.");
        } else {
            textArea.appendText("Library Contents:\n");
            for (booksTwo book : bookList) {
                textArea.appendText(book.toString() + "\n");
            }
        }
    }
}



