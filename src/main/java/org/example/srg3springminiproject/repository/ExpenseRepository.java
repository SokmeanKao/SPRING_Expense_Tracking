package org.example.srg3springminiproject.repository;

import org.apache.ibatis.annotations.*;
import org.example.srg3springminiproject.model.Expense;
import org.example.srg3springminiproject.model.request.ExpenseRequest;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ExpenseRepository {

    @Select("""
        select * from expenses_tb WHERE user_id = CAST(#{UserId} AS UUID) order by ${sortBy} ${orderByStr} LIMIT #{limit} OFFSET #{offset};
        
    """)
    @Results(id="expenseMapping", value = {
            @Result(property = "user",column = "user_id",one = @One(select = "org.example.srg3springminiproject.repository.UserRepository.getUserById")),
            @Result(property = "categories",column = "category_id",one = @One(select = "org.example.srg3springminiproject.repository.CategoryRepository.findCategoryByCate")),
            @Result(property = "expenseId", column = "expense_id"),

    })
    List<Expense> getAllExpense(int offset, int limit, String sortBy,String orderByStr,UUID UserId);

    @Select("""
            SELECT * FROM expenses_tb WHERE expense_id= CAST(#{id} AS UUID) AND user_id = CAST(#{userId} AS UUID)
    """)
    @ResultMap("expenseMapping")
    Expense findExpenseById(UUID id, UUID userId);

    @Select(""" 
            INSERT INTO  expenses_tb (amount,description,date,category_id,user_id) VALUES (#{expense.amount},#{expense.description},#{expense.date},CAST(#{expense.categoryId} AS UUID), CAST(#{UserId} AS UUID) )RETURNING *;
    """)
    @ResultMap("expenseMapping")
    Expense saveExpense(@Param("expense") ExpenseRequest expenseRequest,UUID UserId);

    @Select("""
            UPDATE  expenses_tb SET amount=#{expense.amount}, description=#{expense.description},date=#{expense.date},category_id = CAST(#{expense.categoryId} AS UUID), user_id = CAST(#{UserId} AS UUID) WHERE expense_id = CAST(#{id} AS UUID) RETURNING *;
    """)
    @ResultMap("expenseMapping")
    Expense updateExpense(UUID id , @Param("expense") ExpenseRequest expenseRequest, UUID UserId);

    @Delete("""
            DELETE FROM  expenses_tb WHERE expense_id = CAST(#{id} AS UUID)
    """)
    Boolean deleteExpense(UUID id);
}
