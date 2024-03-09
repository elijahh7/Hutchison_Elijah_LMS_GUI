/*
 * Elijah Hutchison
 * CEN 3024C-24667
 * 2/28/2024
 *
 * Class Name: books
 *
 * Description:
 *   This is my books class, it creates the book object for use in the library class.
 *   A book has three variables, barcode number, title, and checked out status. Each
 *   variable has a getter and a setter. The toString method is also overridden to
 *   to display more relevant results in the correct format.
 */
package com.example.lmsgui;

import java.time.LocalDate; // Import LocalDate class for handling dates
public class booksTwo {
    // Create variables
    private String barcodeNumber;
    private String bookTitle;
    private boolean isCheckedOut;
    private LocalDate dueDate;

    // Define constructors
    public booksTwo(String barcodeNumber, String bookTitle) {
        this.barcodeNumber = barcodeNumber;
        this.bookTitle = bookTitle;
    }

    public static Object calculateDueDate() {
        return null;
    }

    // Getters
    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }
    public LocalDate getDueDate() { return dueDate; }

    //Setters
    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
        // Set due date when checking out
        if (checkedOut) {
            this.dueDate = LocalDate.now().plusWeeks(4); // Due date is 4 weeks from current date
        } else {
            this.dueDate = null; // Reset due date when checking in
        }
    }

    // toString for books class
    @Override
    public String toString() {
        if (isCheckedOut) {
            return "Book [Barcode Number: " + barcodeNumber + ", Title: " + bookTitle + ", Status: Checked Out, Due Date: " + dueDate + "]";
        } else {
            return "Book [Barcode Number: " + barcodeNumber + ", Title: " + bookTitle + ", Status: Checked In, Due Date: null]";
        }
    }
}