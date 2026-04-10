package com.github.roseseal.dailyintelligencedigest.digest;

import com.github.roseseal.dailyintelligencedigest.model.DigestEntry;
import com.github.roseseal.dailyintelligencedigest.model.NormalizedItem;

import java.util.List;

public interface WeeklyDigestGenerator {

	List<DigestEntry> generate(List<NormalizedItem> items);
}
