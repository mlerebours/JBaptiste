package com.modo.apps.dictee.repository;

import com.modo.apps.dictee.domain.Dictee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Dictee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DicteeRepository extends JpaRepository<Dictee, Long> {
    @Query("select distinct dictee from Dictee dictee left join fetch dictee.words")
    List<Dictee> findAllWithEagerRelationships();

    @Query("select dictee from Dictee dictee left join fetch dictee.words where dictee.id =:id")
    Dictee findOneWithEagerRelationships(@Param("id") Long id);

}
