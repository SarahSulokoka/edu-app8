package gr.aueb.cf.eduapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessageDTO {
    private String code;
    private String description;

    public ResponseMessageDTO(String code) {
        this.code = code;
        this.description = "";
    }
}
