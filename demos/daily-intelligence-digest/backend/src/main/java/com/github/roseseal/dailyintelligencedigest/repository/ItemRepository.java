package com.github.roseseal.dailyintelligencedigest.repository;

import com.github.roseseal.dailyintelligencedigest.model.NormalizedItem;
import com.github.roseseal.dailyintelligencedigest.model.RawItem;

import java.util.List;

public interface ItemRepository {

	void saveRawItems(List<RawItem> items);

	void saveNormalizedItems(List<NormalizedItem> items);
}
