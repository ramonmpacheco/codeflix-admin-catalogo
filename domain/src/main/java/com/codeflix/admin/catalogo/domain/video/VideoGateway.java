package com.codeflix.admin.catalogo.domain.video;

import com.codeflix.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {
    Video create(Video aVideo);

    void deleteById(VideoId anId);

    Optional<Video> findById(VideoId anId);

    Video update(Video aVideo);

    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);
}
