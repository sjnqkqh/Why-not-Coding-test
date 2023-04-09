package com.challenge.self.entity;

import com.challenge.self.util.YnCode;

import javax.persistence.*;

@Entity
@Table(name = "TB_LIBRARY")
public class Library extends CommonBaseDateTime{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "library_name", nullable = false)
	private String libraryName;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "use_yn", nullable = false)
	@Enumerated(EnumType.STRING)
	private YnCode useYn;
}
