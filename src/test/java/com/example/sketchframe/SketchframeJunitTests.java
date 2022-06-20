package com.example.sketchframe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import com.example.sketchframe.model.Artwork;
import com.example.sketchframe.model.User;
import com.example.sketchframe.repository.ArtworkRepository;
import com.example.sketchframe.repository.UserRepository;
import com.example.sketchframe.service.ArtworkService;
import com.example.sketchframe.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SketchframeJunitTests {
    @Autowired
    ArtworkRepository artworkRepository;
    @Autowired
    UserRepository userRepository;
    @Test
    public void testFindUserById(){
        Long id = Long.valueOf(1);
        Optional<User> user = userRepository.findById(id);
        assertThat(user).isNotNull();
    }
    @Test
    public void testFindUserByUsername(){
        String username = "Isaiah";
        User user = userRepository.findByUsername(username);
        assertThat(user).isNotNull();
    }
    @Test
    public void testFindArtByid(){
        Optional<Artwork> artwork = artworkRepository.findById(Long.valueOf(3));
        assertThat(artwork).isPresent();
    }
    @Test
    public void testFindArtByUsername(){
        String username = "Isaiah";
        List<Artwork> artwork = artworkRepository.findArtByUsername(username);
        assertFalse(artwork.isEmpty());
    }
    @Test
    public void testFindArtByIsAccessible(){
        List<Artwork> artwork = artworkRepository.findAllAccessible();
        assertFalse(artwork.isEmpty());
    }
    @Test
    public void testFindArtWithIdAndUsername(){
        String username = "Isaiah";
        Artwork artwork = artworkRepository.findArtWithIdAndUsername(Long.valueOf(3), username);
        assertThat(artwork).isNotNull();
    }
}
