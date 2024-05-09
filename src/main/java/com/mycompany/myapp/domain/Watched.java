package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Watched.
 */
@Entity
@Table(name = "watched")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Watched implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "watched_at", nullable = false)
    private Instant watchedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "metaData", "uploader" }, allowSetters = true)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Watched id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getWatchedAt() {
        return this.watchedAt;
    }

    public Watched watchedAt(Instant watchedAt) {
        this.setWatchedAt(watchedAt);
        return this;
    }

    public void setWatchedAt(Instant watchedAt) {
        this.watchedAt = watchedAt;
    }

    public Video getVideo() {
        return this.video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Watched video(Video video) {
        this.setVideo(video);
        return this;
    }

    public User getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(User user) {
        this.userProfile = user;
    }

    public Watched userProfile(User user) {
        this.setUserProfile(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Watched)) {
            return false;
        }
        return getId() != null && getId().equals(((Watched) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Watched{" +
            "id=" + getId() +
            ", watchedAt='" + getWatchedAt() + "'" +
            "}";
    }
}
