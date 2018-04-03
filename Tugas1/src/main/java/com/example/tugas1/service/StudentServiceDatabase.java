package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.StudentMapper;
import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.StudentModel;
import com.example.tugas1.model.UniversitasModel;
import com.example.tugas1.service.StudentServiceDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService {
	@Autowired
    private StudentMapper studentMapper;

	@Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }

    @Override
    public List<StudentModel> selectAllStudents ()
    {
        log.info ("select all students");
        return studentMapper.selectAllStudents ();
    }

    @Override
    public boolean addStudent (StudentModel student)
    {
       return studentMapper.addStudent (student);
    }

    @Override
    public void deleteStudent (String npm)
    {
    	studentMapper.deleteStudent(npm);
    	log.info("student"+npm+"deleted");
    }
    
    @Override
    public void updateStudent (StudentModel student)
    {
    	studentMapper.updateStudent(student);
    	log.info("student"+student+"updated");
    }

	@Override
	public ProgramStudiModel selectProdi(String id) {
		// TODO Auto-generated method stub
		return studentMapper.selectProdi(id);
	}

	@Override
	public FakultasModel selectFakultas(String id) {
		// TODO Auto-generated method stub
		return studentMapper.selectFakultas(id);
	}

	@Override
	public UniversitasModel selectUniv(String id) {
		// TODO Auto-generated method stub
		return studentMapper.selectUniv(id);
	}

	@Override
	public String getLastNPM(String id_prodi) {
		// TODO Auto-generated method stub
		return studentMapper.getLastNPM(id_prodi);
	}

   
}
