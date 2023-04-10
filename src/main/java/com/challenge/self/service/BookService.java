package com.challenge.self.service;

import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.self.entity.Book;
import com.challenge.self.repository.BookRepository;
import com.challenge.self.util.YnCode;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public ArrayList<Book> searchBookList() {
		return new ArrayList(bookRepository.findAll());
	}
}
