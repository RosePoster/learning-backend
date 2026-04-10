package com.github.roseseal.dailyintelligencedigest.digest;

import com.github.roseseal.dailyintelligencedigest.model.DigestEntry;

import java.util.List;

public interface MarkdownRenderer {

	String render(List<DigestEntry> entries);
}
