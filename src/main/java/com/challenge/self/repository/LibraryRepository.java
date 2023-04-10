package com.challenge.self.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.self.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
