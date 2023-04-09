package com.challenge.self.entity;

import com.challenge.self.util.YnCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_BOOK_RESERVATION")
public class BookReservation extends CommonBaseDateTime{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library_id")
	private Library library;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;

	@Column(name = "expired_at", nullable = false)
	private LocalDateTime expiredAt;

	@Column(name = "expired_yn", nullable = false)
	@Enumerated(EnumType.STRING)
	private YnCode expiredYn;

	@Column(name = "expired_reason")
	private String expiredReason;

	@Column(name = "use_yn", nullable = false)
	@Enumerated(EnumType.STRING)
	private YnCode useYn;

}