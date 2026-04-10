package com.github.roseseal.dailyintelligencedigest.service;

import com.github.roseseal.dailyintelligencedigest.model.DigestEntry;
import com.github.roseseal.dailyintelligencedigest.model.NormalizedItem;

import java.util.List;

public interface WeeklyRecommendationService {

	List<DigestEntry> recommend(List<NormalizedItem> items);
}
