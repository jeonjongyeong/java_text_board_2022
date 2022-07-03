package com.jjy.exam.board;

import java.util.ArrayList;
import java.util.List;

public class UsrMemberController {
    private int membersLastId;
    private List<Member> members;

    UsrMemberController() {
        membersLastId = 0;
        members = new ArrayList<>();

        makeTestData();

        if (members.size() > 0) {
            membersLastId = members.get(members.size() - 1).id;
        }
    }

    void makeTestData() {
        for (int i = 0; i < 3; i++) {
            int id = i + 1;
            members.add(new Member(id, "user" + id, "user" + id));
        }
    }

    public void actionJoin(Rq rq) {
        System.out.println("- 회원 가입 -");
        System.out.printf("로그인아이디 : ");
        String loginId = Container.sc.nextLine();
        System.out.printf("로그인비밀번호 : ");
        String loginPw = Container.sc.nextLine();
        System.out.printf("로그인비밀번호 확인 : ");
        String loginPwConfirm = Container.sc.nextLine();

        if (loginPw.equals(loginPwConfirm) == false) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return;
        }

        int id = membersLastId + 1;
        membersLastId = id;

        Member member = new Member(id, loginId, loginPw);
        members.add(member);
        System.out.printf("%s님. 가입을 환영합니다.\n", member.loginId);
        System.out.printf("%d번 회원이 생성되었습니다.\n", member.id);
    }
}