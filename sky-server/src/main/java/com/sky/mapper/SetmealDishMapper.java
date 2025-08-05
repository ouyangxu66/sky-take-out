package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id获取套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 插入多条菜品数据
     * @param setmealDish
     */
    void insertBatch(List<SetmealDish> setmealDish);


    /**
     * 根据套餐id获取菜品信息
     *
     * @param id
     * @return
     */
    @Select("select *from setmeal_dish where dish_id=#{id}")
    List<SetmealDish> getBySetmealId(Long id);

    /**
     * 根据套餐id删除菜品
     * @param id
     */
    @Delete("delete from setmeal_dish where setmeal_id=#{id} ")
    void deleteBySetmealId(Long id);
}
