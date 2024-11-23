package com.itheima.reggie.config;

import java.util.LinkedList;

/**
 * @program: reggie_take_out
 * @description: *
 * @return:
 **/
public class LinkedListSort {
    public static class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
    public static ListNode SortLinkedList(ListNode l1, ListNode l2){
        ListNode res = new ListNode(-1);
        ListNode dummy = res;
        while(l1 != null && l2 != null){
            if(l1.val >= l2.val){
                res.next = l2;
                l2 = l2.next;
            }else{
                res.next = l1;
                l1 = l1.next;
            }
            res = res.next;
        }
        if(l1 != null){
            res.next = l1;
        }
        if(l2 != null){
            res.next = l2;
        }
        return dummy.next;
    }
}
