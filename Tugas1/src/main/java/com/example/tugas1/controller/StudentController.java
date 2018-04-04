package com.example.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.StudentModel;
import com.example.tugas1.model.UniversitasModel;
import com.example.tugas1.service.StudentService;

import ch.qos.logback.classic.Logger;

@Controller
public class StudentController {
	@Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }
    
    @RequestMapping("/mahasiswa/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "view-all";
    }
    
    @RequestMapping(value = "/mahasiswa", method = RequestMethod.GET)
    public String viewMahasiswa(@RequestParam(value = "npm") String npm,Model model) {
    	
    	StudentModel student = studentDAO.selectStudent(npm);

        if (student != null) {
        	
			model.addAttribute("mahasiswa", student);
			return "View-mahasiswa";
        } else {
            model.addAttribute("npm",npm);
            return "not-found";
        }
    	
    }

    @RequestMapping(value = "/mahasiswa/tambah", method = RequestMethod.GET)
    public String tambahMahasiswa(Model model)
    {
    	model.addAttribute("student", new StudentModel());
        return "form-add";
    }

    @RequestMapping("/mahasiswa/tambah/submit")
    public String addSubmit (Model model, @ModelAttribute("student") StudentModel student)
    {
    	int kode_jalur;
		String jalurMasuk;
				
		if(student.getJalur_masuk().equalsIgnoreCase("UndanganOlimpiade")) {
		
			kode_jalur = 53;
			jalurMasuk = "Undangan Olimpiade";
		}
		else if(student.getJalur_masuk().equalsIgnoreCase("UndanganRegulerSNMPTN"))	
		{
			kode_jalur = 54;
				jalurMasuk = "Undangan Reguler / SNMPTN";
		}
		else if(student.getJalur_masuk().equalsIgnoreCase("UndanganParalelPPKB"))
		{
			kode_jalur = 55;
			jalurMasuk = "Undangan Paralel / PPKB";
		}
		
		else if(student.getJalur_masuk().equalsIgnoreCase("UjianTulisBersamaSBMPTN"))
		{
			kode_jalur = 57;
			jalurMasuk = "Ujian Tulis Bersama / SBMPTN";
		}
		else if(student.getJalur_masuk().equalsIgnoreCase("UjianTulisMandiri"))
		{
			kode_jalur = 62;
			jalurMasuk = "Ujian Tulis Mandiri";
		}
		else
		{
			kode_jalur = 0;
			jalurMasuk = "";
		}
		
		ProgramStudiModel prodi = studentDAO.selectProdi(student.getId_prodi());
		FakultasModel fakultas = studentDAO.selectFakultas(prodi.getId_fakultas());
		UniversitasModel univ = studentDAO.selectUniv(fakultas.getId_univ());
		String lastNPM = studentDAO.getLastNPM(student.getId_prodi());
		
		String tahunMasuk = student.getTahun_masuk().substring(2, student.getTahun_masuk().length());
		String kode_univ= univ.getKode_univ();
		String kode_prodi = prodi.getKode_prodi();
		String LastNPM = lastNPM.substring(9, lastNPM.length()) + 1;
		String NPM = tahunMasuk+kode_univ+kode_prodi+LastNPM;
		student.setNpm(NPM);
		student.setStatus("Aktif");
		student.setJalur_masuk(jalurMasuk);
		
		if(studentDAO.addStudent(student))
		{
			model.addAttribute("student",student);
			return "success-add";
		}
		else
		{
			return "form-add";
		}

        
    }
    
    @RequestMapping(value = "/mahasiswa/ubah/{npm}")
	public String ubahMahasiswa(Model model, @PathVariable(value = "npm") String npm) {
    	StudentModel student = studentDAO.selectStudent(npm);
    	if(student != null) {
    		model.addAttribute ("student", student);
			return "form-update";
		}
    	else
    	{
    		return "not-found";
    	}
        
	}

	@RequestMapping(value = "/mahasiswa/ubah/submit", method = RequestMethod.POST)
	public String updateMahasiswaSubmit(@ModelAttribute StudentModel student, Model model)
	{
		studentDAO.updateStudent(student);
		return "success-update";
	}
	
	@RequestMapping("/kelulusan")
    public String kelulusan (Model model)
    {
		return"view-kelulusan";
    }
	
	@RequestMapping("/kelulusan/submit")
    public String viewKelulusan (Model model,
    @RequestParam(value = "tahun_masuk", required=false) String tahun_masuk, @RequestParam(value = "id_prodi", required=false)String id_prodi )
    {
		
        int total_mhs= studentDAO.getMHSByTahunMasuk(tahun_masuk, id_prodi);
        
        int mhs_lulus = studentDAO.getMHSLulus(tahun_masuk, id_prodi);
        
        System.out.println(mhs_lulus);
        double persen_lulus = ((double) mhs_lulus / (double) total_mhs) * 100;
             
        String persentase = Double.toString(persen_lulus);
             
        ProgramStudiModel program_studi = studentDAO.selectProdi(id_prodi);
        FakultasModel fakultas = studentDAO.selectFakultas(program_studi.getId_fakultas());
        UniversitasModel universitas = studentDAO.selectUniv(fakultas.getId_univ());
             
         if(program_studi != null && fakultas != null && universitas != null)
         {
             model.addAttribute("total_mhs", total_mhs);
             model.addAttribute("mhs_lulus", mhs_lulus);
             model.addAttribute("presentase", persentase);
             model.addAttribute("tahun_masuk", tahun_masuk);
             model.addAttribute("program_studi", program_studi.getNama_prodi());
             model.addAttribute("fakultas", fakultas.getNama_fakultas());
             model.addAttribute("universitas", universitas.getNama_univ());

             return "persentase_kelulusan";
        }
        else
        {
        	return "not-found";
        }
    }
    
   
}
