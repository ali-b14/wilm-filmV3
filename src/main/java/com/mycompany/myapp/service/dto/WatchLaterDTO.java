package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.WatchLater} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WatchLaterDTO implements Serializable {

    private Long id;

    private VideoDTO video;

    private UserDTO userProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof WatchLaterDTO)) {
            return false;
        }

        WatchLaterDTO watchLaterDTO = (WatchLaterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, watchLaterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchLaterDTO{" +
            "id=" + getId() +
            ", video=" + getVideo() +
            ", userProfile=" + getUserProfile() +
            "}";
    }
}
