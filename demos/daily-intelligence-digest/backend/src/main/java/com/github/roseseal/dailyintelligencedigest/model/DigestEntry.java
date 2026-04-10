package com.github.roseseal.dailyintelligencedigest.model;

public record DigestEntry(
		String title,
		String summary,
		String url,
		String source
) {
}
