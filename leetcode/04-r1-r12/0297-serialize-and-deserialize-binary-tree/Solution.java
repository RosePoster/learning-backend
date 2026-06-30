public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        queue.offer(root);

        while (!queue.isEmpty()) { 
            TreeNode curr = queue.poll();
            if (curr == null) sb.append('#').append('/');
            else {
                sb.append(curr.val).append('/');
                queue.offer(curr.left);
                queue.offer(curr.right);
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        int[] index = new int[]{0};
        Integer num = getNum(data, index);
        if (num == null) return null;
        TreeNode root = new TreeNode(num);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (index[0] < data.length()) {
            TreeNode curr = queue.poll();
            num = getNum(data, index);
            if (num != null) {
                curr.left = new TreeNode(num);
                queue.offer(curr.left);
            }
            num = getNum(data, index);
            if (num != null) {
                curr.right = new TreeNode(num);
                queue.offer(curr.right);
            }
        }
        return root;
    }

    private Integer getNum(String data, int[] index) {
        int i = index[0];

        if (data.charAt(i) == '#') {
            index[0] = i + 2; // 跳过 "#/"
            return null;
        }

        int sign = 1;
        if (data.charAt(i) == '-') {
            sign = -1;
            i++;
        }

        int num = 0;
        while (data.charAt(i) != '/') {
            num = num * 10 + (data.charAt(i) - '0');
            i++;
        }

        index[0] = i + 1; // 跳过 "/"
        return sign * num;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));