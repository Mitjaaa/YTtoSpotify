# YTtoSpotify
This project transfers a Youtube playlist from your account to Spotify.

## How to use it?
It's almost ready to use. You just need to do a few things:
1. create a new project or choose an existing project [here](https://console.developers.google.com/project/_/apiui/credential).
    * then you need to copy and paste the client id and secret 
      from your Google project to the client_secrets.json in src/main/resources/
2. visit [this](https://developer.spotify.com/dashboard/applications) site, log in, and create an app.
    * then go to the settings and add http://localhost:8080/api as a new redirect url.
    * After that copy the client id and secret of your spotify app and paste them in StartTransfer.java in line 25 and 26.
3. copy the youtube playlistid (you can find it in the link of your playlist) and paste it in StartTransfer.java in line 23.
4. create or an existing playlist from spotify and copy the id (you can find it in the link or URI) and paste it StartTransfer.java in line 27.

After you've done these steps you can compile & start the project!

## Licensing
The source code is licensed under GPL v3. License is available [here](https://github.com/Mitjaaa/YTtoSpotify/blob/master/LICENSE)
