package com.challenge.self.repository;

import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.self.entity.Book;
import com.challenge.self.entity.BookReservation;
import com.challenge.self.util.YnCode;

public interface ReservationRepository extends JpaRepository<BookReservation, Long> {

}
