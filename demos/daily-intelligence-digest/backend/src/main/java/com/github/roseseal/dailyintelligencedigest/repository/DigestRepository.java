package com.github.roseseal.dailyintelligencedigest.repository;

public interface DigestRepository {

	void saveDailyDigest(String content);

	void saveWeeklyDigest(String content);
}
