package com.leetcode.tree;
//中序遍历 inorder = [9,3,15,20,7]
//后序遍历 postorder = [9,15,7,20,3]
//        3
//        / \
//        9  20
//           /  \
//          15   7
public class L_106_BuildTree {

    public static void main(String[] args) {
        int[] posOrder = new int[]{9,15,7,20,3};
        int[] inOrder = new int[]{9,3,15,20,7};
        TreeNode head = buildTree(posOrder, 0, posOrder.length - 1, inOrder, 0, inOrder.length - 1);
    }

    private static TreeNode buildTree(int[] posOrder, int posStart, int posEnd,
                                      int[] inOrder, int inStart, int inEnd) {
        if (posStart > posEnd || inStart > inEnd) {
            return null;
        }
        // 1. 根据后序找到根节点
        int rootVal = posOrder[posEnd];
        TreeNode head = new TreeNode(rootVal);
        // 2. 根据rootVal在中序中找到根节点位置，从而找到左、右子树数量
        int index = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrder[i] == rootVal) {
                index = i;
                break;
            }
        }
        System.out.println("index: " + index + " value: " + rootVal);
        if (index < 0) {
            return null;
        }
        // 3.找出后序的左右子树分割点
        int leftTreeSize = index - inStart;
        // 注意：posStart + leftTreeSize - 1 。因为第一个就是左子树。
        head.left = buildTree(posOrder, posStart, posStart + leftTreeSize - 1,
                             inOrder, inStart, index - 1);
        head.right = buildTree(posOrder, posStart + leftTreeSize, posEnd - 1,
                               inOrder, index + 1, inEnd);
        return head;
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
