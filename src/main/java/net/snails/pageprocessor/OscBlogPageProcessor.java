package net.snails.pageprocessor;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.data.utils.BloomFilter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author krisjin
 */
public class OscBlogPageProcessor implements PageProcessor {

	private Site site = Site.me().setDomain("my.oschina.net");
	AtomicInteger counts = new AtomicInteger(0);

	public void process(Page page) {
		List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/\\w+/blog/\\d+").all();
		
		page.addTargetRequests(links);

		page.putField("title", page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1/text()")
				.toString());
		page.putField("content", page.getHtml().xpath("//div[@class='BlogContent']/outerHtml()").toString());
		if (page.getResultItems().get("title") == null) {
			page.setSkip(true);
		}

		page.putField("date", page.getHtml().xpath("//div[@class='BlogStat']/text()").toString());

		page.putField("url", page.getRequest().getUrl());

	}

	public Site getSite() {
		return site;
	}

}
