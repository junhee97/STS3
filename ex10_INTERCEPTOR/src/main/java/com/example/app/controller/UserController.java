package com.example.app.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j

public class UserController {

	@InitBinder
	public void dataBinder(WebDataBinder webDataBinder) {
		log.info("WebDataBinder's invoke.. " + webDataBinder);
		webDataBinder.registerCustomEditor(LocalDate.class, "birthday", new BirthdayEditor());
		webDataBinder.registerCustomEditor(String.class, "phone", new PhoneEditor());
	}

	@GetMapping("/join")
	public void join() {
		log.info("GET /join..");
	}

	@PostMapping("/join")
	public void join_post(@Valid UserDto dto, BindingResult bindingResult, Model model) {
		log.info("POST /join.." + dto);

		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				log.info("Error Field : " + error.getField() + "Error Msg : " + error.getDefaultMessage());
				model.addAttribute(error.getField(), error.getDefaultMessage());
			}
		}
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
