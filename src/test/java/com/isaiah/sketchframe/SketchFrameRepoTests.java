package com.isaiah.sketchframe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import com.isaiah.sketchframe.model.Artwork;
import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.repository.ArtworkRepository;
import com.isaiah.sketchframe.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

//  Many tests testing the repository methods of my application
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class SketchFrameRepoTests {
    @Autowired
    ArtworkRepository artworkRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser()
    {
        User user = new User();
        user.setEmail("user@user.com");
        user.setPassword("password");
        user.setFirstname("user");
        user.setLastname("name");
        user.setUsername("username");

        User savedUser = userRepository.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

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
    public void testCreateArtwork()
    {
        Artwork artwork = new Artwork();
        artwork.setParams("Many Things");
        artwork.setTitle("artwork");
        artwork.setIsAccessible(true);
        artwork.setImage("dataurl");
        artwork.setUsername("username");

        Artwork savedArtwork = artworkRepository.save(artwork);

        Artwork existArtwork = entityManager.find(Artwork.class, savedArtwork.getId());

        assertThat(existArtwork.getId()).isEqualTo(artwork.getId());
    }
    @Test
    public void testFindArtByid(){
        Optional<Artwork> artwork = artworkRepository.findById(Long.valueOf(2));
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
        Artwork artwork = artworkRepository.findArtWithIdAndUsername(Long.valueOf(2), username);
        assertThat(artwork).isNotNull();
    }
}
