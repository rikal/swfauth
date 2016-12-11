package com.swiffshot.media.model;

import java.sql.Timestamp;
import java.util.List;

public class Video
{
	private String id;
	private Timestamp time;
	private int seconds;
	private String caption;
	private String url;
	private String thumbnailUrl;
	private int size;
	private String author;
	private List<String> tagList;
	private List<String> recipients;
	private List<String> views;
	private List<String> likes;
	private List<String> laughs;
	private List<String> cries;
	private List<String> disgusts;
	private List<String> haters;
}
