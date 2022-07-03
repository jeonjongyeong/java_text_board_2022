package com.jjy.exam.board;

import java.util.*;

/*
TODO
- [x] 환영메세지 출력
- [x] 고객으로부터 명령어 입력받기
    - [x] 스캐너 객체라도 하나 만들어보기
    - [x] 받은 명령어 출력하기
- [x] exit 명령어 처리하기
    - [x] exit 입력받으면 종료라고 출력하기
    - [x] exit 입력받을 때 까지 계속 실행
- [x] /usr/article/write 명령어 입력처리
    - [x] 시작문구라도 출력
    - [x] 제목과 내용이라도 입력받기
    - [x] 생성된 게시물 번호 출력
    - [x] 생성될 때 마다 게시물번호가 증가
    - [x] 생성된 게시물을 객체에 담기
    - [x] Article 클래스 생성하기
    - [x] Article 객체 생성하기
- [ ] /usr/article/detail 명령어 입력처리
- [x] 명령어를 제대로 입력했다면 입력받은 명령어 문구는 안나오도록
- [x] toString 메서드 오버라이드 하기
- [x] 생성자 도입
*/
public class Main {
    static void makeTestData(List<Article> articles) {
        for (int i = 0; i < 100; i++) {
            int id = i + 1;
            articles.add(new Article(id, "제목" + id, "내용" + id));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("== 게시판 v 0.1 ==");
        System.out.println("== 프로그램 시작 ==");
        int articlesLastId = 0;
        List<Article> articles = new ArrayList<>();
        makeTestData(articles);
        if (articles.size() > 0) {
            articlesLastId = articles.get(articles.size() - 1).id;
        }
        while (true) {
            System.out.printf("명령) ");
            String cmd = sc.nextLine();
            Rq rq = new Rq(cmd);
            Map<String, String> params = rq.getParams();
            if (rq.getUrlPath().equals("exit")) {
                break;
            } else if (rq.getUrlPath().equals("/usr/article/list")) {
                actionUsrArticleList(rq, articles);
            } else if (rq.getUrlPath().equals("/usr/article/detail")) {
                actionUsrArticleDetail(rq, articles);
            } else if (rq.getUrlPath().equals("/usr/article/write")) {
                actionUsrArticleWrite(rq, sc, articles, articlesLastId);
                articlesLastId++;
            } else {
                System.out.printf("입력된 명령어 : %s\n", cmd);
            }
        }
        System.out.println("== 프로그램 종료 ==");
        sc.close();
    }

    private static void actionUsrArticleWrite(Rq rq, Scanner sc, List<Article> articles, int articlesLastId) {
            System.out.println("- 게시물 등록 -");
            System.out.printf("제목 : ");
            String title = sc.nextLine();
            System.out.printf("내용 : ");
            String body = sc.nextLine();
            int id = articlesLastId + 1;

            Article article = new Article(id, title, body);
            articles.add(article);
            System.out.println("생성된 게시물 객체 : " + article);

            System.out.printf("%d번 게시물이 입력되었습니다.\n", article.id);
        }
    }

    private static void actionUsrArticleDetail(Rq rq, List<Article> articles) {
        Map<String, String> params = rq.getParams();

        if (params.containsKey("id") == false) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(params.get("id"));
        } catch (NumberFormatException e) {
            System.out.println("id를 정수형태로 입력해주세요.");
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

    private static void actionUsrArticleList(Rq rq, List<Article> articles) {
        System.out.println("- 게시물 리스트 -");
        System.out.println("--------------------");
        System.out.println("번호 / 제목");
        System.out.println("--------------------");

        Map<String, String> params = rq.getParams();

        // 검색시작
        List<Article> filteredArticles = articles;

        if (params.containsKey("searchKeyword")) {
            String searchKeyword = params.get("searchKeyword");

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

        boolean orderByIdDesc = true;

        if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
            orderByIdDesc = false;
        }
        if (orderByIdDesc) {
            sortedArticles = Util.reverseList(sortedArticles);
        }
        for (Article article : sortedArticles) {
            System.out.printf("%d / %s\n", article.id, article.title);
        }
    }
}
class Article {
    int id;
    String title;
    String body;
    Article(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
    @Override
    public String toString() {
        return String.format("{id: %d, title: \"%s\", body: \"%s\"}", id, title, body);
    }
}
class Rq {
    private String url;
    private String urlPath;
    private Map<String, String> params;
    // 필드추가가능
    // 수정가능
    Rq(String url) {
        this.url = url;
        urlPath = Util.getUrlPathFromUrl(this.url);
        params = Util.getParamsFromUrl(this.url);
    }
    // 수정가능, if문 금지
    public Map<String, String> getParams() {
        return params;
    }
    // 수정가능, if문 금지
    public String getUrlPath() {
        return urlPath;
    }
}
class Util {
    // 이 함수는 원본리스트를 훼손하지 않고, 새 리스트를 만듭니다. 즉 정렬이 반대인 복사본리스트를 만들어서 반환합니다.
    public static <T> List<T> reverseList(List<T> list) {
        List<T> reverse = new ArrayList<>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            reverse.add(list.get(i));
        }
        return reverse;
    }
    static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> params = new HashMap<>();
        String[] urlBits = url.split("\\?", 2);
        if (urlBits.length == 1) {
            return params;
        }
        String queryStr = urlBits[1];
        for (String bit : queryStr.split("&")) {
            String[] bits = bit.split("=", 2);
            if (bits.length == 1) {
                continue;
            }
            params.put(bits[0], bits[1]);
        }
        return params;
    }
    static String getUrlPathFromUrl(String url) {
        return url.split("\\?", 2)[0];
    }
}