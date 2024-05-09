package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Like;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select jhiLike from Like jhiLike where jhiLike.user.login = ?#{authentication.name}")
    List<Like> findByUserIsCurrentUser();
}
