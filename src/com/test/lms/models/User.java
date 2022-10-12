package com.test.lms.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    String id;

    String name;

    List<BorrowTime> borrowTimes;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowTimes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BorrowTime> getBorrowTimes() {
        return borrowTimes;
    }

    public void setBorrowTimes(List<BorrowTime> borrowTimes) {
        this.borrowTimes = borrowTimes;
    }
}
