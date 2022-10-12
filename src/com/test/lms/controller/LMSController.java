package com.test.lms.controller;

import com.test.lms.models.Book;
import com.test.lms.repository.BookRepository;
import com.test.lms.service.ReservationService;

public class LMSController {

    public static void addBook(String name, String authorName) {
        System.out.println(authorName);
        Book book = new Book(name, authorName);
        BookRepository.addBook(book);
        System.out.println("Book Added " + book.getId() +" "+ book.getName());
    }

    public static void reserveBook(String userId, String bookId, String startTime) {
        ReservationService.reserveBook(userId, bookId, startTime);
    }

    public static void returnBook(String userId, String bookId, String endTime) {
        ReservationService.returnBook(userId, bookId, endTime);
    }

    public static void calculateFine(String userId) {
        ReservationService.getFine(userId);
    }

    public static void showBooks(String userId) {
        ReservationService.showBooks(userId);
    }
}
