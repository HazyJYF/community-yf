package life.yuanfeng.community.mapper;

import life.yuanfeng.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * QuestionService class
 *
 * @author jiyf
 * @create 2019-09-07 17:05
 **/

@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id , name , token , gmt_create ,gmt_modified,avatar_url) " +
            "values(#{accountId} , #{name} , #{token} , #{gmtCreate} , #{gmtModified} , #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where id = #{creator}")
    User findById(Integer creator);
}
