package testing.belajar.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {

    // Getter & Setter
    private String name;
    private String email;
    private Integer age;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

}
