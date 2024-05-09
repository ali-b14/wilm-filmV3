package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Watched;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Watched entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchedRepository extends JpaRepository<Watched, Long> {
    @Query("select watched from Watched watched where watched.userProfile.login = ?#{authentication.name}")
    List<Watched> findByUserProfileIsCurrentUser();
}
