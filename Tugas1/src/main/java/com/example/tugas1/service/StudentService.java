package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.StudentModel;
import com.example.tugas1.model.UniversitasModel;

public interface StudentService {
	StudentModel selectStudent (String npm);
	
	ProgramStudiModel selectProdi(String id);

	FakultasModel selectFakultas(String id);
    
    UniversitasModel selectUniv(String id);
    
    List<StudentModel> selectAllStudents ();

    boolean addStudent (StudentModel student);

    void deleteStudent (String npm);
    
    void updateStudent (StudentModel student);
    
    String getLastNPM(String id_prodi);
    
    Integer getMHSByTahunMasuk(String tahun_masuk, String id_prodi);
    
    Integer getMHSLulus(String tahun_masuk, String id_prodi);
}
