package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.FakultasModel;

@Mapper
public interface FakultasMapper {

	@Select("select id_fakultas, kode_fakultas, nama_fakultas,id_univ from Fakultas where id_fakultas = #{id_fakultas}")
	FakultasModel selectFakultas(@Param("id_fakultas") String id_fakultas);

    @Select("select id_fakultas, kode_fakultas, nama_fakultas,id_univ from Fakultas")
    List<FakultasModel> selectAllFakultas ();

    @Insert("INSERT INTO Fakultas(id_fakultas, kode_fakultas, nama_fakultas,id_univ) VALUES (#{id_fakultas}, #{kode_fakultas}, #{nama_fakultas}, #{id_univ})")
    void addFakultas(FakultasModel fakultas);
    
    @Delete("DELETE FROM Fakultas WHERE id_fakultas = #{id_fakultas}")
    void deleteFakultas(@Param("id_fakultas") String id_fakultas);
    
    @Update("UPDATE Fakultas SET id_fakultas = #{id_fakultas},kode_fakultas = #{kode_fakultas}, nama_fakultas = #{nama_fakultas}, id_univ = #{id_univ} WHERE id_fakultas = #{id_fakultas}")
    void updateFakultas(FakultasModel fakultas);
    
}
