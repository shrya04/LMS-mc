package com.test.lms.repository;

import com.test.lms.models.Book;

import java.util.HashMap;

public class BookRepository {
    static HashMap<String, Book> bookHashMap;

    //bookId countOfBooksAvailable
    static HashMap<String, Integer> bookNameCount;


    public static void addBook(Book book) {
        if (bookHashMap == null) {
            bookHashMap = new HashMap<>();
        }
        if (bookHashMap.containsKey(book.getId())) {
            throw new RuntimeException("Book already registered");
        }
        bookHashMap.put(book.getId(), book);
        if (bookNameCount == null) {
            bookNameCount = new HashMap<>();
        }
        if (bookNameCount.containsKey(book.getId())) {
            bookNameCount.put(book.getId(), bookNameCount.get(book.getId()) + 1);
        } else {
            bookNameCount.put(book.getId(), 1);
        }
        System.out.println("Book Count " + bookNameCount.get(book.getId()));
    }

    public static Book getBookFromId(String bookId) {
        if (!bookHashMap.containsKey(bookId)) {
            throw new RuntimeException("Book ID not present");
        }
        return bookHashMap.get(bookId);
    }

    public static Integer getNumberOfAvailableBooks(String bookId) {
        return bookNameCount.get(bookId);
    }

    public static void reserveBook(String bookId) {
        bookNameCount.replace(bookId, bookNameCount.get(bookId) - 1);
        System.out.println("Marking book not available");
    }

    public static void updateBookReturn(String bookId) {
        bookNameCount.put(bookId, bookNameCount.get(bookId) + 1);
        System.out.println("Marking book as available");
    }
}
