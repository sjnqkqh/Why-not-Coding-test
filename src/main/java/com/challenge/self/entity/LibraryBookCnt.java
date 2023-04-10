package com.challenge.self.entity;


import com.challenge.self.util.YnCode;

import javax.persistence.*;

@Entity
@Table(name = "TB_LIBRARY_BOOK_CNT")
public class LibraryBookCnt extends CommonBaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "book_cnt", nullable = false)
    private int bookCnt;

    @Column(name = "delete_yn", nullable = false)
    @Enumerated(EnumType.STRING)
    private YnCode deleteYn;

}