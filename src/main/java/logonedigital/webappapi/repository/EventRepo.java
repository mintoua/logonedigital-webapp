package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {
    Optional<Event> findBySlug(String slug);
    @Query("select e from Event e where e.titre = :title")
    Optional<Event> fetchEventByTitle(@Param("title") String title);
    @Query("select e from Event e where e.eventCategory.slug =:slug")
    Page<Event> fetchEventsByEventCategory(@Param("slug") String slug, Pageable pageable);
    @Query("select e from Event e where  e.published=true")
    Page<Event> fectchPublishedEvents(Pageable pageable);
}
