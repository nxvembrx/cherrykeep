package com.alxwnth.cherrybox.cherrykeep.repository;

import com.alxwnth.cherrybox.cherrykeep.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long id);

    List<Note> findByUserIdAndPinnedTrueOrderByCreatedAt(Long id);

    List<Note> findByUserIdAndPinnedFalseAndExpiresAtAfterOrderByCreatedAt(Long user_id, ZonedDateTime expiresAt);
}
