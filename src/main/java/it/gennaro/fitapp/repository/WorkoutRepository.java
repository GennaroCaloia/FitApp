package it.gennaro.fitapp.repository;

import it.gennaro.fitapp.entity.User;
import it.gennaro.fitapp.entity.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query("""
            select w from Workout w
            join w.plan p
            where p.owner = :owner
            order by coalesce(w.finishedAt, w.startedAt) desc, w.id desc
            """)
    Page<Workout> findLatestByOwner(@Param("owner") User owner, Pageable pageable);

    @Query("""
            select w from Workout w
            join fetch w.plan p
            left join fetch w.items wi
            left join fetch wi.exercise e
            where w.id = :id and p.owner = :owner
            """)
    List<Workout> findDetailByIdAndOwner(@Param("id") Long id, @Param("owner") User owner);
}
