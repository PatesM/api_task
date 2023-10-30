package models.negative_response;

import lombok.Data;

@Data
public class NegativeResponseForAllModels {

    private int errorCode;
    private String errorMessage;
    private String errorDetails;
}
