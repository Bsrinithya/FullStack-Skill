package com.klu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i;

    private String n;
    private String c;
    private double p;

    public Product() {}

    public Product(Long i, String n, String c, double p) {
        this.i = i;
        this.n = n;
        this.c = c;
        this.p = p;
    }

    public Long getI() { return i; }
    public void setI(Long i) { this.i = i; }

    public String getN() { return n; }
    public void setN(String n) { this.n = n; }

    public String getC() { return c; }
    public void setC(String c) { this.c = c; }

    public double getP() { return p; }
    public void setP(double p) { this.p = p; }
}