package com.chiguru.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChiguruController {

	@RequestMapping("/chiguru_playhome")
	@ResponseBody
	public String getMovie(ModelMap model) {
		return "Merry Christmas from CHIGURU PLAYHOME!!";
	}
	
	
}