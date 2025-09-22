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
@Table(name = "user_visit")
public class UserVisitModel extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 10)
    private String userId;

    @Column(nullable = false, length = 50)
    private String name;

    // Constructor kosong (dibutuhkan JPA)
    public UserVisitModel() {
    }

    // Constructor penuh
    public UserVisitModel(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

}
