package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Like;
import com.mycompany.myapp.domain.Video;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select jhiLike from Like jhiLike where jhiLike.user.login = ?#{authentication.name}")
    List<Like> findByUserIsCurrentUser();

    @Query("SELECT COUNT(l) FROM Like l WHERE l.video.id = :videoId")
    Long countByVideoId(@Param("videoId") Long videoId);
}
