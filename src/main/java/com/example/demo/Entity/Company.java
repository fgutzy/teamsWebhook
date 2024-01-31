package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String name;

    @Transient
    public String currentPrice;

    public String lastPrice;

    public String dateOfLastChange;

    public int minPrice;

    public int maxPrice;

    public String date;

    public Company(String name, int minPrice, int maxPrice, String date) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.date = date;
    }
    public Company(String name, int minPrice, String date) {
        this.name = name;
        this.minPrice = minPrice;
        this.date = date;
    }
}
