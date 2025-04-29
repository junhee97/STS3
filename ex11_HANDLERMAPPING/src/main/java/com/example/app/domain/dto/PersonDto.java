package com.example.app.domain.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
//@ToString
@Data // 위에 것들 한번에 만들어 주는 명령어
@AllArgsConstructor // 모든 생성자
@NoArgsConstructor // 디폴트 생성자
@Component
@Builder

public class PersonDto {
	private String username;
	private int age;
	private String addr;
}
