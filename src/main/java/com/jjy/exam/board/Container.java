package com.jjy.exam.board;

import java.util.Scanner;

public class Container {
    static Scanner sc;
    static UsrArticleController usrArticleController;
    static UsrMemberController usrMemberController;

    static {
        sc = new Scanner(System.in);
        usrArticleController = new UsrArticleController();
        usrMemberController = new UsrMemberController();
    }
}
