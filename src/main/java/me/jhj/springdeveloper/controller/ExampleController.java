package me.jhj.springdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    @ResponseBody
    public String thymeleaf() {
        return "example";
    }

    @GetMapping("/thymeleaf/example2")
    public String thymeleaf2(Model model) {
        Person person = new Person();
        person.setAge(30);
        person.setName("홍길동");
        person.setHobbies(List.of("독서","영화","음악","헬스"));
        person.setBlogId(1L);
        model.addAttribute("person", person);
        model.addAttribute("today", LocalDate.now());
        return "example";
    }
}

@Getter
@Setter
class Person {
    private String name;
    private int age;
    private List<String> hobbies;
    private Long blogId;
}
