package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param orders
     * @return
     */
    void insert(Orders orders);
    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 查询历史订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> page(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 取消订单
     * @param id
     * @return
     */
    @Update("update orders set status=6 where id=#{id}")
    void cancel(Long id);

    /**
     * 根据订单id查询订单
     * @param id
     * @return
     */
    @Select("select *from orders where id=#{id}")
    Orders getByOrderId(Long id);

    /**
     * 各个状态的订单数量统计
     * @return
     */
    @Select("select count(id)from orders where status=#{status}")
    Integer countStatus(Integer status);

    /**
     * 根据订单状态和订单时间获取订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status=#{status} and order_time <(#{orderTime})")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);


    /**
     * 用于替换微信支付更新数据库状态的问题
     * @param orderStatus
     * @param orderPaidStatus
     */
    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} " +
            "where number = #{orderNumber}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, String orderNumber);

    /**
     * 统计每天营业额
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 根据时间条件统计订单数据
     * @return
     */
    Integer countByMap(Map map);
}
