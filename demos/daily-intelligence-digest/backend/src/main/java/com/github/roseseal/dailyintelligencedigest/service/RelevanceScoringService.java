package com.github.roseseal.dailyintelligencedigest.service;

import com.github.roseseal.dailyintelligencedigest.model.NormalizedItem;

import java.util.List;

public interface RelevanceScoringService {

	List<NormalizedItem> score(List<NormalizedItem> items);
}
