package com.t3h.group3_petshop.utils;

import java.nio.file.Paths;

public class Constant {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String IMAGE_SAVE_PATH = "C:\\T3H_Group3\\Source\\Images\\product\\";
    public static final String INVOICE_PDF_PATH = Paths.get("").toAbsolutePath().getParent().toString() + "/Pdf/invoice/";
    public static String VNP_TMN_CODE = "YTVDOR0L";
    public static String VNP_HASH_SECRET = "TDPNZSAPJSPCWWAIKNFREUDXUBTRUJFF";
    public static String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String VNP_PAY_VERSION = "2.1.0";
    public static Integer ORDER_STATUS_UNPAID = 1;
    public static Float WIDTH_PDF = 570f;

}
