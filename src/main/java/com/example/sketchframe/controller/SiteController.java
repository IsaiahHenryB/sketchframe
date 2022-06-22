package com.example.sketchframe.controller;

import com.example.sketchframe.model.Artwork;
import com.example.sketchframe.service.ArtworkService;
import com.example.sketchframe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class SiteController implements ErrorController{

		@Autowired
		private UserService userService;

		@Autowired
		private ArtworkService artworkService;

		@Autowired
		private HttpServletRequest request;
//	A bunch of generic mapping to various views
		@RequestMapping("/about")
		public String showAboutPage() {
			return "about";
		}
		@GetMapping("/login")
		public String showLoginPage() {
			return "login";
		}
		@GetMapping("/")
		public String showHomePage() {
			return "index";
		}
		@GetMapping("/error")
		public String showErrorPage() {
			return "error";
		}
		@GetMapping("/sent")
		public String showMsgPage() {
		return "messageSent";
	}
//	Get mapping for the community creations page that allows users to see all public artwork
		@GetMapping("/community")
		public String showCreationsPage(Model model) {
			model.addAttribute("Artlist", artworkService.getAccessible());
			return "paintings";
		}
//	Verifies user and allows them to see their artwork (If you aren't the authenticated user in the username path variable, you are given an error page)
	@PreAuthorize("#username == principal.username")
	@GetMapping("/creations/{username}")
	public String displayUserArt(@PathVariable(value = "username") String username, Model model){
		model.addAttribute("artwork", artworkService.getArtByUsername(username));
		return "mypaintings";
	}
	//	Verifies user and allows them to update their artwork name and accessibility (If you aren't the authenticated user in the username path variable, you are given an error page)
	@PreAuthorize("#username == principal.username")
	@GetMapping("/edit/{id}/{username}")
	public String updateUserArt(
			@PathVariable(value = "id") Long id,
			@PathVariable(value = "username") String username,
			Model model){
			model.addAttribute("artwork", artworkService.getArtById(id));
			return "update";
	}
//	Maps to a page that loads in the parameters of the selected artwork into the art generator
	@GetMapping("/remake/{id}")
	public String regenerateUserArt(
			@PathVariable(value = "id") Long id,
			Model model){
		model.addAttribute("artwork", artworkService.getAccessibleArtById(id));
		return "regenerate";
	}
	@PreAuthorize("#username == principal.username")
	@GetMapping("/remake/{id}/{username}")
	public String regeneratePrivateUserArt(
			@PathVariable(value = "id") Long id,
			@PathVariable(value = "username") String username,
			Model model){
		model.addAttribute("artwork", artworkService.getArtWithIdAndUsername(id,username));
		return "regenerate";
	}
//	The post mapping used to overwrite the artwork in the database
	@PostMapping("/update")
	public String updateArtWork(@ModelAttribute("artwork") Artwork artwork) {
		String loggedInUser = request.getUserPrincipal().getName();
		artworkService.save(artwork);
		return "redirect:/creations/"+loggedInUser;
	}
//	The post mapping used to push the artwork into the database
	@PostMapping("/create")
	public String createArtWork(@ModelAttribute("artwork") Artwork artwork){
				String loggedInUser = request.getUserPrincipal().getName();
				artworkService.save(artwork);
				return "redirect:/creations/"+loggedInUser;
	}
//	Loads up the paint.html view with Artwork as the model attribute to eventually be saved to in the above post mapping ^^
	@GetMapping("/create")
	public String uploadArtForm(Model model) {
			Artwork artwork = new Artwork();
			model.addAttribute("artwork", artwork);
			return "paint";
	}
//	View that allows users to see the accessible artwork with corresponding id
	@GetMapping("/view/{id}")
	public String viewArt(
			@PathVariable(value = "id") Long id,
			Model model) throws Exception{
			try{
				model.addAttribute("artwork", artworkService.getAccessibleArtById(id));
				return "painting";
			} catch (Exception ex){
				return "noAccess";
			}

	}
//	View that allows authenticated user to see both their private and accessible artwork
	@PreAuthorize("#username == principal.username")
	@GetMapping("/view/{id}/{username}")
	public String showUserArtwork(@PathVariable (value = "id") Long id,
								@PathVariable(value = "username") String username,
								  Model model){
		model.addAttribute("artwork", artworkService.getArtWithIdAndUsername(id,username));
		return "mypainting";
	}
//	View that allows only corresponding user to delete their artwork from the database
	@PreAuthorize("#username == principal.username")
	@GetMapping("/delete/{id}/{username}")
	public String deleteArtwork(@PathVariable (value = "id") Long id,
								@PathVariable(value = "username") String username){
		this.artworkService.deleteArtById(id);
		String loggedInUser = request.getUserPrincipal().getName();
		return "redirect:/creations/"+loggedInUser;
	}
}
