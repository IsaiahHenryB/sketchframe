package com.isaiah.sketchframe;

import com.isaiah.sketchframe.model.Artwork;
import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.repository.ArtworkRepository;
import com.isaiah.sketchframe.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

//  Many tests testing the repository methods of my application
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class SketchFrameRepoTests {
    @Autowired
    ArtworkRepository artworkRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
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
    public void testFindUserById() {
        Long id = 1L;
        Optional<User> user = userRepository.findById(id);
        assertThat(user).isPresent();
    }

    //    Parameterized test for findById that will pass then fail
    @ParameterizedTest
    @ValueSource(longs = {1L, 1000L})
    public void testFindUserByIdPassThenFail(Long arg) {
        Optional<User> user = userRepository.findById(arg);
        assertThat(user).isPresent();
    }

    @Test
    public void testFindUserByUsername() {
        String username = "Isaiah";
        User user = userRepository.findByUsername(username);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCreateArtwork() {
        Artwork artwork = new Artwork();
        artwork.setParams("Many Things");
        artwork.setTitle("artwork");
        artwork.setIsAccessible(true);
        artwork.setImage("dataurl");
        artwork.setUsername("username");
        artwork.setShowOutlines("Yes");
        artwork.setOutlineColor("On");
        artwork.setColorFill("Yes");
        artwork.setOutlineWidth("1.2");
        artwork.setLayers("4");
        artwork.setOpacity("200");
        artwork.setMinStrokeWidth("20");
        artwork.setMaxStrokeWidth("200");
        artwork.setStrokeAngle("120");
        artwork.setColorFill("Yes");
        artwork.setColorSelection("Palette Based");
        artwork.setPalette("125");

        Artwork savedArtwork = artworkRepository.save(artwork);

        Artwork existArtwork = entityManager.find(Artwork.class, savedArtwork.getId());

        assertThat(existArtwork.getId()).isEqualTo(artwork.getId());
    }

    @Test
    public void testFindArtById() {
        Optional<Artwork> artwork = artworkRepository.findById(2L);
        assertThat(artwork).isPresent();
    }

    @Test
    public void testFindArtByUsername() {
        String username = "Isaiah";
        List<Artwork> artwork = artworkRepository.findArtByUsername(username);
        assertFalse(artwork.isEmpty());
    }

    //    Parameterized test for findArtByUsername that will pass then fail
    @ParameterizedTest
    @ValueSource(strings = {"Isaiah", "Username"})
    public void testFindArtByUsernamePassThenFail(String arg) {
        List<Artwork> artwork = artworkRepository.findArtByUsername(arg);
        assertFalse(artwork.isEmpty());
    }

    @Test
    public void testFindArtByIsAccessible() {
        List<Artwork> artwork = artworkRepository.findAllAccessible();
        assertFalse(artwork.isEmpty());
    }

    @Test
    public void testFindArtWithIdAndUsername() {
        String username = "Isaiah";
        Artwork artwork = artworkRepository.findArtWithIdAndUsername(2L, username);
        assertThat(artwork).isNotNull();
    }
}
