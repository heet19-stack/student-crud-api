package com.Project4.Project4.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDTO
{
    @NotBlank
    private String name;
    @Email(message = "please enter proper email format")
    private String email;
    @Min(value = 1,message = "Minimum age should be one!")
    private int age;
}
