package com.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.Student;
import com.example.backend.repositories.StudentRepository;

@RestController
@RequestMapping("/api/")
public class StudentController {

	@Autowired
	private StudentRepository studentRepo;

	@GetMapping("/allstudents")
	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
		Student student = studentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found."));
		return ResponseEntity.ok(student);
	}

	@GetMapping("student/{name}")
	public List<Student> getStudentByName(@PathVariable String name) {
		List<Student> students = studentRepo.findByName(name);
		if (students.isEmpty()) {
			System.out.println(new ResourceNotFoundException("Student(s) with the name " + name + " not found."));
		}
		return studentRepo.findByName(name);
	}

	@PostMapping("/addstudent")
	public Student newStudent(@RequestBody Student student) {
		return studentRepo.save(student);
	}

	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
		Student foundStudent = studentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found."));
		student.setName(student.getName());
		student.setGrade(student.getGrade());
		Student updatedStudent = studentRepo.save(foundStudent);
		return ResponseEntity.ok(updatedStudent);
	}

	@DeleteMapping("/student/{id}")
	public String deleteStudent(@PathVariable int id) {
		studentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found."));
		studentRepo.deleteById(id);
		return "The student with the id: " + id + " has been removed.";
	}
}

