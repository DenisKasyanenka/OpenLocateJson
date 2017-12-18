package json.open.locate.model.error;

public enum ErrorId {

    GENERIC_ERROR("ERROR_000000"),
    VALIDATION_ERROR("ERROR_000001"),
    INVALID_PAYLOAD_ERROR("ERROR_000003");

    private String identifier;

    ErrorId(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
