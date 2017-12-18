package json.open.locate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LocationContext {

    @JsonProperty("unknown")
    UNKNOWN,
    @JsonProperty("passive")
    PASSIVE,
    @JsonProperty("regular")
    REGULAR,
    @JsonProperty("visit_entry")
    VISIT_ENTRY,
    @JsonProperty("visit_exit")
    VISIT_EXIT;
}
