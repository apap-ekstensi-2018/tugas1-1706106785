package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.StudentModel;

@Mapper
public interface ProgramStudiMapper {

	@Insert("INSERT INTO program_studi VALUES ("
			+ "#{kodeProdi},"
			+ "#{namaProdi},"
			+ "#{idFakultas})")
	void insertProgramStudi(ProgramStudiModel programStudi);

	@Update("UPDATE program_studi SET "
			+ "kode_prodi=#{kodeProdi},"
			+ "nama_prodi=#{namaProdi},"
			+ "id_fakultas=#{idFakultas}")
	void updateProgramStudi(ProgramStudiModel programStudi);

	@Delete("DELETE FROM program_studi WHERE id=#{id}")
	void deleteProgramStudi(@Param("id") int id);
	
	@Select("select npm, name, gpa from student where npm = #{npm}")
	ProgramStudiModel selectProgramStudi (@Param("npm") String npm);

    @Select("select npm, name, gpa from student")
    List<ProgramStudiModel> selectAllProgramStudi ();
}
