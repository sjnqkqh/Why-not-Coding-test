package com.challenge.self.service;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.self.entity.Book;
import com.challenge.self.entity.BookReservation;
import com.challenge.self.entity.Library;
import com.challenge.self.entity.User;
import com.challenge.self.util.YnCode;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {

	private final EntityManager entityManager;

	/**
	 * 테스트용 벌크 데이터 생성 함수
	 *
	 * @param bookList 도서 리스트
	 * @param libraryList 도서관 리스트
	 * @param userList 유저 리스트
	 * @param bulkLimit 총 Bulk 갯수
	 */
	@Transactional
	public void createBulkReservation(ArrayList<Book> bookList, ArrayList<Library> libraryList,
		ArrayList<User> userList, int bulkLimit) {
		ArrayList<BookReservation> reservationList = new ArrayList<>();

		wholeLoop:
		while (true) {
			for (Book book : bookList) {
				for (Library library : libraryList) {
					for (User user : userList) {
						reservationList.add(
							BookReservation.builder()
								.book(book)
								.library(library)
								.user(user)
								.deleteYn(YnCode.N)
								.expiredAt(LocalDateTime.now().plusDays(7))
								.build()
						);
						if (reservationList.size() >= bulkLimit) {
							break wholeLoop;
						}
					}
				}
			}
		}

		entityManager.unwrap(Session.class)
			.doWork(connection -> {
				PreparedStatement ps = connection.prepareStatement("INSERT INTO TB_BOOK_RESERVATION (library_id, book_id, user_id, expired_at) VALUES (?, ?, ?,?)");
				int batchSize = 100000;
				int count = 0;
				for (BookReservation reservation : reservationList) {
					ps.setString(1, String.valueOf(reservation.getLibrary().getId()));
					ps.setString(2, String.valueOf(reservation.getBook().getId()));
					ps.setString(3, String.valueOf(reservation.getUser().getUserId()));
					ps.setString(4, String.valueOf(LocalDateTime.now().plusDays(7)));
					ps.addBatch();
					if (++count % batchSize == 0) {
						ps.executeBatch();
					}
				}
				ps.executeBatch(); // Execute any remaining batches
			});


	}
}
