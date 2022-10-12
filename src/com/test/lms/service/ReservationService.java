package com.test.lms.service;

import com.test.lms.models.Book;
import com.test.lms.models.BorrowTime;
import com.test.lms.models.User;
import com.test.lms.repository.BookRepository;
import com.test.lms.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ReservationService {

    static HashMap<String, Queue<User>> waitList = new HashMap<>();

    public static void reserveBook(String userId, String bookId, String startTime) {
        User user = UserRepository.getUserFromId(userId);
        Book book = BookRepository.getBookFromId(bookId);
        //TODO:  Add check if the user is requesting for the same book again
        if (BookRepository.getNumberOfAvailableBooks(bookId) > 0) {
            System.out.println("Book available");
            user.getBorrowTimes().add(new BorrowTime(new Date(Long.parseLong(startTime)), book));
            BookRepository.reserveBook(bookId);
            System.out.println("Book borrowed");
        } else {
            updateWaitList(bookId, user);
        }
    }

    private static void updateWaitList(String bookId, User user) {
        System.out.println("Book Not available");
        if (waitList.containsKey(bookId)) {
            System.out.println("Waitlist already exisis, adding user");
            Queue<User> userWaitList = waitList.get(bookId);
            userWaitList.add(user);
        } else {
            System.out.println("Creating waitlist and adding user");
            Queue<User> userList = new LinkedList<>();
            userList.add(user);
            waitList.put(bookId, userList);
        }
    }

    public static void returnBook(String userId, String bookId, String endTime) {
        User user = UserRepository.getUserFromId(userId);
        Book book = BookRepository.getBookFromId(bookId);
        BookRepository.updateBookReturn(bookId);
        List<BorrowTime> borrowTimes = user.getBorrowTimes();
        for (BorrowTime b : borrowTimes) {
            if (bookId.equals(b.getBook().getId()) && b.getEndTime() == null) {
                if(Long.parseLong(endTime)*1000 < b.getStartTime().getTime()){
                    throw new RuntimeException("Return Time cant be before borrow time");
                }
                System.out.println("Updating User table as book returned");
                b.setEndTime(new Date(Long.parseLong(endTime)));
                break;
            }
        }
        if (waitList.containsKey(bookId)) {
            updateWaitListForRemove(bookId, endTime, book);
        }
    }

    private static void updateWaitListForRemove(String bookId, String endTime, Book book) {
        System.out.println("assigning book to next user");
        Queue<User> userWaitList = waitList.get(bookId);
        User nextUser = userWaitList.poll();
        nextUser.getBorrowTimes().add(new BorrowTime(new Date(Long.parseLong(endTime)), book));
        if (userWaitList.isEmpty()) {
            System.out.println("waitlist empty, removing the queue");
            waitList.remove(bookId);
        }
    }

    public static void showBooks(String userId) {
        User user = UserRepository.getUserFromId(userId);
        for (BorrowTime b : user.getBorrowTimes()) {
            System.out.println("Borrowed book " + b.getBook() + " at " + b.getStartTime());
        }
    }

    public static void getFine(String userId) {
        User user = UserRepository.getUserFromId(userId);
        List<BorrowTime> borrowTimes = user.getBorrowTimes();
        long fine = 0;
        System.out.println("Calculating fine for user " + user.getId());
        for (BorrowTime borrowTime : borrowTimes) {
            if (borrowTime.getEndTime() != null) {
                fine = calculateFineForReturnedBooks(borrowTime);
            } else {
                fine= fine  + calculateFineForUnreturnedBooks(borrowTime);
            }
        }
        System.out.println("User has fine of rps : " + fine);
    }

    private static long calculateFineForUnreturnedBooks(BorrowTime borrowTime) {
        long fine=0;
        long diffInMillies = Math.abs(System.currentTimeMillis() - borrowTime.getStartTime().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (diff > 14) {
            fine = Math.toIntExact(fine + (20 * (diff - 14)));
        }
        System.out.println("Fine for returned book + unreturned books " + fine);
        return fine;
    }

    private static long calculateFineForReturnedBooks(BorrowTime borrowTime) {
        long fine =0;
        long diffInMillies = Math.abs(borrowTime.getEndTime().getTime() - borrowTime.getStartTime().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (diff > 14) {
            fine = Math.toIntExact(fine + (20 * (diff - 14)));
        }
        System.out.println("Fine for returned book " + fine);
        return fine;
    }
}
