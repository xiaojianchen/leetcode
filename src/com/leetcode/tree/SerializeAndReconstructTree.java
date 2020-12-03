package com.leetcode.tree;

import java.util.*;

public class SerializeAndReconstructTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
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
        Queue<String> queue = preSeries(head);
        Queue<String> queue2 = posSeries(head);
        Queue<String> queue3 = levelSeries(head);
        for (String s : queue) {
            System.out.print(s+",");
        }
        System.out.println();
        for (String s : queue2) {
            System.out.print(s+",");
        }
        System.out.println();
        for (String s : queue3) {
            System.out.print(s+",");
        }
        System.out.println();
        Node headPre = buildByPreQueue(queue);
        levelV2(headPre);

        System.out.println();

        Node headPost = buildByPostQueue(queue2);
        levelV2(headPost);

        System.out.println();

        Node headLevel = buildByLevel(queue3);
        levelV2(headLevel);
    }

    public static Queue<String> preSeries(Node head) {
        Queue<String> queue = new LinkedList<>();
        pres(head, queue);
        return queue;
    }

    public static void pres(Node head, Queue<String> queue) {
        if (head == null) {
            queue.add(null);
        } else {
            queue.add(String.valueOf(head.value));
            pres(head.left, queue);
            pres(head.right, queue);
        }
    }

    public static Queue<String> posSeries(Node head) {
        Queue<String> queue = new LinkedList<>();
        poss(head, queue);
        return queue;
    }

    public static void poss(Node head, Queue<String> queue) {
        if (head == null) {
            queue.add(null);
        } else {
            poss(head.left, queue);
            poss(head.right, queue);
            queue.add(String.valueOf(head.value));
        }
    }

    /**
     * 二叉树序列化其实就是在遍历(深度、广度)的时侯，把空结点加进来，
     * @param head
     * @return
     */
    public static Queue<String> levelSeries(Node head) {
        Queue<String> result = new LinkedList<>();
        if (head == null) {
            return result;
        }
        Queue<Node> treeQueue = new LinkedList<>();
        treeQueue.add(head);
        result.add(String.valueOf(head.value));
        while (!treeQueue.isEmpty()) {
            Node curNode = treeQueue.poll();
            if (curNode.left != null) {
                treeQueue.add(curNode.left);
                result.add(String.valueOf(curNode.left.value));
            } else {
                result.add(null);
            }
            if (curNode.right != null) {
                treeQueue.add(curNode.right);
                result.add(String.valueOf(curNode.right.value));
            } else {
                result.add(null);
            }
        }
        return result;
    }

    /**
     * 反序列化，先充是放到队列里的，所以最先出来的是完全符合先序遍历逻辑的。
     * @param queue
     * @return
     */
    public static Node buildByPreQueue(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        return preb(queue);
    }

    public static Node preb(Queue<String> queue) {
        String value = queue.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = preb(queue);
        head.right = preb(queue);
        return head;
    }

    /**
     * 对于后序的反序列化，我们往队列入时顺序是左右中，所以说队头是最左，队尾是根节点。
     * 那么，我们按照这个顺序把它放到Stack里，所以它弹出栈的顺序就是根右左
     * @param queue
     * @return
     */
    public static Node buildByPostQueue(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        return postb(stack);

    }

    private static Node postb(Stack<String> stack) {
        String value = stack.pop();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.right = postb(stack);
        head.left = postb(stack);
        return head;
    }

    /**
     * 层次遍历反序列化，就是构建一个新的队列，把根节点、左、右子树入队，
     * 然后再出队，在这个过程中对它的左右子树赋值。
     * @param queueString
     * @return
     */
    public static Node buildByLevel(Queue<String> queueString) {
        if (queueString == null || queueString.size() == 0) {
            return null;
        }
        Node head = generateNode(queueString.poll());;
        // 模拟一个层次遍历的levelQueue
        Queue<Node> levelQueue = new LinkedList<>();
        if (head != null) {
            levelQueue.add(head);
        }
        Node node = null;
        while (!levelQueue.isEmpty()) {
            // 第一次就是根节点出队。
            node = levelQueue.poll();
            // 为它的左右子树赋值
            node.left = generateNode(queueString.poll());
            node.right = generateNode(queueString.poll());
            // 把下一层的子树入队
            if (node.left != null) {
                levelQueue.add(node.left);
            }
            if (node.right != null) {
                levelQueue.add(node.right);
            }
        }
        return head;
    }

    private static Node generateNode(String val) {
        if (val == null) {
            return null;
        }
        return new Node(Integer.valueOf(val));
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
