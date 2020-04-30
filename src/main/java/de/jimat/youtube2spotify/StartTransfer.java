package de.jimat.youtube2spotify;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import de.jimat.youtube2spotify.spotify.Spotify;
import de.jimat.youtube2spotify.webservice.RestServiceApplication;
import de.jimat.youtube2spotify.youtube.YTPlaylistItem;
import de.jimat.youtube2spotify.youtube.YoutubePlaylist;

public class StartTransfer {
	
	private static Spotify spotify;
	private static YoutubePlaylist youtube;
	
	
	public static void main(String[] args) throws IOException, InterruptedException, SpotifyWebApiException, URISyntaxException {
		youtube = new YoutubePlaylist("PLaJMPdUYqLLws7HlLN9SgELyv0xkm-NMa");
		
		spotify = new Spotify("ae0934dbb741437192fe6e13ef6f53cc", 
							  "e29c878fa6524136aebc9d8128ee8a02", 
				 			  "1Ec7wYHhTk1kKqcqSFkOym");

		RestServiceApplication.startService();
		spotify.getAuthorizeUri();
	} 
	
	public static void startSpotify() throws SpotifyWebApiException, IOException, InterruptedException {
		System.out.println("starting . . .");
		youtube.startYoutubeAPI();
		spotify.startTransfer(youtube.getPlaylistItems());
	}

}
