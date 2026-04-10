package com.github.roseseal.dailyintelligencedigest.model;

import java.time.LocalDateTime;

public record RawItem(
		String id,
		String source,
		String title,
		String url,
		String content,
		LocalDateTime publishTime
) {
}
