# YTtoSpotify
This project transfers a Youtube playlist from your account to a Spotify playlist.

## How to use it?
It's almost ready to use. You just need to do a few things:
1. create a new project or choose an existing project [here](https://console.developers.google.com/project/_/apiui/credential).
    * then you need to copy and paste the client id and secret 
      from your Google project to the client_secrets.json in src/main/resources/
2. visit [this site](https://developer.spotify.com/dashboard/applications), log in, and create an app.
    * then go to the settings and add http://localhost:8080/api as a new redirect url.
    * After that copy the client id and secret of your spotify app and paste them in StartTransfer.java in line 25 and 26.
3. copy the youtube playlistid (you can find it in the link of your playlist) and paste it in StartTransfer.java in line 23.
4. create or an existing playlist from spotify and copy the id (you can find it in the link or URI) and paste it StartTransfer.java in line 27.

when you've done these steps you can compile & start the project!

## Requirements
For Youtube:
* Guava v23.0
* Google API services - youtube
* Google API services - youtube analytics
* Google API services - youtube reporting
* Google http client  - jackson2
* Google oauth client - jetty
* Google collections

For Spotify:
* Spotify web API v4.3.0
* Spring boot starter web

---

*Note: those are already included in the build.gradle file!*

## Example
I've transfered [my youtube-playlist](https://www.youtube.com/playlist?list=PLaJMPdUYqLLws7HlLN9SgELyv0xkm-NMa) with over 280 items to [my spotify-playlist](https://open.spotify.com/playlist/0ihLi9e9oDKTjmgEb6mHfL?si=RDhiNiwlTLmajgZkJD_gOw) which has (after the transfer) 268 items. Spotify couldn't find a few items because some songs aren't on there or the youtube video had a weird name.

### Note
YTtoSpotify currently doesn't transfer to 100% correctly and has sometimes some false-postives in the transfered playlist. That is because some songs on Youtube have some unknown characters, a weird title, or something else. The program tries to filter stuff like this out but sometimes spotify just can't find the song. 


## Licensing
The source code is licensed under GPL v3. License is available [here](https://github.com/Mitjaaa/YTtoSpotify/blob/master/LICENSE).

