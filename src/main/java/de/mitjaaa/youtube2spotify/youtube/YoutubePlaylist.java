package de.mitjaaa.youtube2spotify.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class YoutubePlaylist {
	
	 /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private YouTube youtube;

    /**
     * Define a global variable that identifies the video that will be added
     * to the new playlist.
     */
    private String PLAYLIST_ID;

    public YoutubePlaylist(String PLAYLIST_ID) {
    	this.PLAYLIST_ID = PLAYLIST_ID;
    }
    
    
    /**
     * Authorize the user, create a playlist, and add an item to the playlist.
     *
     * @param args command line args (not used).
     */
    public void startYoutubeAPI() {

        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "playlistupdates");
            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("YTtoSpotify")
                    .build();

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }

    /**
     * Create a playlist item with the specified video ID and add it to the
     * specified playlist.
     *
     * @param playlistId assign to newly created playlistitem
     * @param videoId    YouTube video id to add to playlistitem
     * @throws InterruptedException 
     */
    public List<YTPlaylistItem> getPlaylistItems() throws IOException, InterruptedException {
    	
    	System.out.println("Getting all items from the YT-Playlist..");

    	String nextPage = "";
    	List<YTPlaylistItem> items = new LinkedList<YTPlaylistItem>();
    	
    	for(int i = 0; i < 4; i++) {
    		YouTube.PlaylistItems.List playlistItemsCommand = youtube.playlistItems().list("snippet,contentDetails");
	        playlistItemsCommand.setPlaylistId(PLAYLIST_ID);
	        playlistItemsCommand.setMaxResults((long) 50);
	        
	        if(i != 0) {
	        	playlistItemsCommand.setPageToken(nextPage);
	        }
	        
	        PlaylistItemListResponse returnedPlaylistItem = playlistItemsCommand.execute();
	        nextPage = returnedPlaylistItem.getNextPageToken();
	        
	        for(PlaylistItem item: returnedPlaylistItem.getItems())  {
	            YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet").setId(item.getSnippet().getResourceId().getVideoId());
	            VideoListResponse listResponse = listVideosRequest.execute();
	            
	            List<Video> videoList = listResponse.getItems();
	            
	            Video video = videoList.get(0);
	            VideoSnippet snippet = video.getSnippet();
	            
	            YTPlaylistItem pitem = new YTPlaylistItem(item.getSnippet().getTitle(), snippet.getChannelTitle());
	            
	            if(pitem.parseVideotitle() != null) items.add(pitem);
	        }
    	}
    	
    	System.out.println("Parsed all Youtube Items!");
        return items;
    }

}
