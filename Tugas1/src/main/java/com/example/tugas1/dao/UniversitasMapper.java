package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.UniversitasModel;

@Mapper
public interface UniversitasMapper {

	@Select("select id_univ, kode_univ, nama_univ from Universitas where id_univ = #{id_univ}")
	UniversitasModel selectUniversitas(@Param("id_univ") String id_univ);

    @Select("select id_univ, kode_univ, nama_univ from Universitas")
    List<UniversitasModel> selectAllUniversitas ();

    @Insert("INSERT INTO Universitas(id_univ, kode_univ, nama_univ) VALUES (#{id_univ}, #{kode_univ}, #{nama_univ})")
    void addUniversitas(UniversitasModel universitas);
    
    @Delete("DELETE FROM Universitas WHERE id_univ = #{id_univ}")
    void deleteUniversitas(@Param("id_univ") String id_univ);
    
    @Update("UPDATE Universitas SET id_univ = #{id_univ},kode_univ = #{kode_univ}, nama_univ = #{nama_univ} WHERE id_fakultas = #{id_fakultas}")
    void updateUniversitas(UniversitasModel universitas);
}
