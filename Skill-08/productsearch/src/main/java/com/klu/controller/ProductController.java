package com.klu.controller;

import com.klu.model.Product;
import com.klu.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository r;

    public ProductController(ProductRepository r) {
        this.r = r;
    }

    @GetMapping("/category/{c}")
    public List<Product> a(@PathVariable String c) {
        return r.findByC(c);
    }

    @GetMapping("/category/{c}/price/{p}")
    public List<Product> b(@PathVariable String c, @PathVariable double p) {
        return r.findByCAndPGreaterThan(c, p);
    }

    @GetMapping("/or")
    public List<Product> d(@RequestParam String c, @RequestParam String n) {
        return r.findByCOrN(c, n);
    }

    @GetMapping("/filter")
    public List<Product> e(@RequestParam double a, @RequestParam double b) {
        return r.findByPBetween(a, b);
    }

    @GetMapping("/search")
    public List<Product> f(@RequestParam String k) {
        return r.findByNLike("%" + k + "%");
    }

    @GetMapping("/expensive/{p}")
    public List<Product> g(@PathVariable double p) {
        return r.findByPGreaterThan(p);
    }

    @GetMapping("/count/{c}")
    public long h(@PathVariable String c) {
        return r.countByC(c);
    }

    @GetMapping("/exists/{n}")
    public boolean i(@PathVariable String n) {
        return r.existsByN(n);
    }

    @DeleteMapping("/delete/{n}")
    public String j(@PathVariable String n) {
        r.deleteByN(n);
        return "deleted";
    }

    @GetMapping("/sorted")
    public List<Product> k() {
        return r.sortByP();
    }
    @PostMapping("/add")
public Product m(@RequestBody Product p) {
    return r.save(p);
}
}