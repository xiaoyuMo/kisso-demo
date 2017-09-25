package com.zhaohg.kisso.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhaohg.kisso.oauth2.entity.Client;
import com.zhaohg.kisso.oauth2.service.ClientService;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;


	@RequestMapping(method = RequestMethod.GET)
	public String list( Model model ) {
		model.addAttribute("clientList", clientService.findAll());
		return "client/list";
	}


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm( Model model ) {
		model.addAttribute("client", new Client());
		return "client/add";
	}


	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create( Client client, RedirectAttributes redirectAttributes ) {
		clientService.createClient(client);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/client";
	}


	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm( @PathVariable("id" ) Long id, Model model) {
		model.addAttribute("client", clientService.findOne(id));
		return "client/edit";
	}


	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update( Client client, RedirectAttributes redirectAttributes ) {
		clientService.updateClient(client);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/client";
	}


	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete( @PathVariable("id" ) Long id, RedirectAttributes redirectAttributes) {
		clientService.deleteClient(id);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/client";
	}

}
