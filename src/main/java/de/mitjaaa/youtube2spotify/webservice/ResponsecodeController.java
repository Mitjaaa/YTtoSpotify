package de.mitjaaa.youtube2spotify.webservice;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import de.mitjaaa.youtube2spotify.StartTransfer;
import de.mitjaaa.youtube2spotify.spotify.Spotify;

@RestController
public class ResponsecodeController {

	@GetMapping("/api")
	public String response(@RequestParam(value = "code", defaultValue = "1234") String code) throws SpotifyWebApiException, IOException, InterruptedException {
		Spotify.code = code;
		
		Thread transfer = new Thread() {
			public void run() {
				try {
					StartTransfer.startSpotify();
				} catch (SpotifyWebApiException | IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		transfer.start();
		
		return "You are done here!";
	}
}