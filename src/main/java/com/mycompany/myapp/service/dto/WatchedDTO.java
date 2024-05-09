package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Watched} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchedDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant watchedAt;

    private VideoDTO video;

    private UserDTO userProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getWatchedAt() {
        return watchedAt;
    }

    public void setWatchedAt(Instant watchedAt) {
        this.watchedAt = watchedAt;
    }

    public VideoDTO getVideo() {
        return video;
    }

    public void setVideo(VideoDTO video) {
        this.video = video;
    }

    public UserDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserDTO userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchedDTO)) {
            return false;
        }

        WatchedDTO watchedDTO = (WatchedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, watchedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchedDTO{" +
            "id=" + getId() +
            ", watchedAt='" + getWatchedAt() + "'" +
            ", video=" + getVideo() +
            ", userProfile=" + getUserProfile() +
            "}";
    }
}
