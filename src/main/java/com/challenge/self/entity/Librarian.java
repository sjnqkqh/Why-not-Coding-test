package com.challenge.self.entity;

import com.challenge.self.util.YnCode;

import javax.persistence.*;
@Entity
@Table(name = "TB_LIBRARIAN")
public class Librarian{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "email", nullable = false)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library_id")
	private Library library;

	@Column(name = "delete_yn")
	@Enumerated(EnumType.STRING)
	private YnCode deleteYn;

}