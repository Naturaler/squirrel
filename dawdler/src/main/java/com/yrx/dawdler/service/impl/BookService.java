package com.yrx.dawdler.service.impl;

import com.yrx.dawdler.vo.BookVO;

import java.util.*;

public class BookService {
    static Map<String, BookVO> map = new HashMap<String, BookVO>();

    //初始数据
    static {
        map.put("123456", new BookVO("123456", "世界是平的", 45, new Date()));
        map.put("123457", new BookVO("123457", "正年的奇迹", 75, new Date()));
        map.put("123458", new BookVO("123458", "浪潮之耟", 69, new Date()));
        map.put("123459", new BookVO("123459", "数学 之美丽", 58, new Date()));
    }

    public static void save(BookVO book) {
        map.put(book.getIsbn(), book);
    }

    public static void update(BookVO book) {
        map.put(book.getIsbn(), book);
    }

    public static void delete(String isbn) {
        map.remove(isbn);
    }

    public static List<BookVO> getAll() {
        return new ArrayList<BookVO>(map.values());
    }

    public static BookVO getBook(String isbn) {
        return map.get(isbn);
    }

}
