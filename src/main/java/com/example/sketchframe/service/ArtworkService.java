package com.example.sketchframe.service;

import com.example.sketchframe.model.Artwork;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ArtworkService {
		void save(Artwork artwork);
		List<Artwork> getAllArtwork();
		List<Artwork> getAccessible();

		List<Artwork> getArtByUsername(String username);

		Artwork getArtById(Long id);
		Artwork getAccessibleArtById(Long id);
		Artwork getArtWithIdAndUsername(Long id, String username);
		void deleteArtById(Long id);
}
