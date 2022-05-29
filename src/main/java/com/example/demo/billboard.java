package com.example.demo;

import java.io.*;
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
		bdataRespository.deleteByedate(DeleteEnddata.today());
		model.addAttribute("bdata", bdataRespository.findAll());
		return "index";
	}

	@GetMapping(value = "/{id}")
	public String detail(@PathVariable(value = "id") Integer id, Model model) {
		bdata Detailbdata = bdataRespository.findById(id).get();
		model.addAttribute("Detailbdata", Detailbdata);
		return "detail";
	}

	@GetMapping(value = "/delete{id}")
	public String delete(@PathVariable(value = "id") Integer id, Model model) {
		bdata Deletebdata = bdataRespository.findById(id).get();
		NewFile.deleteAll(new File("/File/" + id));
		bdataRespository.deleteById(id);
		model.addAttribute("Deletebdata", Deletebdata);
		return "delete";
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

	@GetMapping(value = "/download{id}")
	public void download(@PathVariable(value = "id") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		File fileDir = new File("/File/");
		NewFile.MakeZip(fileDir, id);
		File file = new File(fileDir + "/" + id + ".zip");
		NewFile.DownloadZip(file, id, request, response);
		file.delete();
	}

	@PostMapping("/success")
	public String success(@ModelAttribute bdata newbdata, MultipartFile[] mulUploadFile, Model model) throws UnsupportedEncodingException {
		if (mulUploadFile[0].isEmpty()) newbdata.setFilequantity(new String("無附件".getBytes("utf-8"),"utf-8"));
		else newbdata.setFilequantity(new String((mulUploadFile.length+"個附件").getBytes("utf-8"),"utf-8"));
		bdataRespository.save(newbdata);
		File filedir = new File("/File/" + newbdata.getId());
		NewFile.deleteAll(filedir);
		NewFile.NewF(filedir);
		NewFile.uploadfile(mulUploadFile, newbdata.getId(), filedir);
		model.addAttribute("newbdata", newbdata);
		return "success";
	}

}