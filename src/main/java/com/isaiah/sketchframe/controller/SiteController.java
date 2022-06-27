package com.isaiah.sketchframe.controller;

import com.isaiah.sketchframe.model.Artwork;
import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.repository.ArtworkRepository;
import com.isaiah.sketchframe.repository.UserRepository;
import com.isaiah.sketchframe.service.ArtworkService;
import com.isaiah.sketchframe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class SiteController implements ErrorController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArtworkService artworkService;
    @Autowired
    ArtworkRepository artworkRepository;

    @Autowired
    private HttpServletRequest request;
    //		Adding slf4j Custom Transaction Logging
    Logger logger = LoggerFactory.getLogger(SiteController.class);

    //		A bunch of generic mapping to various views
    @RequestMapping("/about")
    public String showAboutPage() {
        logger.trace("User has accessed the about page");
        return "about";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        logger.trace("User has accessed the login page");
        return "login";
    }

    @GetMapping("/")
    public String showHomePage() {
        logger.trace("User has accessed the home/landing page");
        return "index";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        logger.error("There was an error");
        return "error";
    }

    @GetMapping("/noAccess")
    public String showNoAccessPage() {
        logger.error("user cannot access this artwork");
        return "noAccess";
    }

    @GetMapping("/sent")
    public String showMsgPage() {
        logger.trace("The message sent page was displayed");
        return "messageSent";
    }

    //	(READ) Get mapping for the community creations page that allows users to see all public artwork
    @GetMapping("/community")
    public String showCreationsPage(Model model) {
        model.addAttribute("Artlist", artworkService.getAccessible());
        logger.trace("User has accessed the community creations page");
        return "paintings";
    }

    //	(READ) Verifies user and allows them to see their artwork (If you aren't the authenticated user in the username path variable, you are given an error page)
    @PreAuthorize("#username == principal.username")
    @GetMapping("/creations/{username}")
    public String displayUserArt(@PathVariable(value = "username") String username, Model model) {
        model.addAttribute("artwork", artworkService.getArtByUsername(username));
        logger.trace("User: " + username + " has accessed their art management page");
        return "mypaintings";
    }

    //	(READ) Verifies user and allows them to update their artwork name and accessibility (If you aren't the authenticated user in the username path variable, you are given an error page)
    @PreAuthorize("#username == principal.username")
    @GetMapping("/edit/{id}/{username}")
    public String updateUserArt(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "username") String username,
            Model model) {
        model.addAttribute("artwork", artworkService.getArtById(id));
        logger.trace("User: " + username + " has accessed the image update page");
        return "update";
    }

    //	(READ) Maps to a page that loads in the parameters of the selected artwork into the art generator
    @GetMapping("/remake/{id}")
    public String regenerateUserArt(
            @PathVariable(value = "id") Long id,
            Model model) {
        model.addAttribute("artwork", artworkService.getAccessibleArtById(id));
        logger.trace("User has accessed the regenerate page");
        return "regenerate";
    }

    //	(READ) Takes data from private artwork and displays it into the art generator
    @PreAuthorize("#username == principal.username")
    @GetMapping("/remake/{id}/{username}")
    public String regeneratePrivateUserArt(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "username") String username,
            Model model) {
        model.addAttribute("artwork", artworkService.getArtWithIdAndUsername(id, username));
        logger.trace("User: " + username + " has accessed the specialized regenerate page");
        return "regenerate";
    }

    //	(UPDATE) The post mapping used to overwrite the artwork in the database
    @PostMapping("/update")
    public String updateArtWork(@ModelAttribute("artwork") Artwork artwork) {
        String loggedInUser = request.getUserPrincipal().getName();
        artworkService.save(artwork);
        logger.trace("User: " + loggedInUser + " has updated their artwork!");
        return "redirect:/creations/" + loggedInUser;
    }

    //	(CREATE) The post mapping used to push the artwork into the database
    @PostMapping("/create")
    public String createArtWork(@ModelAttribute("artwork") Artwork artwork) {
        String loggedInUser = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(loggedInUser);
        artworkService.save(artwork);
        user.getArtwork().add(artwork);
        this.userRepository.save(user);
        logger.trace("User: " + loggedInUser + " has uploaded a new artwork!");
        return "redirect:/creations/" + loggedInUser;
    }

    //	(READ) Loads up the paint.html view with Artwork as the model attribute to eventually be saved to in the above post mapping ^^
    @GetMapping("/create")
    public String uploadArtForm(Model model) {
        Artwork artwork = new Artwork();
        model.addAttribute("artwork", artwork);
        logger.trace("User has accessed the create page");
        return "paint";
    }

    //	(READ) View that allows users to see the accessible artwork with corresponding id
    @GetMapping("/view/{id}")
    public String viewArt(
            @PathVariable(value = "id") Long id,
            Model model) throws Exception {
        try {
            model.addAttribute("artwork", artworkService.getAccessibleArtById(id));
            logger.trace("User has accessed the image with id: " + id);
            return "painting";
        } catch (Exception ex) {
            logger.error("Image Is Not Accessible");
            return "redirect:/noAccess";
        }

    }

    //	(READ) View that allows authenticated user to see both their private and accessible artwork
    @PreAuthorize("#username == principal.username")
    @GetMapping("/view/{id}/{username}")
    public String showUserArtwork(@PathVariable(value = "id") Long id,
                                  @PathVariable(value = "username") String username,
                                  Model model) {
        model.addAttribute("artwork", artworkService.getArtWithIdAndUsername(id, username));
        logger.trace("User has accessed their artwork with id: " + id);
        return "mypainting";
    }

    //	(DELETE) Mapping that allows only corresponding user to delete their artwork from the database
    @PreAuthorize("#username == principal.username")
    @GetMapping("/delete/{id}/{username}")
    public String deleteArtwork(@PathVariable(value = "id") Long id,
                                @PathVariable(value = "username") String username) {
        String loggedInUser = request.getUserPrincipal().getName();
        String artist = artworkService.getArtById(id).getUsername();
        if(loggedInUser.equals(username)) {
            this.artworkService.deleteArtById(id);
            logger.trace("User: " + loggedInUser + " has saved deleted artwork with id:" + id);
            return "redirect:/creations/" + loggedInUser;
        } else  {
            logger.error(username +" Is not the artist of this piece, "+ artist + " is!");
            return "redirect:/noAccess";
        }

    }
}
