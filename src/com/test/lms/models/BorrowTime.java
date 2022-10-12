package com.test.lms.models;

import java.util.Date;

public class BorrowTime {
    Book book;

    Date startTime;

    Date endTime;

    public BorrowTime(Date date) {
        this.startTime = date;
    }

    public BorrowTime(Date date, Book book) {
        this.startTime = date;
        this.book = book;
    }

    public BorrowTime(Book book, Date startTime, Date endTime) {
        this.book = book;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
