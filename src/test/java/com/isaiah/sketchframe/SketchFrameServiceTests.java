package com.isaiah.sketchframe;

import com.isaiah.sketchframe.model.Artwork;
import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.service.ArtworkService;
import com.isaiah.sketchframe.service.UserService;
import com.isaiah.sketchframe.web.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;

//  Some tests testing some service class methods of my application
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class SketchFrameServiceTests {
    @Autowired
    UserService userService;
    @Autowired
    ArtworkService artworkService;

    @Test
    public void testLoadUserByUsername(){
        String username = "Isaiah";
        UserDetails user = userService.loadUserByUsername(username);
        assertThat(user).isNotNull();
    }

    @Test
    public void testGetArtById(){
        Artwork artwork = artworkService.getArtById(2L);
        assertThat(artwork).isNotNull();
    }
    @Test
    public void testGetArtByUsername(){
        String username = "Isaiah";
        List<Artwork> artwork = artworkService.getArtByUsername(username);
        assertFalse(artwork.isEmpty());
    }
//    Parameterized test for findArtByUsername that will pass then fail
    @ParameterizedTest
    @ValueSource(strings = {"Isaiah","Username"})
    public void testGetArtByUsernamePassThenFail(String arg){
        List<Artwork> artwork = artworkService.getArtByUsername(arg);
        assertFalse(artwork.isEmpty());
    }
    @Test
    public void testGetArtByIsAccessible(){
        List<Artwork> artwork = artworkService.getAccessible();
        assertFalse(artwork.isEmpty());
    }
    @Test
    public void testFindArtWithIdAndUsername(){
        String username = "Isaiah";
        Artwork artwork = artworkService.getArtWithIdAndUsername(2L, username);
        assertThat(artwork).isNotNull();
    }
}
