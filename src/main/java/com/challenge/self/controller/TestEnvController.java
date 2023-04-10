package com.challenge.self.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.self.dto.enviroment.req.BulkReqDto;
import com.challenge.self.dto.user.req.UserCreateReqDto;
import com.challenge.self.dto.user.resp.LoginIdAvailableRespDto;
import com.challenge.self.entity.Book;
import com.challenge.self.entity.Library;
import com.challenge.self.entity.User;
import com.challenge.self.service.BookService;
import com.challenge.self.service.LibraryService;
import com.challenge.self.service.ReservationService;
import com.challenge.self.service.UserService;
import com.challenge.self.util.StringUtil;
import com.challenge.self.util.TelecomCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestEnvController {

	private final UserService userService;
	private final BookService bookService;
	private final LibraryService libraryService;
	private final ReservationService reservationService;

	@PostMapping("/api/env")
	public LoginIdAvailableRespDto test(){
		return new LoginIdAvailableRespDto(true);
	}

	@PostMapping("/api/env/user/bulk-generate")
	public void createRandomUserBulk() throws Exception {
		// Random user generate
		ArrayList<UserCreateReqDto> dtoList = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			dtoList.add(new UserCreateReqDto(
				StringUtil.getUUID(),
				"test@test.com",
				"testPassword",
				StringUtil.generateRandomNameString("회원"),
				"01000000000",
				TelecomCode.KT,
				"auth"
			));
		}

		userService.createUserBulk(dtoList);
	}

	@PostMapping("/api/env/reservation/bulk-generate")
	public void createRandomReservationBulk() {
		// Random reservation generate
		ArrayList<Book> bookList = bookService.searchBookList();
		ArrayList<User> userList = userService.getRandomUser(50);
		ArrayList<Library> libraryList = libraryService.getAllLibrary();

		for (int i = 0; i < 100; i++) {
			reservationService.createBulkReservation(bookList, libraryList, userList, 10000);
		}

	}

}
