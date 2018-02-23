package kr.co.ezenac.cjy.teamproject.singletone;

import java.util.HashMap;

import kr.co.ezenac.cjy.teamproject.model.Member;

/**
 * Created by Administrator on 2018-02-02.
 */

public class LoginInfo {
        private static LoginInfo curr = null;
        private Member member;


        public static LoginInfo getInstance() {
            if (curr == null) {
                curr = new LoginInfo();
            }
            return curr;
        }

        private LoginInfo(){

        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }
}
