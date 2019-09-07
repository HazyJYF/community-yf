package life.yuanfeng.community.mapper;

import life.yuanfeng.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * QuestionService class
 *
 * @author jiyf
 * @create 2019-09-07 17:05
 **/

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void createQuestion(Question question);

    @Select("select * from question")
    List<Question> list();

}

