package pers.prover07.lottery.domain.award.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.domain.award.model.vo.ShippingAddress;

/**
 * 奖品发货信息
 *
 * @author Prover07
 * @date 2023/9/1 14:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReq {

    private String uId;

    private Long orderId;

    private String awardId;

    private String awardName;

    private String awardContent;

    private ShippingAddress shippingAddress;

    private String extInfo;

    public GoodsReq(String uId, Long orderId, String awardId, String awardName, String awardContent) {
        this.uId = uId;
        this.orderId = orderId;
        this.awardId = awardId;
        this.awardName = awardName;
        this.awardContent = awardContent;
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }
        // 保证小的在左边，大的在右边
        // 1 4 3 2 5 0
        // temp.next = min.next = val.next =

        ListNode minHead = new ListNode(-1);
        ListNode maxHead = null;
        ListNode res = minHead;
        while (head != null) {
            System.out.println(head.val);
            if (head.val < x) {
                minHead.next = head;
                minHead = minHead.next;
            } else {
                if (maxHead == null) {
                    maxHead = head;
                } else {
                    maxHead.next = head;
                    maxHead = maxHead.next;
                }
            }

            head = head.next;
        }

        if (res.next == null) {
            return maxHead;
        } else {
            minHead.next = maxHead;
            return res.next;
        }
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
