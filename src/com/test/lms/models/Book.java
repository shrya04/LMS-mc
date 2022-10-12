package com.test.lms.models;

public class Book {
    String id;

    String author;

    String name;

    static int idGenerator = 1000;


    public Book(String name, String author) {
        this.author = author;
        this.name = name;
        this.id = getUniqueId(author);
    }

    private String getUniqueId(String author) {
        String[] name = author.split(" ");
        if(name[1].length()<3){
            return name[1] + idGenerator++;
        }
        return name[1].substring(0,3) + idGenerator++;
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
}
