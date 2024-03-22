package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.entity.Student;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String contact;

    // 365*1000*24*60*60

    public Student to() {
        return Student.builder()
                .name(name)
                .phoneNumber(contact)
                .validity(new Date(System.currentTimeMillis()+31536000000l))
                .build();
    }
}
