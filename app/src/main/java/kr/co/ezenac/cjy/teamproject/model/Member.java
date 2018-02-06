package kr.co.ezenac.cjy.teamproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2018-02-01.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
    private Integer id;
    private String login_id;
    private String pw;
    private String member_img;
}
