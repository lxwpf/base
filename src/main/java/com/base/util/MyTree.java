package com.base.util;

/**
 * @author lixiong
 * @date 2020 07 2020/7/30
 * @desc 手写一颗二叉树
 */
public class MyTree {

    private class Node {
        private int val;
        private Node left,right;

        public Node(int x) {
            this.val = x;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    /**
     * 往树里面添加元素
     */
    public void add(int x) {
        if (root == null) {
            root = new Node(x);
        }
        add(root, x);
    }

    /**
     * 前序遍历，中后序遍历改变输出位置即可
     */
    private void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.println(node.val);
        preOrder(node.left);
        preOrder(node.right);
    }

    // 递归添加
    private void add(Node node,int x) {
        // 结束条件
        if (x < node.val && null == node.left) {
            node.left = new Node(x);
            size++;
            return;
        }else if (x > node.val && null == node.right) {
            node.right = new Node(x);
            size++;
            return;
        }

        if (x < node.val) {
            add(node.left, x);
        }else
            add(node.right, x);
    }

}
