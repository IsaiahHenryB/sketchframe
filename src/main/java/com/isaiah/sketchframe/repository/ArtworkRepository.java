package com.isaiah.sketchframe.repository;

import com.isaiah.sketchframe.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork,Long> {
 List<Artwork> findAll();
// These are sql queries that search through the Artwork entity for corresponding values
 @Query("SELECT a FROM Artwork a WHERE a.isAccessible = true")
 List<Artwork> findAllAccessible();
 @Query("SELECT a FROM Artwork a WHERE a.username = ?1")
 List<Artwork> findArtByUsername(String username);
 @Query("SELECT a FROM Artwork a WHERE a.isAccessible = true and a.id = ?1")
 Artwork findAccessibleById(Long id);
 @Query("SELECT a FROM Artwork a WHERE a.id = ?1 and a.username = ?2")
 Artwork findArtWithIdAndUsername(Long id, String username);
}
