package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Teacher;
import com.example.repo.TeacherRepository;

import javassist.NotFoundException;

@Service
public class TeacherService {

	private TeacherRepository teacherRepository;

	@Autowired
	public TeacherService(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	public List<Teacher> getTeacher() {
		return this.teacherRepository.findAll();
	}

	public Teacher getTeacher(Long id) throws NotFoundException {
		Optional<Teacher> teacher = teacherRepository.findById(id);
		if (!teacher.isPresent()) {
			throw new NotFoundException("Teacher with id = " + id + " not found");
		}
		System.out.println("--------------------------->>>>>>>>>>>>>>>>>>>>.-----------------------------");
		return teacher.get();
	}

	public Teacher saveTeacher(Teacher teacher) throws NotFoundException {
		if (teacher.getId() != null) {
			final Teacher teacherDb = getTeacher(teacher.getId());
			teacherDb.setName(teacher.getName());
			if (teacher.getCourses() != null && !teacher.getCourses().isEmpty()) {
				teacherDb.setCourses(teacher.getCourses());
				teacherDb.getCourses().stream().forEach(c -> c.setTeacher(teacherDb));
			}
			return teacherRepository.save(teacherDb);
		}
		final Teacher teacherdb = teacher;
		if (teacher.getCourses() != null && !teacher.getCourses().isEmpty()) {
			teacherdb.setCourses(teacher.getCourses());
			teacherdb.getCourses().stream().forEach(c -> c.setTeacher(teacherdb));
		}
		teacher = teacherRepository.save(teacher);
		return teacher;
	}

	public List<Teacher> getTeacherByCourseName(String courseName) {
		return this.teacherRepository.getTeacherByCourseName(courseName);
	}
}
