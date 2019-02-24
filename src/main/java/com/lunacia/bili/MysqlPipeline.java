package com.lunacia.bili;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.Map;

public class MysqlPipeline implements Pipeline {
	VideoInfoDAO videoInfoDAO = new VideoInfoDAO();

	public void process (ResultItems resultItems, Task task) {
		Iterator it = resultItems.getAll().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, VideoInfo> entry = (Map.Entry) it.next();
			VideoInfo v = entry.getValue();
			System.out.println(v);
			videoInfoDAO.insert(v);
		}
	}

	public MysqlPipeline () {
	}
}
