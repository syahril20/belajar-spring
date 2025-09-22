package testing.belajar.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({"code", "status", "message"})
public class ApiResponse {

    // Getter & Setter
    private int code;
    private String status;
    private Object message;

    public ApiResponse(int code, String status, Object message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}