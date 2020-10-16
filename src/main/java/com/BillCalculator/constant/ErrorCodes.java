package com.BillCalculator.constant;

public class ErrorCodes {

    public static final String SUCCESS = "00";
    public static final String FAILED = "99";

    public static final String SUCCESS_EXPLANATION_SHAREHOLDER = "Shareholder createad on DB";
    public static final String FAILED_EXPLANATION_SHAREHOLDER = "Getting error while creating shareholder on DB";
    public static final String SUCCESS_EXPLANATION_USER = "Register is succesfull. Check your email";
    public static final String USER_ALREADY_EXIST = "User already exist";
    public static final String USER_EXIST_BUT_MAIL_NOT_CONFIRMED = "User registered but user's mail is not confirmed. Check your mail to confirmation";
}
