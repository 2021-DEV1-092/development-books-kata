package com.bnp.developmentbookskata.repository;

import com.bnp.developmentbookskata.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
