package com.jjy.exam.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsrArticleController {
    private int articlesLastId;
    private List<Article> articles;

    UsrArticleController() {
        articlesLastId = 0;
        articles = new ArrayList<>();

        makeTestData();

        if (articles.size() > 0) {
            articlesLastId = articles.get(articles.size() - 1).id;
        }
    }

    void makeTestData() {
        for (int i = 0; i < 100; i++) {
            int id = i + 1;
            articles.add(new Article(id, "제목" + id, "내용" + id));
        }
    }

    public void actionWrite(Rq rq) {
        System.out.println("- 게시물 등록 -");
        System.out.printf("제목 : ");
        String title = Container.sc.nextLine();
        System.out.printf("내용 : ");
        String body = Container.sc.nextLine();
        int id = articlesLastId + 1;
        articlesLastId = id;

        Article article = new Article(id, title, body);
        articles.add(article);
        System.out.println("생성된 게시물 객체 : " + article);

        System.out.printf("%d번 게시물이 입력되었습니다.\n", article.id);
    }

    public void actionDetail(Rq rq) {
        int id = rq.getIntParam("id", 0);


        if (id == 0) {
            System.out.println("id를 올바르게 입력해주세요.");
            return;
        }


        if (id > articles.size()) {
            System.out.println("게시물이 존재하지 않습니다.");
            return;
        }

        Article article = articles.get(id - 1);

        System.out.println("- 게시물 상세내용 -");
        System.out.printf("번호 : %d\n", article.id);
        System.out.printf("제목 : %s\n", article.title);
        System.out.printf("내용 : %s\n", article.body);
    }

    public void actionList(Rq rq) {
        System.out.println("- 게시물 리스트 -");
        System.out.println("--------------------");
        System.out.println("번호 / 제목");
        System.out.println("--------------------");

        String searchKeyword = rq.getParam("searchKeyword", "");

        // 검색시작
        List<Article> filteredArticles = articles;

        if (searchKeyword.length() > 0) {
            filteredArticles = new ArrayList<>();

            for (Article article : articles) {
                boolean matched = article.title.contains(searchKeyword) || article.body.contains(searchKeyword);
                if (matched) {
                    filteredArticles.add(article);
                }
            }
        }
        // 검색끝

        List<Article> sortedArticles = filteredArticles;

        String orderBy = rq.getParam("orderBy", "idDesc");
        boolean orderByIdDesc = orderBy.equals("idDesc");

        if (orderByIdDesc) {
            sortedArticles = Util.reverseList(sortedArticles);
        }

        for (Article article : sortedArticles) {
            System.out.printf("%d / %s\n", article.id, article.title);
        }
    }
}