package com.t3h.group3_petshop.utils;

import java.nio.file.Paths;

public class Constant {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String IMAGE_PATH_LOCAL = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/static/frontend/images/product/";
    public static final String IMAGE_PATH_DEPLOY = "/frontend/images/product/";
    public static final String IMAGE_FILE_TEST = "file_test/test.jpg";
    public static String VNP_TMN_CODE = "YTVDOR0L";
    public static String VNP_HASH_SECRET = "TDPNZSAPJSPCWWAIKNFREUDXUBTRUJFF";
    public static String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static Integer ORDER_STATUS_UNPAID = 1;
}
