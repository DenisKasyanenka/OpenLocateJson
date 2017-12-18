package json.open.locate.service.impl;

import json.open.locate.model.AdLocation;
import json.open.locate.repository.AdLocationRepository;
import json.open.locate.service.AdLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdLocationServiceImpl implements AdLocationService {

    private final AdLocationRepository repository;

    @Autowired
    public AdLocationServiceImpl(AdLocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AdLocation> saveLocations(List<AdLocation> locations) {
        return repository.save(locations);
    }
}
