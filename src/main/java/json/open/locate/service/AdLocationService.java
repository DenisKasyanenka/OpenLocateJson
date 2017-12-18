package json.open.locate.service;

import json.open.locate.model.AdLocation;

import java.util.List;

public interface AdLocationService {

    List<AdLocation> saveLocations(List<AdLocation> locations);
}
