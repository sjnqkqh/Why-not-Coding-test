package com.challenge.self.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.challenge.self.util.YnCode;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_BOOK_RESERVATION")
public class BookReservation extends CommonBaseDateTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library_id")
	private Library library;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "expired_at", nullable = false)
	private LocalDateTime expiredAt;

	@Column(name = "expired_yn", nullable = false)
	@Enumerated(EnumType.STRING)
	private YnCode expiredYn;

	@Column(name = "expired_reason")
	private String expiredReason;

	@Column(name = "delete_yn", nullable = false)
	@Enumerated(EnumType.STRING)
	private YnCode deleteYn;

	@Builder
	public BookReservation(Long id, Library library, Book book, User user, LocalDateTime expiredAt,
		YnCode expiredYn, String expiredReason, YnCode deleteYn) {
		this.id = id;
		this.library = library;
		this.book = book;
		this.user = user;
		this.expiredAt = expiredAt;
		this.expiredYn = expiredYn;
		this.expiredReason = expiredReason;
		this.deleteYn = deleteYn;
	}
}