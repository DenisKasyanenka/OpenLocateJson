package json.open.locate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import json.open.locate.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonPropertyOrder({
        "ad_id",
        "ad_opt_out",
        "id_type",
        "latitude",
        "longitude",
        "utc_timestamp",
        "horizontal_accuracy",
        "vertical_accuracy",
        "altitude",
        "wifi_ssid",
        "wifi_bssid",
        "location_context",
        "course",
        "speed",
        "is_charging",
        "device_model",
        "os_version"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdLocation {

    @NotEmpty @NotNull
    @JsonProperty("ad_id")
    @ApiModelProperty(value = "An alphanumeric string unique to each device, used only for serving advertisements.")
    private String adId;

    @NotNull
    @JsonProperty("ad_opt_out")
    @ApiModelProperty(value = "A Boolean value that indicates whether the user has limited ad tracking.")
    private Boolean adOptOut;

    /*
     * if this field has finite number of values should be enum. Only "idfa" is known so far
     * IDFA stands for Apple "identifier for advertisers."
     */
    @NotEmpty @NotNull
    @JsonProperty("id_type")
    @ApiModelProperty(value = "A string value that indicates which operating systen advertising info belongs to. 'idfa' for iOS devices")
    private String idType;

    /**
     * Using double has enough precision for accurate lat/lon down to inches for 6-7 decimal places.
     */
    @NotNull @DecimalMin(value = "" + Constants.LATITUDE_RANGE_SOUTH) @DecimalMax(value = "" + Constants.LATITUDE_RANGE_NORTH)
    @ApiModelProperty(value = "The latitude in degrees.")
    @JsonProperty("latitude")
    private Double latitude;

    @NotNull @DecimalMin(value = "" + Constants.LONGITUDE_RANGE_WEST) @DecimalMax(value = "" + Constants.LONGITUDE_RANGE_EAST)
    @JsonProperty(value = "longitude")
    @ApiModelProperty(value = "The longitude in degrees.")
    private Double longitude;

    @NotNull @Min(0L)
    @JsonProperty(value = "utc_timestamp")
    @ApiModelProperty(value = "The time at which this location was determined.")
    private Long utcTimestamp;

    @NotNull @DecimalMin("" + 0.0F)
    @JsonProperty(value = "horizontal_accuracy")
    @ApiModelProperty(value = "The radius of uncertainty for the location, measured in meters.")
    private Float horizontalAccuracy;

    @NotNull @DecimalMin("" + 0.0F)
    @JsonProperty(value = "vertical_accuracy")
    @ApiModelProperty(value = "The accuracy of the altitude value, measured in meters.")
    public Float verticalAccuracy;

    @NotNull @DecimalMin(value = "" + 0.0F)
    @JsonProperty(value = "altitude")
    @ApiModelProperty(value = "The altitude, measured in meters.")
    private Float altitude;

    @NotEmpty @NotNull
    @JsonProperty(value = "wifi_ssid")
    @ApiModelProperty(value = "A string value representing the bssid of the wifi to which the device is connected to")
    private String wifiSsid;

    @NotEmpty @NotNull
    @JsonProperty(value = "wifi_bssid")
    @ApiModelProperty(value = "A string value representing the ssis of the wifi to which the device is connected to")
    private String wifiBssid;

    @NotNull
    @JsonProperty(value = "location_context")
    @ApiModelProperty(value = "A string value representing the state of the location when it was collected. Possible value - unknown, passive, regular, visit_entry, visit_exit")
    private LocationContext locationContext;

    @NotNull @DecimalMin(value = "" + 0.0F) @DecimalMax(value = "" + Constants.COURSE_MAX)
    @JsonProperty("course")
    @ApiModelProperty(value = "The direction in which the device is traveling.")
    private Float course;

    @NotNull @DecimalMin(value = "" + 0.0F)
    @JsonProperty(value = "speed")
    @ApiModelProperty(value = "The instantaneous speed of the device, measured in meters per second.")
    private Float speed;

    @NotNull
    @JsonProperty(value = "is_charging")
    @ApiModelProperty(value = "A boolean value to determine if the phone was charging when the location was determined")
    private Boolean isCharging;

    @NotEmpty @NotNull
    @JsonProperty(value = "device_model")
    @ApiModelProperty(value = "A string value representing the model of the device")
    private String deviceModel;

    @NotEmpty @NotNull
    @JsonProperty(value = "os_version")
    @ApiModelProperty(value = "A String value representing the version of the operating system")
    private String osVersion;
}
