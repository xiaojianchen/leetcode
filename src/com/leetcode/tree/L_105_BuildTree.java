package com.leetcode.tree;

import java.util.*;
//前序遍历 preorder = [3,9,20,15,7]
//中序遍历 inorder = [9,3,15,20,7]
//        3
//        / \
//        9  20
//        /  \
//        15   7
public class L_105_BuildTree {

    public static void main(String[] args) {
        int[] preorder = new int[]{3,9,20,15,7};
        int[] inorder = new int[]{9,3,15,20,7};
        Node node = buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
        levelV2(node);
    }

    /**
     * 注意，它们已经是索引了，是左右闭合的。
     * @param preorder
     * @param preStart
     * @param preEnd
     * @param inorder
     * @param inStart
     * @param inEnd
     */
    private static Node buildTree(int[] preorder, int preStart, int preEnd,
                                  int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        // 1. 先序的第一个节点即为根节点
        int rootVal = preorder[preStart];
        Node head = new Node(rootVal);
        // 2. 再通过这个去寻找中序节点中，根节点在哪里，这里就能分出左右子树了。所以是前/后序 + 中序是可以确立一棵树的，因为中序可以分出左右子树
        int index = -1;
        for(int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootVal) {
                index = i;
                break;
            }
        }
        System.out.println("index: " + index + " rootVal: " + rootVal);
        if (index < 0) {
            return null;
        }
        // 3. 根据这个可以得出左、右子树分别有多个节点
        int leftTreeSize = index - inStart;

        head.left = buildTree(preorder, preStart + 1, preStart + leftTreeSize,
                              inorder,  inStart, index - 1 );
        head.right = buildTree(preorder, preStart + leftTreeSize + 1, preEnd,
                              inorder, index + 1, inEnd);

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
