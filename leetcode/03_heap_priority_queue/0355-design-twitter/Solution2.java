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
    boolean[][] followees;
    Tweet[] tweetsByUser;
    int time;

    public Twitter() {
        followees = new boolean[501][501];
        for(int i = 1; i < 501; i++) {
            followees[i][i] = true;
        }
        tweetsByUser = new Tweet[501];
        time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, time++);
        Tweet tweets = tweetsByUser[userId];
        tweet.next = tweets;
        int nextSize = (tweets == null ? 0 : tweets.size);
        tweet.size = nextSize + 1;
        tweets = tweet;
        if(tweets.size > 10) {
            Tweet curr = tweets;
            while(curr.next.next != null) {
                curr = curr.next;
            }
            curr.next = null;
            tweets.size = 10;
        }
        tweetsByUser[userId] = tweet;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> feedHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.time, a.time)
        );

        for(int followeeId = 1; followeeId < 501; followeeId++) {
            if(followees[userId][followeeId] == true) {
                Tweet t = tweetsByUser[followeeId];
                if(t != null) {
                    feedHeap.offer(t);
                }
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
        followees[followerId][followeeId] = true;
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            return;
        }
        followees[followerId][followeeId] = false;
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