package com.isaiah.sketchframe;

import com.isaiah.sketchframe.model.Artwork;
import com.isaiah.sketchframe.repository.ArtworkRepository;
import com.isaiah.sketchframe.service.ArtworkServiceImpl;
import com.isaiah.sketchframe.service.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


//Testing a few methods from my service classes
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class SketchFrameServiceTestWithMockito {
    @MockBean
    ArtworkRepository artworkRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;
    @InjectMocks
    ArtworkServiceImpl artworkServiceImpl;
//  An attempt at testing using mockito
//    Tests getArtWithUsernameAndId method in the ArtworkService Class
    @ParameterizedTest
    @CsvSource({"1,Isaiah", "77,username"})
//    Should Pass then Fail
    public void testGetArtWithIdAndUsernamePassThenFail(Long arg1, String arg2){
        Long id = 1L;
        String username = "Isaiah";
        Artwork artwork = new Artwork();
        artwork.setId(id);
        artwork.setUsername(username);
        Mockito.when(artworkRepository.findArtWithIdAndUsername(id, username)).thenReturn(artwork);
        Artwork thisArt = artworkServiceImpl.getArtWithIdAndUsername(id, username);
        assertEquals(thisArt.getId(),arg1);
        assertEquals(thisArt.getUsername(),arg2);
    }
}
