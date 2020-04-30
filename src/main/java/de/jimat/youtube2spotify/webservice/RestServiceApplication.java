package de.jimat.youtube2spotify.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

	public static void startService() {
		SpringApplication.run(RestServiceApplication.class);
	}
	
//	public static void main(String[] args) throws SpotifyWebApiException, IOException {
//		Spotify sp = new Spotify("ae0934dbb741437192fe6e13ef6f53cc", 
//								 "e29c878fa6524136aebc9d8128ee8a02", 
//								 "0SE62g5S5tSELK23maLBmU",
//								 "http://localhost:8080/greeting");
//		
//		startService();
//		sp.getAuthorizeUri();
//		
//	}

}
