package com.lunacia.novels;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.Map;

public class MysqlPipeline implements Pipeline {

	private NovelInfoDAO novelInfoDAO = new NovelInfoDAO();

	public MysqlPipeline() {
	}

	public void process(ResultItems resultItems, Task task) {
		Iterator iterator = resultItems.getAll().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, NovelInfo> entry = (Map.Entry) iterator.next();
			System.out.println(entry.getValue());
			novelInfoDAO.insert(entry.getValue());
		}
	}
}
