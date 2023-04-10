package com.challenge.self.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.challenge.self.entity.Library;
import com.challenge.self.repository.LibraryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LibraryService {

	private final LibraryRepository libraryRepository;

	public ArrayList<Library> getAllLibrary(){
		return new ArrayList<>(libraryRepository.findAll());
	}

	public void createLibrary(){

	}

}
