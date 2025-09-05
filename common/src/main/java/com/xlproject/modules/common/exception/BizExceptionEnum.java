package com.xlproject.modules.common.exception;

import lombok.Getter;

public enum BizExceptionEnum {
    USER_NOT_EXIT(1001,"用户不存在"),
    USER_PASSWORD_ERROR(1002,"用户密码错误"),
    USER_Name_ERROR(1003,"用户名错误或不存在"),
    USER_ALREADY_EXIT(1004,"用户已存在"),
    USER_PASSWORD_ISNULL(1005,"用户密码为空"),
    USER_USERNAME_EXIT(1006,"用户名已被占用"),
    USER_ROLE_NOT_CAN_UPD(1007,"用户角色不可在此更改"),
    USER_STATUS_NOT_CAN_UPD(1007,"用户状态不可在此更改"),
    USER_PHONE_ERROR(1008,"手机号错误"),
    USER_ID_ERROR(1009,"用户ID错误，请重试"),
    USER_NOT_PERMISSION(1010,"用户无权限"),

    CATEGORY_NOT_EXIT(2001,"商品分类不存在"),
    CATEGORY_ADD_ERROR(2001,"商品分类添加失败，请重试"),
    CATEGORY_ALREADY_EXIT(2003,"商品分类已存在"),
    CATEGORY_NOT_EDIT(2004,"商品分类创建时间不可更改"),
    CATEGORY_UPDATE_ERROR(2005,"商品分类更新失败"),

    PRODUCT_ID_ERROR(3001,"商品ID有误，请重新输入"),
    PRODUCT_ID_NOT_EXIT(3002,"根据此ID搜索的商品不存在，请重新确认"),
    PRODUCT_NAME_ERROR(3003,"商品名有误，请重新输入"),
    PRODUCT_NAME_NOT_EXIT(3004,"根据此商品名搜索的商品不存在，请重新确认"),
    PRODUCT_CODE_ERROR(3005,"商品编号有误，请重新输入"),
    PRODUCT_CODE_NOT_EXIT(3006,"根据此商品编号搜索的商品不存在，请重新确认"),
    PRODUCT_SUPPLIER_NOT_SEARCH(3007,"供应商名未搜到商品信息，请重新确认"),
    PRODUCT_CATEGORY_NOT_SEARCH(3008,"商品分类未搜到商品信息，请重新确认"),
    PRODUCT_NOT_EXIT(3009,"商品不存在，请重新确认"),
    PRODUCT_NAME_ALREADY_EXIT(3010,"商品名已存在，请重新确认"),
    PRODUCT_PRICE_ERROR(3011,"商品单价有误，请重新确认"),
    PRODUCT_STATUS_ERROR(3011,"商品状态有误，请重新确认"),
    PRODUCT_EXIT_INVENTORY(3013,"商品存在库存"),
    PRODUCT_DEL_ERROR(3014,"删除商品错误"),
    PRODUCT_MIN_STOCK_ERROR(3015,"商品最低预警值不可为空且不能小于10"),

    SUPPLIER_NOT_SEARCH_ID(4001,"通过id未查找到该供应商，请重新确认"),
    SUPPLIER_NOT_SEARCH_NAME(4002,"通过名字未查找到该供应商，请重新确认"),
    SUPPLIER_NAME_ERROR(4003,"供应商名有误，请重新确认"),
    SUPPLIER_ADD_ERROR(4004,"新增供应商信息出错，请重新确认"),
    SUPPLIER_UPD_ERROR(4005,"修改供应商信息有误，请重新尝试"),
    SUPPLIER_UPD_ID_ERROR(4006,"请使用正确的供应商ID进行更改"),
    SUPPLIER_UPD_STATUS_ERROR(4007,"请使用正确的供应商状态进行更改"),
    SUPPLIER_HAS_PRODUCTS(4008,"此供应商还有在售的商品，不可删除"),

    WAREHOUSE_ID_NOT_SEARCH(5001,"未搜索到指定id的仓库信息，请重试"),
    WAREHOUSE_CODE_NOT_SEARCH(5002,"未搜索到指定code编号的仓库信息，请重试"),
    WAREHOUSE_CODE_EXIT(5003,"仓库号已存在，请重试"),
    WAREHOUSE_EXIT(5004,"仓库已存在，请重试"),
    WAREHOUSE_ID_EXIT(5005,"仓库ID已存在，请重试"),
    WAREHOUSE_MANAGER_ID_NOT_SEARCH(5006,"仓库负责人不存在，请重新确认"),
    WAREHOUSE_ID_ERROR(5007,"仓库ID号错误，请重新确认"),
    WAREHOUSE_STATUS_ERROR(5008,"仓库状态必须为0或1，请重新确认"),
    WAREHOUSE_PRODUCT_EXIT(5009,"仓库还存有商品不可删除，请重新确认"),

    INBOUND_SUPPLIERID_NEED_PURCHASE(6001,"入库表供应商ID需要入库类型为采购入库PURCHASE"),
    INBOUND_BATCHNO_ERROR(6002,"批次号格式有误(SPXXX-20250820-001)"),
    INBOUND_PRODUCT_BATCHNO_EXIT(6003,"批次及商品已存在"),

    INVENTORY_ID_ERROR(7001,"库存ID有误，请重新输入"),
    INVENTORY_ID_NOT_SEARCH(7002,"库存ID未搜索到库存，请重新输入"),
    INVENTORY_PRODUCTID_NOT_SEARCH(7003,"商品ID未搜索到库存，请重新输入"),
    INVENTORY_WAREHOUSEID_NOT_SEARCH(7004,"仓库未有任何商品的库存，请重新输入"),
    INVENTORY_PRODUCTCODE_NOT_SEARCH(7005,"未搜索到该商品编号的库存，请重新输入"),
    INVENTORY_SUPPLIERNAME_NOT_SEARCH(7006,"未搜索到该供应商的库存，请重新输入"),

    OUTBOUND_INVENTORY(8001,"剩余库存不足，无法出库"),
    OUTBOUND_PRODUCT_NOTEXIT(8002,"要出库商品不存在或库存不足，无法出库"),
    OUTBOUND_QUANTITY_ERROR(8003,"要出库商品数量错误，至少大于0"),
    OUTBOUND_BATCH_NOT_FOUND(8004,"未找到指定仓库出库商品批次号的商品，请重新确认"),



    FILE_UPLOAD_ERROR(9001,"文件路径加载错误" ),
    FILE_TYPE_ERROR(9002, "文件类型错误，只允许上传图片文件");


    @Getter
     final Integer code;
    @Getter
    private final   String msg;
    BizExceptionEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
