package com.lunacia.bili;

public class VideoInfo {
	private long Id;
	private String title;
	private String title_img;
	private String author;
	private String intro;
	private String view;
	private String date;
	private String dm;

	public VideoInfo () {
	}

	public long getId () {
		return Id;
	}

	public void setId (long id) {
		Id = id;
	}

	public String getTitle () {

		return title;
	}

	public void setTitle (String title) {
		this.title = title;
	}

	public String getTitle_img () {
		return title_img;
	}

	public void setTitle_img (String title_img) {
		this.title_img = title_img;
	}

	public String getAuthor () {
		return author;
	}

	public void setAuthor (String author) {
		this.author = author;
	}

	public String getIntro () {
		return intro;
	}

	public void setIntro (String intro) {
		this.intro = intro;
	}

	public String getView () {
		return view;
	}

	public void setView (String view) {
		this.view = view;
	}

	public String getDate () {
		return date;
	}

	public void setDate (String date) {
		this.date = date;
	}

	public String getDm () {
		return dm;
	}

	public void setDm (String dm) {
		this.dm = dm;
	}

	@Override
	public String toString () {
		return "VideoInfo{" +
				"title='" + title + '\'' +
				", title_img='" + title_img + '\'' +
				", author='" + author + '\'' +
				", intro='" + intro + '\'' +
				", view='" + view + '\'' +
				", date='" + date + '\'' +
				", dm='" + dm + '\'' +
				'}';
	}
}
