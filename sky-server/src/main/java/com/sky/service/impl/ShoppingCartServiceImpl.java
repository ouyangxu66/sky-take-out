package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加商品到购物车
     * @param shoppingCartDTO
     * @return
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        //判断添加的商品在购物车中是否已经存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        Long userId=BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //如果存在则商品数量加一即可
        if (list!=null && list.size()>0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber()+1);
            shoppingCartMapper.updateNumberById(cart);
        }else {
            //如果不存在将商品插入到购物车数据库表
            //判断此次添加的商品是套餐还是菜品
            Long dishId = shoppingCartDTO.getDishId();

            if (dishId!=null){
                //本次添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                //获取菜品信息,将其添加到ShoppingCartDTO

                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else {
                //否则添加的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();

                //获取套餐信息,将其添加到ShoppingCartDTO
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }

        }
        //不管是套餐还是菜品,都插入到数据库
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);

    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {

        Long userId=BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder().id(userId).build();

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        return list;
    }
}
