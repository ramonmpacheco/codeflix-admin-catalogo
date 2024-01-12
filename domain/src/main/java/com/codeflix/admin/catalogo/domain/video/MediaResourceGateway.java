package com.codeflix.admin.catalogo.domain.video;

import java.util.Optional;

public interface MediaResourceGateway {
    AudioVideoMedia storeAudioVideo(VideoId anId, VideoResource aResource);

    ImageMedia storeImage(VideoId anId, VideoResource aResource);

    Optional<Resource> getResource(VideoId anId, VideoMediaType type);

    void clearResources(VideoId anId);
}
