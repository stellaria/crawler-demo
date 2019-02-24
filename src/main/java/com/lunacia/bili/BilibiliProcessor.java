package com.lunacia.bili;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;

public class BilibiliProcessor implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setTimeOut(1000)
			.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");

	private long av = 1;
	private int flag = 0;
	private String videoUrl = "https://www.bilibili.com/video/av";
	private String JsonUrl = "https://api.bilibili.com/x/web-interface/archive/stat?aid=";

	public void process (Page page) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(JsonUrl + av);
		JsonObject json = null;
		try {
			HttpResponse res = httpClient.execute(httpGet);
			String str = EntityUtils.toString(res.getEntity());
			json = (JsonObject) new JsonParser().parse(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!json.get("message").getAsString().equals("0")) {
			av++;
			flag++;
			if (flag < 233) //简单判断av号上限
				page.addTargetRequest(videoUrl + av);
		} else {
			//解析页面
			VideoInfo videoInfo = new VideoInfo();
			videoInfo.setId(av);
			videoInfo.setTitle(page.getHtml().xpath("//*[@id=\"viewbox_report\"]/h1/span/text()").get());
			videoInfo.setTitle_img(page.getHtml().xpath("/html/head/meta[10]").regex("[a-zA-Z]+://[^\\s\\\"\\>]*").get());
			videoInfo.setAuthor(page.getHtml().xpath("//*[@id=\"v_upinfo\"]/div[2]/div/a[1]/text()").get());
			videoInfo.setIntro(page.getHtml().xpath("//*[@id=\"v_desc\"]/div").get()
					.replaceAll("<\\w+\\s.*\\\">", "")
					.replaceAll("<\\/\\w+>", "")
					.replace("\n", ""));
			videoInfo.setDate(page.getHtml().xpath("//*[@id=\"viewbox_report\"]/div[1]/time/text()").get());

			//httpClient 请求播放量和弹幕量的json
			JsonObject data = null;
			try {
				HttpResponse res = httpClient.execute(httpGet);
				String str = EntityUtils.toString(res.getEntity());
				json = (JsonObject) new JsonParser().parse(str);
				data = json.get("data").getAsJsonObject();
				videoInfo.setView(data.get("view").getAsString());
				videoInfo.setDm(data.get("danmaku").getAsString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			page.putField("videoInfo", videoInfo);
			av++;
			flag = 0;
			page.addTargetRequest(videoUrl + av);
		}

//		System.out.println(page.getUrl());
//		if (!json.get("message").getAsString().equals("0")) {
//			System.out.println(av);
//		}
//		av++;
//		page.addTargetRequest(videoUrl + av);

	}

	public Site getSite () {
		return site;
	}

	public static void main (String[] args) {
		Spider.create(new BilibiliProcessor())
				.addUrl("https://www.bilibili.com/video/av1")
				.addPipeline(new MysqlPipeline())
				.run();
//		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//		HttpGet httpGet = new HttpGet("https://api.bilibili.com/x/web-interface/archive/stat?aid=1");
//		JsonObject json = null;
//		try {
//			HttpResponse res = httpClient.execute(httpGet);
//			String str = EntityUtils.toString(res.getEntity());
//			json = (JsonObject) new JsonParser().parse(str);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(json.get("message").getAsString());
	}
}
