package com.leetcode.tree;

import java.util.*;

public class LevelTraversalBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void level(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }


    /**
     * 两种方式层次遍历的区别，上面的简单层次遍历每次只会poll出来一个节点。
     * 而这个V2的层次遍历会写个for循环，把之前已存在队列里的全部遍历完，注意是之前，
     * 也就是说把当前层的节点遍历完，同时在for循环里，它也把下一层的节点全部放了进去，这样就
     * 形成了：
     * for循环的作用是遍历当前层，并把下一层节点添加进来
     * while循环的意义是，当前层遍历完后，开启下一层遍历。
     * 这里有一个重点的环节就是queueSize,它是在for循环之前的size，因为在for循环中size会一直变，这也是
     * 能够衔接两个循环的关键所在。
     * 当然如果是N叉树，把添加左/右子树的地方也变成一个循环。因为一个节点可能有多个子树。而不是只有左/右。
     * @param head
     */
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

    /**
     * 本质就是把每一层级的最右边节点打印出来，这不就是层级遍历的小变种吗
     * @param head
     */
    public static void rightView_199(Node head) {
        if (head == null) {
            return;
        }
        List<Integer> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                Node cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
                if (i == queueSize - 1) {
                    result.add(cur.value);
                }
            }
        }

        for (Integer integer : result) {
            System.out.print(integer + ",");
        }
    }

    /**
     * 在for循环里遍历每一层，对当前层所有数据相加然后求平均数即可。
     * @param head
     */
    public static void averageOfLevels_637(Node head) {
        if (head == null) {
            return;
        }
        List<Double> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            double sumLevel = 0.0;//每一层的数值和
            double averageLevel = 0.0;//每一层的平均值
            for (int i = 0; i < queueSize; i++) {
                Node cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
                sumLevel += cur.value;
            }
            averageLevel = sumLevel / queueSize;
            result.add(averageLevel);

        }
        for (Double d : result) {
            System.out.print(d+"    ");
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        levelV2(head);
//        rightView_199(head);
        averageOfLevels_637(head);
    }




}
