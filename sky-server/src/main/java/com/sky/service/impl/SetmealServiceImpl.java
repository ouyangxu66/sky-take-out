package com.sky.service.impl;

import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.BagUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @Override
    public void save(SetmealDTO setmealDTO) {
        //1.DTO里面包含了setmeal和setmealDish属性,所以需要分开插入数据库表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //2.添加套餐基本信息 --插入setmeal属性
        setmealMapper.insert(setmeal);

        //3.获取套餐id--插入setmealDish属性
        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes!=null && setmealDishes.size()>0){
            setmealDishes.forEach(
                    setmealDish -> setmealDish.setSetmealId(setmealId)
            );
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }


}
