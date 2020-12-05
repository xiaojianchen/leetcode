package com.leetcode.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L_652_DuplicateSubtrees {

    public static void main(String[] args) {

    }
    // 记录重新的树，只出现一次。
    List<TreeNode> result = new ArrayList<>();

    // 记录子树出现的重复次数，把所有树都存储这里
    Map<String, Integer> dupCount = new HashMap<>();
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        traverse(root);
        return result;
    }

    /**
     * 还是老套路，先思考，对于某一个节点，它应该做什么。
     * 你需要知道以下两点：
     * 1、以我为根的这棵二叉树（子树）长啥样？--- 所以，把所有节点的子树全部序列化成字符串去比较
     * 2、以其他节点为根的子树都长啥样？ -- 以节点描述为key，出现次数为value。
     * 最关键的是为啥用后序，因为是左、右子树都遍历完，回到根节点这个时机做逻辑，只有这个时间点做逻辑，才知道该节点的树形结构
     */
    /* 辅助函数 */
    String traverse(TreeNode node) {
        if (node == null) {
            return  "#";
        }
        // 最关键的是为啥用后序，因为是左、右子树都遍历完，回到根节点这个时机做逻辑，只有这个时间点做逻辑，才知道该节点的树形结构
        String left = traverse(node.left);
        String right = traverse(node.right);
        String subTree = left + "," + right + "," + node.val;

        //先获取有没有存储过
        int feq = dupCount.getOrDefault(subTree, 0);
        // 说明之前有过一个，再来的就是重复的，然后把归回来路上的节点放进去
        if (feq == 1) {
            result.add(node);
        }
        dupCount.put(subTree, feq + 1);
        return subTree;
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
