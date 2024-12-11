package org.example.srg3springminiproject.repository;
import org.apache.ibatis.annotations.*;
import org.example.srg3springminiproject.model.Category;
import org.example.srg3springminiproject.model.request.CategoryRequest;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface CategoryRepository {
    @Select("""
       SELECT * FROM categories_tb WHERE user_id = CAST(#{UserId} AS UUID) LIMIT #{limit} OFFSET #{offset};
    """)
    @Results(id = "categoryMapper", value = {
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "user", column = "user_id", one = @One(select = "org.example.srg3springminiproject.repository.UserRepository.getUserById"))
    })
    List<Category> findAllCategory(Integer offset, Integer limit,UUID UserId);

    @Select("""
        SELECT * FROM categories_tb WHERE category_id = CAST(#{id} AS UUID) AND user_id = CAST(#{userId} AS UUID) ;
    """)
    @ResultMap("categoryMapper")
    Category findCategoryById(UUID id, UUID userId);

    @Select("SELECT category_id, name,description FROM categories_tb WHERE category_id = CAST(#{id} AS UUID)")
    @ResultMap("categoryMapper")
    Category findCategoryByCate(UUID id);

    @Select("""
        INSERT INTO categories_tb (name,description,user_id) values (#{category.name}, #{category.description}, CAST(#{userId} AS UUID)) RETURNING *;
    """)
    @ResultMap("categoryMapper")
    Category insertCategory(@Param("category") CategoryRequest categoryRequest, UUID userId);

    @Select("""
        UPDATE categories_tb set name = (#{category.name}),description = (#{category.description}) WHERE category_id = CAST(#{id} AS UUID) RETURNING *
    """)
    @ResultMap("categoryMapper")
    Category updateCategory(UUID id,@Param("category") CategoryRequest categoryRequest);

    @Delete("""
        DELETE  FROM categories_tb WHERE category_id = CAST(#{id} AS UUID);
    """)
    @ResultMap("categoryMapper")
    Boolean removeCategory(UUID id);

}