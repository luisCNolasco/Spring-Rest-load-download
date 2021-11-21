package com.demomultiplefileupload.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileServiceAPI {

	private final Path rootFolder = Paths.get("uploads");

	@Override
	public String save(MultipartFile file) throws Exception {
		Files.copy(file.getInputStream(), this.rootFolder.resolve(file.getOriginalFilename()));

		return file.getOriginalFilename();

		/*
		 * try { byte[] bytes = file.getBytes();
		 * 
		 * Path path = this.rootFolder.resolve(file.getOriginalFilename());
		 * 
		 * Files.write(path, bytes);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return
		 * file.getOriginalFilename();
		 */
	}

	@Override
	public Resource load(String fileName) throws Exception {
		try {
			Path filePath = this.rootFolder.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				System.out.println("Archivo no existe");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(List<MultipartFile> files) throws Exception {
		for (MultipartFile file : files) {
			this.save(file);
		}

	}

	@Override
	public Stream<Path> loadAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
