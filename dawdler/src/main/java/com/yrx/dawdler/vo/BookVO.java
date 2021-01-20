package com.yrx.dawdler.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BookVO {
    String isbn;
    String name;
    Integer price;
    Date publishDate;

    public BookVO(String isbn, String name, Integer price, Date publishDate) {
        this.isbn = isbn;
        this.name = name;
        this.price = price;
        this.publishDate = publishDate;
    }
}
