package com.lunacia.novels;

import java.util.List;

public class NovelInfo {
	private String id;
	private String name;
	private String author;
	private String intro;
	private String img_url;
	private List<String> titles;
	private List<String> content_urls;

	public NovelInfo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getContent_urls() {
		return content_urls;
	}

	public void setContent_urls(List<String> content_urls) {
		this.content_urls = content_urls;
	}

	@Override
	public String toString() {
		return this.name + " " + this.author;
	}
}
