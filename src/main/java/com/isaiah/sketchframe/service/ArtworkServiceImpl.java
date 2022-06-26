package com.isaiah.sketchframe.service;

import com.isaiah.sketchframe.model.Artwork;
import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
	public class ArtworkServiceImpl implements ArtworkService{
//	Implementing methods from ArtworkService interface
		@Autowired
		private ArtworkRepository artRepository;
		
		public ArtworkServiceImpl(ArtworkRepository artRepository) {
			super();
			this.artRepository = artRepository;
		}

//	Allows Artwork To Be Saved
	@Override
	public void save(Artwork artwork) {
		this.artRepository.save(artwork);
	}
//	Finds all artwork in the database
	@Override
	public List<Artwork> getAllArtwork() {
		return artRepository.findAll();
	}
//	Finds all artwork from database where isAccessible = true/1 boolean value
	@Override
	public List<Artwork> getAccessible() {
		return artRepository.findAllAccessible();
	}
//	Finds art form the database where there is a corresponding username(Not case-sensitive)
	@Override
	public List<Artwork> getArtByUsername(String username) {
			return artRepository.findArtByUsername(username);
	}
//	Finds Artwork From database with corresponding id field
	@Override
	public Artwork getArtById(Long id) {
		Optional<Artwork> optional = artRepository.findById(id);
		Artwork artwork = null;
		if(optional.isPresent()){
			artwork = optional.get();
		} else {
			throw new RuntimeException("Artwork not found in database");
		}
		return artwork;
	}
//	Finds all artwork where isAccessible = true and has corresponding id field
	@Override
	public Artwork getAccessibleArtById(Long id) {
		return artRepository.findAccessibleById(id);
	}
//	Finds art with corresponding id and username values in the database
	@Override
	public Artwork getArtWithIdAndUsername(Long id, String username) {
		return artRepository.findArtWithIdAndUsername(id , username);
	}
//	Finds and deletes artwork with corresponding id
	@Override
	public void deleteArtById(Long id) {
		this.artRepository.deleteById(id);
	}

}

