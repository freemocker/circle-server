package com.ubatis.circleserver.util.common;

public class DICT {

    public static final String IS_DELETE_NO = "N";
    public static final String IS_DELETE_YES = "Y";

    /** 订单状态 mkt_order.order_status. */
    // (过时)状态：1、待支付 2、待录件 3、待审核 4、待修改 5、待收货 6、已完成
    // 状态：0--未知值 1--待支付 2--待录件 3--待审核 4--待发货 5--待收货 6--已完成
    public static final int ORDER_STATUS_UNKNOWN = 0;
    public static final int ORDER_STATUS_NOT_PAID = 1;
    public static final int ORDER_STATUS_FOR_RECORD = 2;
    public static final int ORDER_STATUS_FOR_VALID = 3;
    public static final int ORDER_STATUS_FOR_DELIVERY = 4;
    public static final int ORDER_STATUS_FOR_RECEIVE = 5;
    public static final int ORDER_STATUS_COMPLETE = 6;
    /** 状态状态-子状态*/
    // 结果：0--默认值 1--需修改
    public static final int ORDER_IS_FLAG_DEFAULT = 0;
    public static final int ORDER_IS_FLAG_PAUSE = 1;

    /** 用户状态 crm_user.user_status */
    // 用户状态：0--未实名，1--待审核 2--已实名 3--挂失
    public static final int USER_STATUS_UN_CERTIFICATED = 0;
    public static final int USER_STATUS_FOR_CERTIFICATED = 1;
    public static final int USER_STATUS_CERTIFICATED = 2;
    public static final int USER_STATUS_LOSS = 3;

    /** pay_cash.pay_status */
    // 申请状态 0--申请中（待审核） 1--确认申请（通过未打款） 2--已打款 3--驳回
    public static final int PAY_STATUS_APPLYING = 0;
    public static final int PAY_STATUS_CONFIRM = 1;
    public static final int PAY_STATUS_COMPLETE = 2;
    public static final int PAY_STATUS_REJECT = 3;

    /** pay_detail.cost_type*/
    // 变动类型:0--支出,1--收入
    public static final int PAY_DETAIL_COST_TYPE_OUT = 0;
    public static final int PAY_DETAIL_COST_TYPE_IN = 1;
    /** pay_detail.purpose*/
    // 用途说明，款项说明（比如：1--个人分销，2--个人分润，3--团队奖，4--提现）
    public static final int PAY_DETAIL_PURPOSE_PERSON_FENXIAO = 1;
    public static final int PAY_DETAIL_PURPOSE_PERSON_FENRUN = 2;
    public static final int PAY_DETAIL_PURPOSE_TEAM_PROFIT = 3;
    public static final int PAY_DETAIL_PURPOSE_FOR_CASH = 4;
    /** pay_detail.detail*/
    // 明细说明（比如：0--不作说明 1--直接收益，2--间接收益）
    public static final int PAY_DETAIL_DETAIL_NO_COMMENT = 0;
    public static final int PAY_DETAIL_DETAIL_STRAING_INCOME = 1;
    public static final int PAY_DETAIL_DETAIL_NOT_STRAING_INCOME = 2;

    /** 录件记录状态 */
    public static final int MACH_RECORD_STATUS_INACTIVATED = 0;
    public static final int MACH_RECORD_STATUS_ACTIVATED = 1;

    /** 支付宝支付状态 */
    public static final String ALIPAY_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";//交易成功

    /** 专题状态:  0--未启用 1--已启用 2--已下架 */
    public static final int SUBJECT_STATUS_PREPARING = 0;
    public static final int SUBJECT_STATUS_ACTIVED = 1;
    public static final int SUBJECT_STATUS_INACTIVE = 2;

    /** 余额变动类型:0--支出,1--收入 */
    public static final int COST_TYPE_OUT = 0;
    public static final int COST_TYPE_IN = 1;

    /** 分润类型0--个人分润 1--团队分润 */
    public static final int PROFIT_TYPE_PERSON = 0;
    public static final int PROFIT_TYPE_TEAM = 1;

    /** 商品类型 0--普通商品，1--录件商品，2--仅展示商品 */
    public static final int GOODS_TYPE_NORMAL = 0;
    public static final int GOODS_TYPE_RECORD = 1;
    public static final int GOODS_TYPE_SHOW = 2;

    /** 商品状态 状态：0--未上架 1--已上架 */
    public static final int GOODS_STATUS_DOWN = 0;
    public static final int GOODS_STATUS_UP = 1;

}
