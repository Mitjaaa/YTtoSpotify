package de.mitjaaa.youtube2spotify;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import de.mitjaaa.youtube2spotify.spotify.Spotify;
import de.mitjaaa.youtube2spotify.webservice.RestServiceApplication;
import de.mitjaaa.youtube2spotify.youtube.YTPlaylistItem;
import de.mitjaaa.youtube2spotify.youtube.YoutubePlaylist;

public class StartTransfer {
	
	private static Spotify spotify;
	private static YoutubePlaylist youtube;
	
	private static List<YTPlaylistItem> items;
	
	
	public static void main(String[] args) throws IOException, InterruptedException, SpotifyWebApiException, URISyntaxException {
		youtube = new YoutubePlaylist("youtube-playlist-id");
		
		spotify = new Spotify("spotify.client-id", 
							  "spotify-client-secret", 
				 			  "spotify-playlist-id");

		
		System.out.println("starting Youtube API ...");
		youtube.startYoutubeAPI();
		items = youtube.getPlaylistItems();
		
		
		System.out.println("starting rest service ...");
		RestServiceApplication.startService();
		spotify.getAuthorizeUri();
	} 
	
	public static void startSpotify() throws SpotifyWebApiException, IOException, InterruptedException {
		System.out.println("starting transfer ...");
		spotify.startTransfer(items);
	}

}
