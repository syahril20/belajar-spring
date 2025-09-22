package testing.belajar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateVisitRequest {

    // Getter & Setter
    @JsonProperty("userId")
    private String userId;

    private String name;

    public CreateVisitRequest() {
    }

    public CreateVisitRequest(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

}
