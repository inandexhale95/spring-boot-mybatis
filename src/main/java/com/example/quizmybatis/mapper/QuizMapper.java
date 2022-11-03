package com.example.quizmybatis.mapper;

import com.example.quizmybatis.entity.Quiz;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface QuizMapper {
    @Insert("INSERT INTO quiz(question, answer, author) VALUES(#{quiz.question}, #{quiz.answer}, #{quiz.author})")
    boolean insert(@Param("quiz")Quiz quiz);

    @Select("SELECT * FROM quiz")
    @Results(id = "QuizMap", value = {
            @Result(property = "question", column = "question"),
            @Result(property = "answer", column = "answer"),
            @Result(property = "author", column = "author")
    })
    List<Quiz> getAll();

    @Select("SELECT * FROM quiz WHERE id=#{id}")
    @ResultMap("QuizMap")
    Optional<Quiz> getById(@Param("id")int id);

    @Update("UPDATE quiz SET question=#{quiz.question}, answer=#{quiz.answer}, author=#{quiz.author} WHERE id=#{quiz.id}")
    boolean update(@Param("quiz")Quiz quiz);

    @Delete("DELETE FROM quiz WHERE id=#{id}")
    boolean delete(@Param("id")int id);

    @Select("SELECT * FROM quiz ORDER BY RAND() LIMIT 1")
    Optional<Quiz> getRandom();
}
