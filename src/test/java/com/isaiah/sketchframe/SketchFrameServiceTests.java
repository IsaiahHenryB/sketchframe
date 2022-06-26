package com.isaiah.sketchframe;

import com.isaiah.sketchframe.model.Artwork;
import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.repository.ArtworkRepository;
import com.isaiah.sketchframe.repository.UserRepository;
import com.isaiah.sketchframe.service.ArtworkServiceImpl;
import com.isaiah.sketchframe.service.UserServiceImpl;
import com.isaiah.sketchframe.web.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


//Testing a few methods from my service classes
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class SketchFrameServiceTests {
    @MockBean
    UserRepository userRepository;
    @MockBean
    ArtworkRepository artworkRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;
    @InjectMocks
    ArtworkServiceImpl artworkServiceImpl;

//  Tests doesUsernameExist method in the UserService Class
    @Test
//    Should Pass
    public void testDoesUsernameExist(){
        String username = "Isaiah";
        User user = new User();
        user.setUsername(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);
        boolean result = userServiceImpl.doesUsernameExist(username);
        assertTrue(result);
    }
    @Test
//    Should Fail
    public void testDoesUsernameExistFail(){
        String username = "Isaiah";
        User user = new User();
        user.setUsername("username");
        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);
        boolean result = userServiceImpl.doesUsernameExist(username);
        assertTrue(result);
    }
//    Tests getArtWithUsernameAndId method in the ArtworkService Class
    @Test
//    Should Pass
    public void testGetArtWithIdAndUsername(){
        Long id = 1L;
        String username = "Isaiah";
        Artwork artwork = new Artwork();
        artwork.setId(id);
        artwork.setUsername(username);
        Mockito.when(artworkRepository.findArtWithIdAndUsername(id, username)).thenReturn(artwork);
        Artwork thisArt = artworkServiceImpl.getArtWithIdAndUsername(id, username);
        assertThat(thisArt).isNotNull();
    }
}
