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

@Controller
public class StudentController {
	@Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
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
    
    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent(npm);
    	if(student == null)
    	{
    		model.addAttribute("npm",npm);
    		return "not-found";
    	}
    	studentDAO.deleteStudent (npm);
      
        return "delete";
    }

    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent(npm);
    	if(student == null)
    	{
    		model.addAttribute("npm",npm);
    		return "not-found";
    	}
    	model.addAttribute ("student", student);      
        return "form-update";   
	}

	@RequestMapping(value = "/student/update/submit", method = RequestMethod.POST )
	public String updateSubmit(@ModelAttribute StudentModel student)
	{
		studentDAO.updateStudent (student);
	  
	    return "success-update";   }
}
