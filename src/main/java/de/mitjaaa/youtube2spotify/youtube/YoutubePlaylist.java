package de.mitjaaa.youtube2spotify.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class YoutubePlaylist {
	
	 /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private YouTube youtube;

    /**
     * Define a global variable that holds the ID of the youtube playlist.
     */
    private String PLAYLIST_ID;

    public YoutubePlaylist(String PLAYLIST_ID) {
    	this.PLAYLIST_ID = PLAYLIST_ID;
    }
    
    
    public void startYoutubeAPI() {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "YTtoSpotify");
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

    public List<YTPlaylistItem> getPlaylistItems() throws IOException, InterruptedException {
    	int videos = scanPlaylistSizeFromUser();
    	System.out.println("getting all items from the playlist..");

    	String nextPage = "";
    	List<YTPlaylistItem> items = new LinkedList<YTPlaylistItem>();
    	
    	List<String> options1 = new LinkedList<String>();
    	options1.add("snippet");
    	options1.add("contentDetails");
    	
    	List<String> options2 = new LinkedList<String>();
    	options2.add("snippet");
    	
    	// problem: its repeating the songs it added already
    	for(int i = 0; i < 10; i++) {
    		YouTube.PlaylistItems.List playlistItemsCommand = youtube.playlistItems().list(options1);
	        playlistItemsCommand.setPlaylistId(PLAYLIST_ID);
	        
	        if(videos > 50) {
	        	playlistItemsCommand.setMaxResults((long) 50);
	        	videos = videos - 50;
	        	
	        } else {
	        	playlistItemsCommand.setMaxResults((long) videos);
	        	videos = videos - videos;
	        }
	        
	        
	        if(i != 0) {
	        	playlistItemsCommand.setPageToken(nextPage);
	        }
	        
	        PlaylistItemListResponse returnedPlaylistItem = playlistItemsCommand.execute();
	        nextPage = returnedPlaylistItem.getNextPageToken();
	        
	        for(PlaylistItem item: returnedPlaylistItem.getItems())  {
	            List<String> id = new LinkedList<String>();
	            id.add(item.getSnippet().getResourceId().getVideoId());
	        	
	        	YouTube.Videos.List listVideosRequest = youtube.videos().list(options2).setId(id);
	            VideoListResponse listResponse = listVideosRequest.execute();
	            
	            List<Video> videoList = listResponse.getItems();
	            
	            Video video = videoList.get(0);
	            VideoSnippet snippet = video.getSnippet();
	            
	            YTPlaylistItem pitem = new YTPlaylistItem(item.getSnippet().getTitle(), snippet.getChannelTitle());
	            
	            if(pitem.parseVideotitle() != null) items.add(pitem);
	        }
	        
	        if(videos == 0) break;
    	}
    	
    	System.out.println("parsed all items!");
        return items;
    }


	private int scanPlaylistSizeFromUser() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("\nenter the size of your youtube-playlist: ");
		
		try {
			return Integer.parseInt(scanner.nextLine());
		
		} catch(Exception e) {
			System.err.println("\nplease enter a valid number!");
			return scanPlaylistSizeFromUser();
		
		} finally {
			scanner.close();
		}
	}

}
