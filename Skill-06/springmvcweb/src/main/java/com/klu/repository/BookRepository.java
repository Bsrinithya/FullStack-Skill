package com.klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}