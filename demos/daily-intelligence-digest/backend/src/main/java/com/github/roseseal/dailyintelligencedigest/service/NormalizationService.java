package com.github.roseseal.dailyintelligencedigest.service;

import com.github.roseseal.dailyintelligencedigest.model.NormalizedItem;
import com.github.roseseal.dailyintelligencedigest.model.RawItem;

import java.util.List;

public interface NormalizationService {

	List<NormalizedItem> normalize(List<RawItem> rawItems);
}
