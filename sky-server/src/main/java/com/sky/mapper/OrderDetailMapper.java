package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {


    /**
     * 订单明细表中插入多条数据
     * @param orderDetailList
     */
    void insertBatch(List<OrderDetail> orderDetailList);

    /**
     * 根据订单id获取菜品详细信息
     * @param id
     * @return
     */
    @Select("select *from order_detail where order_id=#{id}")
    List<OrderDetail> getByOrderId(Long id);
}
