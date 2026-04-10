package com.github.roseseal.dailyintelligencedigest.model;

import java.time.LocalDateTime;
import java.util.List;

public record NormalizedItem(
		String id,
		String title,
		String summary,
		List<String> tags,
		String source,
		double score,
		LocalDateTime createdAt
) {
}
