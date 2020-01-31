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

import com.wildcodeschool.entity.Magicien;
import com.wildcodeschool.repository.MagicienRepository;



@Controller
public class MagicienController {
	
	public String urlPhoto = System.getProperty("user.dir") + "/src/main/resources/static";

	@Autowired
	private MagicienRepository magicienrepository;


	@GetMapping("/admin/magiciens")
	public String getAll(Model model) {
		model.addAttribute("magiciens", magicienrepository.findAll());
		return "magiciens";
	}

	@GetMapping("/admin/magicien")
	public String getMagicien(Model model, @RequestParam(required = false) Long id) {

		Magicien magicien = new Magicien();
		if (id != null) {
			Optional<Magicien> optionalMagicien = magicienrepository.findById(id);
			if (optionalMagicien.isPresent()) {
				magicien = optionalMagicien.get();
			}
		}
		model.addAttribute("magicien", magicien);
		return "magicien";
	}

	@PostMapping("/admin/magicien")
	public String postMagicien(@ModelAttribute Magicien magicien, @RequestParam MultipartFile photoByte) {
		try {
			byte[] bytes = photoByte.getBytes();
			Path path = Paths.get(urlPhoto + "/img/photoMagicien/" + photoByte.getOriginalFilename());
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(magicien.getId());
		if (magicien.getId() != null) {

			File fileToDelete = new File(urlPhoto + magicien.getPhoto());

			System.out.println(fileToDelete.toString());
			fileToDelete.delete();
			magicienrepository.delete(magicien);
		}
		magicien.setPhoto("/img/photoMagicien/" + photoByte.getOriginalFilename());

		magicienrepository.save(magicien);
		return "redirect:/admin/magiciens";
	}

	@GetMapping("/admin/magicien/delete")
	public String deleteMagicien(@RequestParam Long id) {
		Magicien magicien = magicienrepository.getOne(id);
		File fileToDelete = new File(urlPhoto + magicien.getPhoto());

		System.out.println(fileToDelete.toString());
		fileToDelete.delete();

		magicienrepository.deleteById(id);

		return "redirect:/admin/magiciens";
	}

}
