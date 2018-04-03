package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.StudentModel;
import com.example.tugas1.model.UniversitasModel;

@Mapper
public interface StudentMapper {
		@Select("select mahasiswa.id,npm,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,agama,golongan_darah,status,tahun_masuk,jalur_masuk,nama_prodi,nama_univ,nama_fakultas from mahasiswa join program_studi on mahasiswa.id_prodi = program_studi.id join fakultas on program_studi.id_fakultas = fakultas.id join universitas on universitas.id = fakultas.id_univ where npm = #{npm}")
		StudentModel selectStudent (@Param("npm") String npm);

	    @Select("select id,npm,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,agama,golongan_darah,status,tahun_masuk,jalur_masuk,id_prodi from mahasiswa")
	    List<StudentModel> selectAllStudents ();

	    @Insert("INSERT INTO mahasiswa (id,npm,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,agama,golongan_darah,status,tahun_masuk,jalur_masuk,id_prodi) VALUES (#{id}, #{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{agama}, #{golongan_darah}, #{status}, #{tahun_masuk}, #{jalur_masuk}, #{id_prodi})")
	    boolean addStudent (StudentModel student);
	    
	    @Delete("DELETE FROM mahasiswa WHERE npm = #{npm}")
	    void deleteStudent(@Param("npm") String npm);
	    
	    @Update("UPDATE mahasiswa SET id = #{id},npm = #{npm},nama = #{nama},tempat_lahir =  #{tempat_lahir},tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin},agama =  #{agama},golongan_darah =  #{golongan_darah},status =  #{status},tahun_masuk = #{tahun_masuk},jalur_masuk = #{jalur_masuk},id_prodi = #{id_prodi} WHERE npm = #{npm}")
	    void updateStudent(StudentModel student);
	    
	    @Select("select kode_prodi, nama_prodi, id_fakultas from program_studi where id = #{id}")
	    ProgramStudiModel selectProdi (@Param("id") String id);
	    
	    @Select("select nama_fakultas, id_univ from fakultas where id = #{id}")
	    FakultasModel selectFakultas(@Param("id") String id);
	    
	    @Select("select kode_univ, nama_univ from universitas where id = #{id}")
	    UniversitasModel selectUniv(@Param("id") String id);
	    
	    @Select("select * from universitas")
	    List<UniversitasModel> selectAllUniv();
	    
	    @Select("SELECT npm FROM mahasiswa, program_studi, fakultas, universitas " + 
	    		"WHERE mahasiswa.id_prodi = #{id_prodi} AND mahasiswa.id_prodi = program_studi.id AND program_studi.id_fakultas = fakultas.id " + 
	    		"AND fakultas.id_univ = universitas.id " + 
	    		"ORDER BY mahasiswa.npm DESC LIMIT 1")
	    String getLastNPM(@Param("id_prodi") String id_prodi);
}
