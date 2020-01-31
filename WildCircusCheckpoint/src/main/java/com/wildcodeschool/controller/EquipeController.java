package com.wildcodeschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wildcodeschool.repository.AnimalRepository;
import com.wildcodeschool.repository.ClownRepository;
import com.wildcodeschool.repository.MagicienRepository;

@Controller
public class EquipeController {
	
	@Autowired
	private MagicienRepository magicienrepository;
	@Autowired
	private ClownRepository clownrepository;
	@Autowired
	private AnimalRepository animalrepository;
	
	@GetMapping("/equipe")
	public String equipe(Model model) {

		model.addAttribute("clowns", clownrepository.findAll());
		model.addAttribute("magiciens", magicienrepository.findAll());
		model.addAttribute("animals", animalrepository.findAll());
		return "equipe";
	}

}
