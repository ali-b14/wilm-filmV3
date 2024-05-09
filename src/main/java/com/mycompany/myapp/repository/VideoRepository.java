package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Video;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("select video from Video video where video.uploader.login = ?#{authentication.name}")
    List<Video> findByUploaderIsCurrentUser();
}
