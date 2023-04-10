package com.challenge.self.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

import com.challenge.self.util.YnCode;

import lombok.Getter;

@Entity
@Getter
@Table(name = "TB_BOOK")
public class Book extends CommonBaseDateTime{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "publisher_name")
	private String publisherName;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "book_information")
	private String bookInformation;

	@Column(name = "book_cover_image")
	private String bookCoverImage;

	@Column(name = "delete_yn")
	@Enumerated(EnumType.STRING)
	private YnCode deleteYn;
}