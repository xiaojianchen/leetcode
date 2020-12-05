package com.leetcode.tree;

import java.util.*;

//  输入：[3,2,1,6,0,5]
//  输出：返回下面这棵树的根节点：
//
//        6
//        /   \
//        3     5
//        \    /
//        2  0
//        \
//        1
public class L_654_MaxTree {

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,6,0,5};
        Node node = constructMaximumBinaryTree(nums, 0, nums.length - 1);
        levelV2(node);
    }

    public static Node constructMaximumBinaryTree(int[] nums, int low, int high) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        if (low > high) {
            return null;
        }

        // 1. 找到最大值及索引
        int maxValue = Integer.MIN_VALUE;
        int index = -1;
        // 注意是i <= high，这里已经只剩索引，上面size转索引已经减去1了。
        for (int i = low; i <= high; i++) {
            int value = nums[i];
            if (value > maxValue) {
                maxValue = value;
                index = i;
            }
        }
        if (index == -1) {
            return null;
        }
        System.out.println("index:" + index +" value:" + maxValue);
        Node head = new Node(maxValue);
        // 2. 递归调用给左右子树赋值
        head.left = constructMaximumBinaryTree(nums, low, index - 1);
        head.right = constructMaximumBinaryTree(nums, index + 1, high);

        return head;
    }




    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void levelV2(Node head) {
        if (head == null) {
            return;
        }
        List<List<Integer>> resultList = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            // 存储每一个层次的节点，把for循环结束后，添加到要输出的list中。
            List<Integer> levelList = new ArrayList<>();
            // 当前层的size
            int queueSize = queue.size();
            // 遍历当前层的节点，并打印
            for (int i = 0; i < queueSize; i++) {
                Node cur = queue.poll();
                levelList.add(cur.value);
                // 在遍历的过程中，把当前层节点的下一层也加到队列中来。
                // 你可以理解为第一层size为1,接下来最大为2,4,8,16
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            // 遍历完当前层，在上面循环里，已把所有下一层放到队列里，可以继续遍历
            resultList.add(levelList);
        }
        for (List<Integer> list : resultList) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }
}
