//package com.example.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.entity.CrossStateLib;
//import com.example.service.LibrariesService;
//
//import javassist.NotFoundException;
//
//@RestController
//@RequestMapping("/libraries")
//public class LibrariesController {
//
//	private LibrariesService librariesService;
//
//	@Autowired
//	public LibrariesController(LibrariesService librariesService) {
//		this.librariesService = librariesService;
//	}
//
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public List<CrossStateLib> getLibraries() throws NotFoundException {
//		return librariesService.getLibraries();
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public CrossStateLib getLibrary(@PathVariable Long id) throws NotFoundException {
//		return librariesService.getLibraryById(id);
//	}
//
//}
