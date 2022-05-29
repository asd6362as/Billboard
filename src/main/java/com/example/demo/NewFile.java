package com.example.demo;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public class NewFile {

	public static void NewF(File filepath) {
		filepath.mkdirs();
	}

	public static void deleteAll(File path) {
		if (!path.exists()) {
			return;
		}
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAll(files[i]);
		}
		path.delete();
	}

	public static void uploadfile(MultipartFile[] mulUploadFile, int id, File filePath) throws UnsupportedEncodingException {
		for (int i = 0; i < mulUploadFile.length; i++) {
			String fileName = new String((mulUploadFile[i].getOriginalFilename()).getBytes("utf-8"),"utf-8");
			File file = new File(filePath + "/" + fileName);
			try {
				mulUploadFile[i].transferTo(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void MakeZip(File filedir, int id) throws Exception {
		File sourceFile = new File(filedir + "/" + id);
		File zipFile = new File(sourceFile.getAbsolutePath() + ".zip");
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
		fileToZip(zos, sourceFile, "");
		zos.close();
		fos.close();

	}

	public static void DownloadZip(File file, int id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String fileName = new String((id + ".zip").getBytes("UTF-8"), "ISO_8859_1");
		response.setContentType("text/html;charset=utf-8");
		response.addHeader("Content-Type", "application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		try (InputStream in = new FileInputStream(file)) {
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			
			while ((len = in.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}
			out.flush();
			out.close();
		}
	}

	private static void fileToZip(ZipOutputStream zos, File sourceFile, String path) throws Exception{
		if (sourceFile.isDirectory()){
			path = path + sourceFile.getName()+"/";
			ZipEntry zipEntry = new ZipEntry(path);
			zos.putNextEntry(zipEntry);
			for (File file : sourceFile.listFiles()){
			fileToZip(zos,file,path);
			}
		
		}else {
			ZipEntry zipEntry = new ZipEntry(path + sourceFile.getName());
			zos.putNextEntry(zipEntry);
			
			byte[] bufs = new byte[1024 * 10];
			FileInputStream fis = new FileInputStream(sourceFile);
			BufferedInputStream bis = new BufferedInputStream(fis,1024 * 10);
			int read = 0;
			while ((read = bis.read(bufs, 0 , 1024 * 10)) != -1){
			    zos.write(bufs, 0, read);
			}
			bis.close();
			fis.close();
			}
	}


}
