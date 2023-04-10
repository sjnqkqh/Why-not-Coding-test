package com.challenge.self.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.query.Param;

import com.challenge.self.entity.Book;
import com.challenge.self.util.YnCode;

public interface BookRepository extends JpaRepository<Book, Long> {

	ArrayList<Book> findBooksByDeleteYnOrderByUpdatedAtDesc(YnCode deleteYn, Pageable pageable);
}
