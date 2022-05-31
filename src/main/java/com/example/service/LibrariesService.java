//package com.example.service;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.entity.CrossStateLib;
//import com.example.exception.NotFoundException;
//import com.example.repo.LibrariesRepository;
//
//@Service
//public class LibrariesService {
//
//	private LibrariesRepository librariesRepository;
//
//	@Autowired
//	public LibrariesService(LibrariesRepository librariesRepository) {
//		this.librariesRepository = librariesRepository;
//	}
//
//	public List<CrossStateLib> getLibraries() {
//		return this.librariesRepository.findAll();
//	}
//
//	public CrossStateLib getLibraryById(Long id) {
//		System.out.println(id);
//		Optional<CrossStateLib> emp = this.librariesRepository.findById(id);
//		if (Objects.isNull(emp) || !emp.isPresent()) {
//			throw new NotFoundException("Library not found");
//		}
//		return emp.get();
//	}
//
//}
