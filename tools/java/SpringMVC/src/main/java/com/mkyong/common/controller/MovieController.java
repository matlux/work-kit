package com.mkyong.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mkyong.common.Session;


@Controller
@RequestMapping("/movie")
public class MovieController {
    final private Session session;

    public MovieController(Session session) {
        this.session = session;
    }
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap model) {

        model.addAttribute("movie", name);
        model.addAttribute("session", session.toString());
		return "list";

	}
	
}