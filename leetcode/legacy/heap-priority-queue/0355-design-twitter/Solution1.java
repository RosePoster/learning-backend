class Tweet {
    int id;
    int time;
    int size;
    Tweet next;

    Tweet(int id, int time) {
        this.id = id;
        this.time = time;
        this.size = 1;
    }
}

class Twitter {
    Map<Integer, Set<Integer>> followees;
    Map<Integer, Tweet> tweetsByUser;
    int time;

    public Twitter() {
        followees = new HashMap<>();
            for(int i = 1; i < 501; i++) {
            Set<Integer> set = new HashSet<>();
            set.add(i);
            followees.put(i, set);
        }
        tweetsByUser = new HashMap<>();
        time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, time++);
        Tweet tweets = tweetsByUser.getOrDefault(userId, null);
        tweet.next = tweets;
        tweet.size = (tweets == null ? 0 : tweets.size) + 1;
        tweets = tweet;
        if(tweets.size > 10) {
            Tweet curr = tweets;
            while(curr.next.next != null) {
                curr = curr.next;
            }
            curr.next = null;
            tweets.size = 10;
        }
        tweetsByUser.put(userId, tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> feedHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.time, a.time)
        );

        for(int followeeId : followees.get(userId)) {
            Tweet t = tweetsByUser.get(followeeId);
            if(t != null) {
                feedHeap.offer(t);
            }
        }

        List<Integer> newsFeeds = new ArrayList<>();
        while(!feedHeap.isEmpty() && newsFeeds.size() < 10) {
            Tweet t = feedHeap.poll();
            newsFeeds.add(t.id);
            t = t.next;
            if(t != null) {
                feedHeap.offer(t);
            }
        }

        return newsFeeds;
    }
    
    public void follow(int followerId, int followeeId) {
        followees.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId) { 
            return; 
        }
        Set<Integer> set = followees.get(followerId);
        if (set != null) {
            set.remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */