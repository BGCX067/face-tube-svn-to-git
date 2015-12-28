package com.dnb.webmash.facetube.client.ui;


import java.util.List;

import com.dnb.webmash.facetube.shared.FBUser;
import com.google.gdata.client.youtube.YouTubeManager;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.youtube.client.YouTubeEmbeddedPlayer;

import com.google.gdata.client.Query;
import com.google.gdata.client.QueryPage;
import com.google.gdata.client.youtube.ui.YouTubeSearchPanel;
import com.google.gdata.client.youtube.ui.YouTubeSearchResultPanel;
import com.google.gdata.client.youtube.ui.YouTubeSearchPanel.SearchEvent;
import com.google.gdata.client.youtube.ui.YouTubeSearchResultPanel.VideoResolution;
import com.google.gwt.user.client.ui.Image;

/**
 * @author nic
 *	Displays a youtube API with controls
 */
public class TubePlayer extends Composite {
	YouTubeEmbeddedPlayer youTubeEmbeddedPlayer;
	private static TubePlayerUiBinder uiBinder = GWT
			.create(TubePlayerUiBinder.class);

	interface TubePlayerUiBinder extends UiBinder<Widget, TubePlayer> {
	}

	public TubePlayer(FBUser user) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public void loadTube(String vidID) {
		YouTubeEmbeddedPlayer youTubeEmbeddedPlayer = new YouTubeEmbeddedPlayer(vidID);
		RootPanel.get().remove(0);
		youTubeEmbeddedPlayer.setWidth("640px");
		youTubeEmbeddedPlayer.setHeight("480px");
		RootPanel.get().add(youTubeEmbeddedPlayer);
	}
	
	@Override
	public void setSize(String width, String height) {
		if (youTubeEmbeddedPlayer == null) youTubeEmbeddedPlayer = new YouTubeEmbeddedPlayer("LanCLS_hIo4"); 
		youTubeEmbeddedPlayer.setWidth(height);
		youTubeEmbeddedPlayer.setHeight(width);
		super.setSize(width, height);
	}
	
	public void searchNPlayFirst(String query) {
		YouTubeManager youTubeManager = new YouTubeManager();
		if (query!= null){
			youTubeManager.retrieveVideo(query, new AsyncCallback<VideoFeed>() {
					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Unable to obtain videos from youtube service", caught);					
					}
	
					@Override
					public void onSuccess(VideoFeed result) {
						List<VideoEntry> entries = 	result.getEntries();//TODO RETURN THIS
						VideoEntry fEntry = entries.get(0);
		                String videoId = fEntry.getMediaGroup().getVideoId();
		                loadTube(videoId);
					}
			});
		}
	}
	
	private static final String YOUTUBE_LOGO_STYLE = "youtube-logo";
    private static final String YOUTUBE_SEARCH_PANEL = "youtube-search-panel";
    
	public HorizontalPanel createSearchPanel() {
         return createSearchPanel(null);
	}
	public HorizontalPanel createSearchPanel(String query) {
		HorizontalPanel hp = new HorizontalPanel();
        Image img = new Image(YOUTUBE_LOGO_STYLE);
        img.setStyleName(YOUTUBE_SEARCH_PANEL);
        hp.add(img);

        final YouTubeSearchPanel youTubeSearchPanel = new YouTubeSearchPanel();
        if (query != null) {
                youTubeSearchPanel.setSearchText(query);
        }                       
        youTubeSearchPanel.addPagingHandler(new YouTubeSearchPanel.SearchHandler() {

                public void onPage(SearchEvent event) {
                        RootPanel.get().clear();
                        RootPanel.get().add(createSearchPanel(event.getSearch()));
                        YouTubeSearchResultPanel youTubeSearchResultPanel = new YouTubeSearchResultPanel() {
                                protected VideoResolution getVideoResolution() {
                                        return VideoResolution.MEDIUM;
                                };
                        };
                        youTubeSearchResultPanel.setStyleName(YOUTUBE_SEARCH_PANEL);
                        RootPanel.get().add(youTubeSearchResultPanel);
                        youTubeSearchResultPanel.showResults(event.getSearch(), new QueryPage(1, 5, Query.UNDEFINED));
                }
        });

        hp.add(youTubeSearchPanel);
        
        //RootPanel.get().remove(0);
		RootPanel.get().add(hp);

        return hp;
	}
}
