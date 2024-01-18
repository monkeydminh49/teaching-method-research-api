package com.minhdunk.research.service;

import com.minhdunk.research.entity.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StreamingService {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private MediaService mediaService;

    public Mono<Resource> getStreamingMedia(String id){
        Media media = mediaService.getMediaById(id);
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:" + media.getFilePath()));
    }
}
