package com.jjy.exam.board;

import java.util.Scanner;

public class Container {
    static Scanner sc;
    static UsrArticleController usrArticleController;

    static {
        sc = new Scanner(System.in);
        usrArticleController = new UsrArticleController();
    }
}
