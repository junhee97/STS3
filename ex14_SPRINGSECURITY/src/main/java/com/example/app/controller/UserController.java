package com.example.app.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.dto.UserDto;
import com.example.app.domain.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j

public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@InitBinder
	public void dataBinder(WebDataBinder webDataBinder) {
		log.info("WebDataBinder's invoke.. " + webDataBinder);
		webDataBinder.registerCustomEditor(LocalDate.class, "birthday", new BirthdayEditor());
		webDataBinder.registerCustomEditor(String.class, "phone", new PhoneEditor());
	}

	@GetMapping("/login")
	public void login() {
		log.info("GET /login");
	}

//	@GetMapping("/user")
//	public void user(Authentication authentication) {
//		log.info("GET /user" + authentication);
//		log.info("name : " + authentication.getName());
//		log.info("principal : " + authentication.getPrincipal());
//		log.info("authorities : " + authentication.getAuthorities());
//		log.info("details : " + authentication.getDetails());
//		log.info("credential : " + authentication.getCredentials());
//	}

//	@GetMapping("/user")
//	public void user(@AuthenticationPrincipal Principal principal) {
//		log.info("GET /user " + principal);
//	}

	@GetMapping("/user")
	public void user(Model model) {
		log.info("GET /user ");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("authentication : " + authentication);
		model.addAttribute("auth", authentication);
	}

	@GetMapping("/manager")
	public void manager() {
		log.info("GET /manager ");
	}

	@GetMapping("/admin")
	public void admin() {
		log.info("GET /admin ");
	}

	@GetMapping("/join")
	public void join() {
		log.info("GET /join..");
	}

	@PostMapping("/join")
	public String join_post(@Valid UserDto dto, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		log.info("POST /join.." + dto);

		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				log.info("Error Field : " + error.getField() + "Error Msg : " + error.getDefaultMessage());
				model.addAttribute(error.getField(), error.getDefaultMessage());
				return "join";
			}
		}
		boolean isJoin = userService.userJoin(dto);
		if (isJoin) {
			redirectAttributes.addFlashAttribute("message", "회원가입 완료!");
			return "redirect:/login";
		} else
			return "join";
	}

	//
	private static class BirthdayEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String birthday) throws IllegalArgumentException {
			log.info("BirthdayEditor's setAsText invoke.. " + birthday);
			birthday = birthday.replaceAll("~", "-");
			LocalDate date = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			setValue(date);
		}

	}

	private static class PhoneEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String phone) throws IllegalArgumentException {
			log.info("PhoneEditor's setAsText invoke.. " + phone);
			phone = phone.replaceAll("-", "");
			setValue(phone);
		}

	}
}
