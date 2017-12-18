package json.open.locate.model.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ApiModel(value = "Error", description = "Error representation")
public class BasicError {

    @ApiModelProperty(value = "Unique identifier for this particular occurrence of the problem", required = true, readOnly = true)
    private final UUID uuid;
    @ApiModelProperty(value = "HTTP status code applicable to this problem", required = true, readOnly = true)
    private final String returnStatus;
    @JsonIgnore
    private final ErrorId errorId;
    @ApiModelProperty(value = "Human-readable summary of the problem", required = true, readOnly = true)
    private final String title;
    @ApiModelProperty(value = "human-readable explanation specific of the problem", required = true, readOnly = true)
    private final String detail;
    @ApiModelProperty(value = "Object containing non-standard meta-information about the error", readOnly = true)
    private List<ValidationError> validationErrors;

    public BasicError(UUID uuid, String returnStatus, ErrorId errorId, String detail) {
        this(uuid, returnStatus, errorId, detail, new ArrayList<>());
    }

    public BasicError(UUID uuid, String returnStatus, ErrorId errorId, String detail,
                      List<ValidationError> validationErrors) {
        this.uuid = uuid;
        this.returnStatus = returnStatus;
        this.errorId = errorId;
        this.title = this.errorId.name();
        this.detail = detail;
        this.validationErrors = validationErrors;
    }
}
