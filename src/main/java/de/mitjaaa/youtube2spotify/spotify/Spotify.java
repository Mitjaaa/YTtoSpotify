package de.mitjaaa.youtube2spotify.spotify;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import de.mitjaaa.youtube2spotify.youtube.YTPlaylistItem;

public class Spotify {
	
	public static String code;
	
	private String clientId;
	private String clientSecret;
	private URI redirectUri;
	
	private String playlistId;
	
	private SpotifyApi spotifyApi;
	
	
	public Spotify(String clientId, String clientSecret, String playlistId) throws SpotifyWebApiException, IOException {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.playlistId = playlistId;
		
		this.redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api");
		
		spotifyApi = new SpotifyApi.Builder()
		          .setClientId(this.clientId)
		          .setClientSecret(this.clientSecret)
		          .setRedirectUri(this.redirectUri)
		          .build();
	}
	
	public void startTransfer(List<YTPlaylistItem> tracks) throws SpotifyWebApiException, IOException {
		getAuthorizeTokens();
		searchTracks(tracks);
		
		System.out.println("Transfered all tracks to Spotify!");
		System.exit(0);
	}
	
	
	
	private SearchTracksRequest searchTracksRequest;
	
	private void searchTracks(List<YTPlaylistItem> tracks) throws SpotifyWebApiException, IOException {
		System.out.println("Searching all tracks ...");
		
		for(int i = 0; i < tracks.size(); i++) {
			searchTracksRequest = spotifyApi.searchTracks(tracks.get(i).parseVideotitle()).build();
			System.out.println("\nSearching " + tracks.get(i).parseVideotitle());
			
			try {
				Paging<Track> trackPaging = searchTracksRequest.execute();
				String[] uris = {trackPaging.getItems()[0].getUri()};
				
				Thread.sleep(100);
				
				addToPlaylist(uris);
				System.out.println("added " + trackPaging.getItems()[0].getName());
			} catch(Exception e) {
				e.printStackTrace();
				System.err.println("Cant find " + tracks.get(i).parseVideotitle());
			}
		}
	}
	
	
	private AddTracksToPlaylistRequest addTracksToPlaylistRequest;
	
	private void addToPlaylist(String[] uris) throws SpotifyWebApiException, IOException {
		addTracksToPlaylistRequest = spotifyApi.addTracksToPlaylist(playlistId, uris).build();
		try {
			addTracksToPlaylistRequest.execute();
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	}
	
	private AuthorizationCodeUriRequest authorizationCodeUriRequest;
	
	public void getAuthorizeUri() throws IOException, URISyntaxException {
		authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("playlist-modify-public,playlist-modify-private,user-read-private").build();
		URI uri = authorizationCodeUriRequest.execute();
		
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf("win") >= 0) {
			Runtime rt = Runtime.getRuntime();
			rt.exec("rundll32 url.dll,FileProtocolHandler " + uri.toString());
			
		} else if(os.indexOf("mac") >= 0) {
			Runtime rt = Runtime.getRuntime();
			rt.exec("open " + uri.toString());
			
		} else if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			Runtime rt = Runtime.getRuntime();
			rt.exec("xdg-open " + uri.toString());
		}
	}
	
	private AuthorizationCodeRequest authorizationCodeRequest;
	
	private void getAuthorizeTokens() {
		authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
		
		try {
			AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

	      	spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
	      	spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
	    } catch (IOException | SpotifyWebApiException e) {
	    	System.out.println("Error: " + e.getMessage());
	    }
	}
}
