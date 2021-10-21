package com.example.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{

	List<Student> findByName(String name);
}
