package com.yrx.dawdler.api;

import com.yrx.dawdler.service.impl.BookService;
import com.yrx.dawdler.vo.BookVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/book")
public class BookApi {
    @GetMapping("")
    public String getAll(Map<String, Object> map) {
        map.put("list", BookService.getAll());
        System.out.println("查询所有");
        System.out.println(map);
        return "list";
    }

    @GetMapping("/{isbn}")
    public String getBook(Map<String, Object> map, @PathVariable("isbn") String isbn) {
        map.put("book", BookService.getBook(isbn));
        System.out.println("查询一个" + map);
        return "book";
    }

    @DeleteMapping("/{isbn}")
    public String delete(@PathVariable("isbn") String isbn) {
        BookService.delete(isbn);
        System.out.println("删除一个" + isbn);
        return "redirect:/book";
    }

    @PutMapping("")
    public String update(Map<String, Object> map, BookVO book) {
        BookService.update(book);
        System.out.println("修改一个：" + book);
        return "redirect:/book";
    }

    @PostMapping("")
    public String save(Map<String, Object> map, BookVO book) {
        BookService.save(book);
        System.out.println("增加一个：" + book);
        return "redirect:/book";
    }

    @GetMapping("/index")
    public String index() {
        return "book";
    }
}
