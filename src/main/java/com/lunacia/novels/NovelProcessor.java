package com.lunacia.novels;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class NovelProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setRetrySleepTime(1000)
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

	private final String LIST_URL = "http://www.xbiquge.la/xiaoshuodaquan/";
	private final String ARTICLE_URL = "http://www.xbiquge.la/\\d+/\\d+/";

	public void process(Page page) {
		//文章列表
		if (page.getUrl().regex(LIST_URL).match()) {
			page.addTargetRequests(page.getHtml()
					.xpath("//div[@class=\"novellist\"]").links().regex(ARTICLE_URL).all());
		} else {
			NovelInfo novelInfo = new NovelInfo();
			novelInfo.setId(page.getUrl().regex("\\d+").all().get(1));
			novelInfo.setAuthor(page.getHtml().xpath("//div[@id=\"info\"]/p/text()").regex("：.*").replace("：", "").get());
			novelInfo.setName(page.getHtml().xpath("//div[@id=\"info\"]/h1/text()").get());
			novelInfo.setIntro(page.getHtml().$("#intro p:nth-child(2)").replace("<\\/?p>", "").get());
			novelInfo.setImg_url(page.getHtml().$("#fmimg img").regex("[a-zA-z]+://[^\\s]*").replace("\"", "").get());
			novelInfo.setTitles(page.getHtml().xpath("//div[@id=\"list\"]/dl/dd/a/text()").all());
			novelInfo.setContent_urls(page.getHtml().$("#list dl dd a").regex("\\/\\d+\\/\\d+\\/\\d+.html").all());
			for (int i = 0; i < novelInfo.getContent_urls().size(); i++) {
				novelInfo.getContent_urls().set(i, "http://www.xbiquge.la" + novelInfo.getContent_urls().get(i));
			}
			if (novelInfo.getAuthor() != null)
				page.putField("novelInfo", novelInfo);
		}

	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new NovelProcessor())
				.addUrl("http://www.xbiquge.la/xiaoshuodaquan/")
				.addPipeline(new MysqlPipeline())
				.thread(5)
				.run();
	}
}
