package com.github.roseseal.dailyintelligencedigest.fetcher;

import com.github.roseseal.dailyintelligencedigest.model.RawItem;

import java.util.List;

public interface SourceFetcher {

	List<RawItem> fetch();
}
