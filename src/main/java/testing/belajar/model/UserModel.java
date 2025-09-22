package testing.belajar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserModel extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(length = 3)
    private Integer age;

    // Constructor kosong (dibutuhkan JPA)
    public UserModel() {
    }

    // Constructor penuh
    public UserModel(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

}
