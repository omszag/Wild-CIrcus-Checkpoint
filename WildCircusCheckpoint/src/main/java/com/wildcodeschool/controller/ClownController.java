package com.wildcodeschool.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wildcodeschool.entity.Clown;
import com.wildcodeschool.repository.ClownRepository;


@Controller
public class ClownController {
	
	public String urlPhoto = System.getProperty("user.dir") + "/src/main/resources/static";

	@Autowired
	private ClownRepository clownrepository;


	@GetMapping("/admin/clowns")
	public String getAll(Model model) {
		model.addAttribute("clowns", clownrepository.findAll());
		return "clowns";
	}

	@GetMapping("/admin/clown")
	public String getClown(Model model, @RequestParam(required = false) Long id) {

		Clown clown = new Clown();
		if (id != null) {
			Optional<Clown> optionalClown = clownrepository.findById(id);
			if (optionalClown.isPresent()) {
				clown = optionalClown.get();
			}
		}
		model.addAttribute("clown", clown);
		return "clown";
	}

	@PostMapping("/admin/clown")
	public String postClown(@ModelAttribute Clown clown, @RequestParam MultipartFile photoByte) {
		try {
			byte[] bytes = photoByte.getBytes();
			Path path = Paths.get(urlPhoto + "/img/photoClown/" + photoByte.getOriginalFilename());
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(clown.getId());
		if (clown.getId() != null) {

			File fileToDelete = new File(urlPhoto + clown.getPhoto());

			System.out.println(fileToDelete.toString());
			fileToDelete.delete();
			clownrepository.delete(clown);
		}
		clown.setPhoto("/img/photoClown/" + photoByte.getOriginalFilename());

		clownrepository.save(clown);
		return "redirect:/admin/clowns";
	}

	@GetMapping("/admin/clown/delete")
	public String deleteClown(@RequestParam Long id) {
		Clown clown = clownrepository.getOne(id);
		File fileToDelete = new File(urlPhoto + clown.getPhoto());

		System.out.println(fileToDelete.toString());
		fileToDelete.delete();

		clownrepository.deleteById(id);

		return "redirect:/admin/clowns";
	}

}
