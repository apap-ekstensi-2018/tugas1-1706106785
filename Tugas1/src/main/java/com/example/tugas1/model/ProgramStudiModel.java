package com.example.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramStudiModel {
	
	private String id_prodi;
	private String kode_prodi;
	private String nama_prodi;
	private String id_fakultas;

}
