package json.open.locate.controller;

import json.open.locate.model.AdLocation;
import json.open.locate.model.error.BasicError;
import json.open.locate.service.AdLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/location")
@Api(value = "/location", description = "Location actions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdLocationController {

    private final AdLocationService adLocationService;

    @Autowired
    public AdLocationController(AdLocationService adLocationService) {
        this.adLocationService = adLocationService;
    }

    @ApiOperation(value = "Stores list of Ad Locations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List saved locations", response = AdLocation.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Ad Locations are not valid", response = BasicError.class),
    })
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdLocation>> saveLocation(@RequestBody @Valid ArrayList<AdLocation> locations) {
        return new ResponseEntity<>(adLocationService.saveLocations(locations), HttpStatus.OK);
    }
}
