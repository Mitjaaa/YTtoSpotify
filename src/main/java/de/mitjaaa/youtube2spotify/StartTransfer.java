package de.mitjaaa.youtube2spotify;

import java.io.IOException;
import java.net.URISyntaxException;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import de.mitjaaa.youtube2spotify.spotify.Spotify;
import de.mitjaaa.youtube2spotify.webservice.RestServiceApplication;
import de.mitjaaa.youtube2spotify.youtube.YoutubePlaylist;

public class StartTransfer {
	
	private static Spotify spotify;
	private static YoutubePlaylist youtube;
	
	
	public static void main(String[] args) throws IOException, InterruptedException, SpotifyWebApiException, URISyntaxException {
		youtube = new YoutubePlaylist("your-yt-playlist-link");
		
		spotify = new Spotify("your-client-id", 
							  "your-client-secret", 
				 			  "your-spotify-playlist-id");

		RestServiceApplication.startService();
		spotify.getAuthorizeUri();
	} 
	
	public static void startSpotify() throws SpotifyWebApiException, IOException, InterruptedException {
		System.out.println("starting . . .");
		youtube.startYoutubeAPI();
		spotify.startTransfer(youtube.getPlaylistItems());
	}

}
