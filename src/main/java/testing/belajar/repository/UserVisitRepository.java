package testing.belajar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import testing.belajar.model.UserVisitModel;

@Repository
public interface UserVisitRepository extends JpaRepository<UserVisitModel, Long> {

    @Query("select count(u) from UserVisitModel u where u.userId = ?1")
    long countByUserId(@NonNull String userId);

    UserVisitModel findFirstByUserId(String id);
    Page<UserVisitModel> findByNameContainingIgnoreCaseOrUserIdContainingIgnoreCase(String searchQuery, String searchQuery1, Pageable pageable);
}
