package models.negative_response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NegativeResponseForAllModels {

    private Integer errorCode;
    private String errorMessage;
    private String errorDetails;
}
