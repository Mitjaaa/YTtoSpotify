package de.jimat.youtube2spotify.youtube;

import java.math.BigInteger;

public class YTPlaylistItem {
	
	private String videotitle;
	private String channelname;

	public YTPlaylistItem() {}
	
	public YTPlaylistItem(String videotitle, String channelname) {
		this.videotitle = videotitle;
		this.channelname = channelname;
	}
	
	
	public String parseVideotitle() {
		String parsedTitle = videotitle;
		
		if(!parsedTitle.matches("[\\w\\Q!\"#$%&'’()*+,-./ ?–:;<=>?@[\\]^_`{|}~\\E]+")) return null;
		
		
		if(parsedTitle.contains(" - ")) {
			parsedTitle = parsedTitle.replace(" - ", " ");
		}
		
		if(parsedTitle.contains(" – ")) {
			parsedTitle = parsedTitle.replace(" – ", " ");
		}
		
		if(parsedTitle.contains(" | ")) {
			parsedTitle = parsedTitle.replaceAll(" | ", " ");
		}
		
		if(parsedTitle.contains("-")) {
			parsedTitle = parsedTitle.replace("-", " ");
		}
		
		if(parsedTitle.contains("\"")) {
			parsedTitle = parsedTitle.replaceAll("\"", "");
		}
		
		if(parsedTitle.contains("(")) {
			String[] splitTitle = parsedTitle.split("\\(");
			parsedTitle = splitTitle[0];
		}
		
		if(parsedTitle.contains("[")) {
			String[] splitTitle = parsedTitle.split("\\[");
			parsedTitle = splitTitle[0];
		}
		
		if(parsedTitle.contains("ft.")) {
			parsedTitle = parsedTitle.replace(" ft. ", " ");
		}
		
		if(parsedTitle.contains("feat.")) {
			parsedTitle = parsedTitle.replace(" feat. ", " ");
		}
		
		if(parsedTitle.contains("with")) {
			String[] splitTitle = parsedTitle.split("with");
			parsedTitle = splitTitle[0];
		}
		
		if(parsedTitle.contains("lyrics")) {
			parsedTitle = parsedTitle.replace("lyrics", " ");
		}
		
		if(parsedTitle.contains("Lyrics")) {
			parsedTitle = parsedTitle.replace("Lyrics", " ");
		}
		
		if(parsedTitle.contains(".mp4")) {
			parsedTitle = parsedTitle.replace(".mp4", " ");
		}
		
		if(parsedTitle.contains(".mp3")) {
			parsedTitle = parsedTitle.replace(".mp3", " ");
		}
		
		
		char startWithDigit = parsedTitle.charAt(0);
        if(Character.isDigit(startWithDigit)) {
			parsedTitle = parsedTitle.replace(parsedTitle.split(" ")[0], "");
		}
        
        if(parsedTitle.startsWith(" ")) {
        	parsedTitle = parsedTitle.substring(1);
        }
		
		parsedTitle = detectOneWord(parsedTitle);
		
		if(parsedTitle.contains(" - Topic")) {
			parsedTitle = parsedTitle.replace(" - Topic", " ");
		}
		
		if(parsedTitle.contains("by")) {
			String[] splitTitle = parsedTitle.split("by");
			parsedTitle = splitTitle[1] + " " + splitTitle[0];
		}
		
		
		if(parsedTitle.contains("  ")) {
        	parsedTitle = parsedTitle.replace("  ", " ");
		}
        
        if(parsedTitle.endsWith(" ")) {
        	parsedTitle = parsedTitle.substring(0, parsedTitle.length()-1);
        }
		
		return parsedTitle;
	}

	
	private String detectOneWord(String parsedTitle) {
		String[] splitTitle = parsedTitle.split(" ");
		boolean oneWord = false;
		
		if(splitTitle.length > 1) {
			int counter = 0;
			
			for(int i = 0; i < splitTitle.length; i++) {
				if(!splitTitle[i].equals(" ") || !splitTitle[i].isEmpty()) {
					counter++;
				}
			}
			
			if(counter <= 1) {
				oneWord = true;
			}
			
		} else {
			oneWord = true;
		}
		
		
		if(oneWord) parsedTitle = parseChannelname() + " " + parsedTitle;
		return parsedTitle;
	}

	private String parseChannelname() {
		String parsedChannelname = channelname;
		
		if(parsedChannelname.contains("VEVO")) {
			parsedChannelname.replace("VEVO", "");
		}
		
		return parsedChannelname;
	}

	public String getVideotitle() {
		return videotitle;
	}

	public void setVideotitle(String videotitle) {
		this.videotitle = videotitle;
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	
	

}
