package json.open.locate.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import json.open.locate.model.AdLocation;

import java.util.List;

public interface AdLocationRepository {
    AdLocation save(AdLocation location) throws JsonProcessingException, InterruptedException;

    List<AdLocation> save(List<AdLocation> locations);
}
