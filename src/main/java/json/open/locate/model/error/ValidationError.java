package json.open.locate.model.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@ApiModel(value = "ValidationError", description = "Validation error representation" )
public class ValidationError {

    @ApiModelProperty(value = "Field which has validation errors", required = true, readOnly = true)
    private String name;
    @ApiModelProperty(value = "Error messages for field", required = true, readOnly = true)
    private List<String> errorMessages;

    public ValidationError(String name, String errorMessage) {
        this.name = name;
        this.errorMessages = Collections.singletonList(errorMessage);
    }
}
