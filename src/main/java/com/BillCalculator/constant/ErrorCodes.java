package com.BillCalculator.constant;

public class ErrorCodes {

    public static final String SUCCESS = "00";
    public static final String FAILED = "99";

    public static final String SUCCESS_EXPLANATION_SHAREHOLDER = "Shareholder createad on DB";
    public static final String FAILED_EXPLANATION_SHAREHOLDER = "Getting error while creating shareholder on DB";
    public static final String SUCCESS_EXPLANATION_USER = "Register is succesfull. Check your email to confirmation";
    public static final String SUCCESS_EXPLANATION_CONFIRM_MAIL = "Mail confirmation is successful. Now you can login!";
    public static final String USER_ALREADY_EXIST = "User already exist";
    public static final String USER_EXIST_BUT_MAIL_NOT_CONFIRMED = "User registered but user's mail is not confirmed. Check your mail to confirmation";
    public static final String PASSWORD_DOES_NOT_EQUAL_CONFIRM_PASSWORD = "Password does not match confirm password.";
    public static final String TOKEN_IS_NOT_FOUND = "Token is not found";
    public static final String MAIL_ALREADY_CONFIRMED = "Mail already confirmed.";
    public static final String PASSWORD_IS_ICORRECT = "Password is incorrect";
    public static final String USERNAME_OR_PASSWORD_INCORRECT = "Username or password is incorrect";
    public static final String LOGIN_SUCCESSFULL = "Login successful";
    public static final String EMAIL_GOT_ERROR_WHILE_SENDING = "Email is not sent";
}
