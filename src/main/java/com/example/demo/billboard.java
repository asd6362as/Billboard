package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class billboard {

	@Autowired
	BdataRespository bdataRespository;

	@GetMapping("/index")
	public String BillboardIndex(Model model) {
		bdataRespository.deleteByedate(today.get());
		model.addAttribute("bdata", bdataRespository.findAll());
		return "index";
	}

	@GetMapping(value = "/{id}")
	public String detail(@PathVariable(value = "id") Integer id, Model model) {
		bdata Detailbdata = bdataRespository.findById(id).get();
		ArrayList<String> Filenamelist = new ArrayList<String>(
				Arrays.asList(Detailbdata.getAllFileName().split("\\?")));
		Filenamelist.remove(0);
		Filenamelist.add("全部下載");
		model.addAttribute("Filenamelist", Filenamelist);
		model.addAttribute("Detailbdata", Detailbdata);
		return "detail";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		bdata newbdata = new bdata();
		model.addAttribute("newbdata", newbdata);
		return "add";
	}
	
	@GetMapping(value = "/edit{id}")
	public String edit(@PathVariable(value = "id") Integer id, Model model) {
		bdata editbdata = bdataRespository.findById(id).get();
		model.addAttribute("editbdata", editbdata);
		return "edit";
	}

	@GetMapping(value = "/delete{id}")
	public String delete(@PathVariable(value = "id") Integer id, Model model) {
		bdata Deletebdata = bdataRespository.findById(id).get();
		NewFile.deleteAll(new File("/File/" + id));
		bdataRespository.deleteById(id);
		model.addAttribute("Deletebdata", Deletebdata);
		return "delete";
	}

	@PostMapping("/success")
	public String success(@ModelAttribute bdata newbdata, MultipartFile[] mulUploadFile, Model model) throws UnsupportedEncodingException {
		newbdata = NewFile.FileNameSet(mulUploadFile, newbdata);
		bdataRespository.save(newbdata);
		File filedir = new File("/File/" + newbdata.getId());
		NewFile.deleteAll(filedir);
		NewFile.NewF(filedir);
		NewFile.uploadfile(mulUploadFile, newbdata.getId(), filedir);
		model.addAttribute("newbdata", newbdata);
		return "success";
	}

	@GetMapping(value = "/download{id}/{FileName}")
	public void download(@PathVariable("id") Integer id, @PathVariable(value = "FileName") String FileName,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileDir = "/File/";
		if (FileName.equals("全部下載") ) {
			File file = new File(fileDir + id + ".zip");
			NewFile.Download(NewFile.MakeZip(new File(fileDir), id), file, request, response);
			file.delete();

		} else {
			File file = new File(fileDir + id + "/" + FileName);
			NewFile.Download(FileName, file, request, response);
		}
	}


	

}