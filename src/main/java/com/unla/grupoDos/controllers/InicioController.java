package com.unla.grupoDos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unla.grupoDos.helpers.ViewRouteHelper;
@Controller
@RequestMapping("/")
public class InicioController {
	
	@GetMapping("/")
	public String index() {
		return ViewRouteHelper.INDEX;
	}	
	
}
