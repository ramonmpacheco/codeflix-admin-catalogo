package com.codeflix.admin.catalogo.application.video.media.update;

import com.codeflix.admin.catalogo.domain.exceptions.NotFoundException;
import com.codeflix.admin.catalogo.domain.video.*;

import java.util.Objects;

import static com.codeflix.admin.catalogo.domain.video.VideoMediaType.TRAILER;
import static com.codeflix.admin.catalogo.domain.video.VideoMediaType.VIDEO;

public class DefaultUpdateMediaStatusUseCase extends UpdateMediaStatusUseCase {

    private final VideoGateway videoGateway;

    public DefaultUpdateMediaStatusUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public void execute(final UpdateMediaStatusCommand aCmd) {
        final var anId = VideoId.from(aCmd.videoId());
        final var aResourceId = aCmd.resourceId();
        final var folder = aCmd.folder();
        final var filename = aCmd.filename();

        final var aVideo = this.videoGateway.findById(anId)
                .orElseThrow(() -> notFound(anId));

        final var encodedPath = "%s/%s".formatted(folder, filename);

        if (matches(aResourceId, aVideo.getVideo().orElse(null))) {
            updateVideo(VIDEO, aCmd.status(), aVideo, encodedPath);
        } else if (matches(aResourceId, aVideo.getTrailer().orElse(null))) {
            updateVideo(TRAILER, aCmd.status(), aVideo, encodedPath);
        }
    }

    private void updateVideo(final VideoMediaType aType, final MediaStatus aStatus, final Video aVideo, final String encodedPath) {
        switch (aStatus) {
            case PENDING -> {
            }
            case PROCESSING -> aVideo.processing(aType);
            case COMPLETED -> aVideo.completed(aType, encodedPath);
        }

        this.videoGateway.update(aVideo);
    }

    private boolean matches(final String anId, final AudioVideoMedia aMedia) {
        if (aMedia == null) {
            return false;
        }

        return aMedia.id().equals(anId);
    }

    private NotFoundException notFound(final VideoId anId) {
        return NotFoundException.with(Video.class, anId);
    }
}
