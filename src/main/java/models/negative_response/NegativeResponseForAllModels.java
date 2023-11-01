package models.negative_response;

import lombok.Data;

@Data
public class NegativeResponseForAllModels {

    private Integer errorCode;
    private String errorMessage;
    private String errorDetails;
}
