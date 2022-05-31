package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Teacher;
import com.example.service.TeacherService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	private TeacherService teacherService;

	@Autowired
	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Teacher> getTeacher() {
		return teacherService.getTeacher();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Teacher getTeacher(@PathVariable Long id) throws NotFoundException {
		return teacherService.getTeacher(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Teacher saveTeacher(@RequestBody Teacher teacher) throws NotFoundException {
		return teacherService.saveTeacher(teacher);
	}

	@RequestMapping(value = "/teacher/{name}", method = RequestMethod.GET)
	public List<Teacher> getTeacherByCourseName(@PathVariable String name) {
		return teacherService.getTeacherByCourseName(name);
	}
}
