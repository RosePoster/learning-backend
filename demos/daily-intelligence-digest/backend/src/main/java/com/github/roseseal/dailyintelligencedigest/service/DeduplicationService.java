package com.github.roseseal.dailyintelligencedigest.service;

import com.github.roseseal.dailyintelligencedigest.model.NormalizedItem;

import java.util.List;

public interface DeduplicationService {

	List<NormalizedItem> deduplicate(List<NormalizedItem> items);
}
