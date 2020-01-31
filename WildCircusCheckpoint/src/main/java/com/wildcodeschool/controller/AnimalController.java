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

import com.wildcodeschool.entity.Animal;

import com.wildcodeschool.repository.AnimalRepository;


@Controller
public class AnimalController {
	
	public String urlPhoto = System.getProperty("user.dir") + "/src/main/resources/static";

	@Autowired
	private AnimalRepository animalrepository;


	@GetMapping("/admin/animals")
	public String getAll(Model model) {
		model.addAttribute("animals", animalrepository.findAll());
		return "animals";
	}

	@GetMapping("/admin/animal")
	public String getAnimal(Model model, @RequestParam(required = false) Long id) {

		Animal animal = new Animal();
		if (id != null) {
			Optional<Animal> optionalAnimal = animalrepository.findById(id);
			if (optionalAnimal.isPresent()) {
				animal = optionalAnimal.get();
			}
		}
		model.addAttribute("animal", animal);
		return "animal";
	}

	@PostMapping("/admin/animal")
	public String postAnimal(@ModelAttribute Animal animal, @RequestParam MultipartFile photoByte) {
		try {
			byte[] bytes = photoByte.getBytes();
			Path path = Paths.get(urlPhoto + "/img/photoAnimal/" + photoByte.getOriginalFilename());
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(animal.getId());
		if (animal.getId() != null) {

			File fileToDelete = new File(urlPhoto + animal.getPhoto());

			System.out.println(fileToDelete.toString());
			fileToDelete.delete();
			animalrepository.delete(animal);
		}
		animal.setPhoto("/img/photoAnimal/" + photoByte.getOriginalFilename());

		animalrepository.save(animal);
		return "redirect:/admin/animals";
	}

	@GetMapping("/admin/animal/delete")
	public String deleteAnimal(@RequestParam Long id) {
		Animal animal = animalrepository.getOne(id);
		File fileToDelete = new File(urlPhoto + animal.getPhoto());

		System.out.println(fileToDelete.toString());
		fileToDelete.delete();

		animalrepository.deleteById(id);

		return "redirect:/admin/animals";
	}

}
