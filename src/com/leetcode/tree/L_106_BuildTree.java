package com.leetcode.tree;

import java.util.*;

//中序遍历 inorder = [9,3,15,20,7]
//后序遍历 postorder = [9,15,7,20,3]
//             3
//            / \
//            9  20
//            /  \
//            15   7
public class L_106_BuildTree {

    public static void main(String[] args) {
        int[] postorder = new int[]{9,15,7,20,3};
        int[] inorder = new int[]{9,3,15,20,7};
        Node node = buildTree(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
        levelV2(node);
    }

    private static Node buildTree(int[] postorder, int posStart, int posEnd, int[] inorder, int inStart, int inEnd) {
        if (posStart > posEnd || inStart > inEnd) {
            return null;
        }
        //1.找出根节点
        int rootVal = postorder[posEnd];
        Node node = new Node(rootVal);
        //2.找出中序中的root index，并且分出左右节点个数
        int index = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (rootVal == inorder[i]) {
                index = i;
                break;
            }
        }
        int leftTreeSize = index - inStart;
        // 后序第一个节点就是左子树的，所以要注意posStart + leftTreeSize - 1
        node.left = buildTree(postorder, posStart, posStart + leftTreeSize - 1,
                              inorder, inStart, index - 1);
        node.right = buildTree(postorder, posStart + leftTreeSize, posEnd - 1,
                              inorder, index + 1, inEnd);
        return node;
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
