package com.kailar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/mailme")
public class SpringController {
	
	@RequestMapping(value="/{emailId}", method = RequestMethod.GET)
	public String getMailDetails(@PathVariable String emailId, ModelMap model) {
 
		model.addAttribute("email", emailId);
 
		//return to jsp page, configured in mvc-dispatcher-servlet.xml, view resolver
		return "list";
 
	}
}
