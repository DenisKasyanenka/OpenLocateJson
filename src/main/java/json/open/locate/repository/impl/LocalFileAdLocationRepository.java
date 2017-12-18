package json.open.locate.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import json.open.locate.model.AdLocation;
import json.open.locate.repository.AdLocationRepository;
import json.open.locate.util.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@Component
public class LocalFileAdLocationRepository implements AdLocationRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileAdLocationRepository.class);
    private final static String fileUri = "./saved.json";
    private static final String FILE_CREATION_ERROR_MSG = "File can not be created in %s";
    private static final String SAVING_LOCATION_ERROR_MSG = "Failed to save location.";
    private static final String SEPARATOR = ", \n";
    private static File file;
    private BlockingQueue<AdLocation> queue = new ArrayBlockingQueue<>(1000);

    private final ObjectMapper objectMapper;

    @Autowired
    public LocalFileAdLocationRepository(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void init() throws IOException {
        file = new File(fileUri);
        createFileIfNotExist();
    }

    @PreDestroy
    public void destroy() {
        this.flush();
    }

    @Override
    public List<AdLocation> save(List<AdLocation> locations) {
        return locations.stream().map(this::putToSavingQueue).collect(Collectors.toList());
    }

    @Override
    public AdLocation save(AdLocation location) {
        return putToSavingQueue(location);
    }

    private void createFileIfNotExist() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error(String.format(FILE_CREATION_ERROR_MSG, fileUri), e);
            }
        }
    }

    private AdLocation putToSavingQueue(AdLocation loc) {
        try {
            queue.put(loc);
        } catch (InterruptedException e) {
            LOGGER.error(SAVING_LOCATION_ERROR_MSG, e);
        }
        return loc;
    }

    @Scheduled(fixedRate = Constants.FILE_UPDATE_TIMER_FREQUENCY)
    public void flush() {
        try {
            if (!CollectionUtils.isEmpty(queue)) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < queue.size(); i++) {
                    list.add(objectMapper.writeValueAsString(queue.take()));
                }
                writeToFile(list);
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error(SAVING_LOCATION_ERROR_MSG, e);
        }
    }

    private synchronized void writeToFile(List<String> jsons) throws IOException {
        FileUtils.writeStringToFile(file, StringUtils.join(jsons, SEPARATOR) + SEPARATOR, Charset.defaultCharset(), true);
    }
}
