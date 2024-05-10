package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.VideoMetaData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VideoMetaDataDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String genre;

    //    @NotNull
    private Instant uploadDate;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Instant getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Instant uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoMetaDataDTO)) {
            return false;
        }

        VideoMetaDataDTO videoMetaDataDTO = (VideoMetaDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, videoMetaDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoMetaDataDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", genre='" + getGenre() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
