package com.zzwl.ias.mapper;

import com.zzwl.ias.dto.notification.NotificationAddDTO;
import com.zzwl.ias.dto.notification.NotificationQueryDTO;
import com.zzwl.ias.dto.notification.NotificationUpdateDTO;
import com.zzwl.ias.vo.NotificationDetailVo;
import com.zzwl.ias.vo.NotificationListVo;
import com.zzwl.ias.vo.NotificationUserListVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Lvpin on 2018/12/5.
 */
@Component
@Mapper
public interface NotificationExtMapper extends NotificationMapper {
    /**
     * 添加通知
     *
     * @param notificationAddDTO
     */
    @Insert({
            "insert into notification (article_id, ",
            "title, summary,end_time,type)",
            "values (#{articleId}, ",
            "#{title},#{summary},#{endTime},#{type})"
    })
    void insertNotification(NotificationAddDTO notificationAddDTO);

    /**
     * 添加通知用户
     *
     * @param userIds
     * @param notificationId
     */
    @Insert({
            "<script>insert into notification_user_list (user_id, notification_id)",
            "values <foreach collection=\"userIds\" item=\"userId\" separator=\",\">",
            "(#{userId},#{notificationId})",
            "</foreach></script>"
    })
    void insertNotificationUserList(@Param("userIds") List<Integer> userIds, @Param("notificationId") Integer notificationId);

    @Select({
            "SELECT @@IDENTITY id"
    })
    int selectLastId();

    /**
     * 通知列表
     *
     * @param notificationQueryDTO
     * @return
     */
    @Select({
            "<script>SELECT n.id, n.article_id, n.title, n.summary, DATE_FORMAT( n.start_time, '%Y-%m-%d %H:%i:%S') start_time, ",
            "DATE_FORMAT( n.end_time, '%Y-%m-%d %H:%i:%S' ) end_time, DATE_FORMAT( n.create_time, '%Y-%m-%d %H:%i:%S' ) create_time,n.type ",
            "FROM notification n",
            "<where>",
            "<if test=\"like != ''\">n.title like concat('%',#{like},'%')",
            "</if></where> order by n.create_time desc",
            "</script> "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "summary", property = "summary", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
    })
    List<NotificationListVo> selectNotificationList(NotificationQueryDTO notificationQueryDTO);

    @Select({
            "SELECT" +
                    "  u.id," +
                    "  u.username," +
                    "  u.mobile," +
                    "  u.id_card," +
                    "  CONCAT(" +
                    "    IFNULL(cr.name, '')," +
                    "    IFNULL(crr.name, '')," +
                    "    IFNULL(crrr.name, '')," +
                    "    IFNULL(crrrr.name, '')," +
                    "    IFNULL(crrrrr.name, '')," +
                    "    IFNULL(u.address, '')" +
                    "  ) address " +
                    "FROM notification_user_list nul" +
                    " LEFT JOIN users u ON " +
                    " u.id  = nul.user_id" +
                    "  LEFT JOIN common_region cr" +
                    "    ON cr.id = u.province_id" +
                    "  LEFT JOIN common_region crr" +
                    "    ON crr.id = u.city_id" +
                    "  LEFT JOIN common_region crrr" +
                    "    ON crrr.id = u.district_id" +
                    "  LEFT JOIN common_region crrrr" +
                    "    ON crrrr.id = u.town_id" +
                    "  LEFT JOIN common_region crrrrr" +
                    "    ON crrrrr.id = u.village_id" +
                    "    WHERE nul.notification_id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mobile", property = "mobile", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id_card", property = "idCard", jdbcType = JdbcType.VARCHAR),
            @Result(column = "address", property = "address", jdbcType = JdbcType.VARCHAR),
    })
    List<NotificationUserListVo> selectUserByNotificationId(@Param("id") Integer id);

    /**
     * 通知详情
     *
     * @param id
     * @return
     */
    @Select({
            "SELECT",
            "n.id,",
            "n.title,",
            "n.summary,",
            "DATE_FORMAT(n.start_time, '%Y-%m-%d %H:%i:%S') start_time,",
            "DATE_FORMAT(n.end_time, '%Y-%m-%d %H:%i:%S') end_time,n.type",
            "FROM",
            "notification n where n.id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "summary", property = "summary", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
    })
    NotificationDetailVo selectNotificationDetail(@Param("id") Integer id);

    /**
     * 修改
     *
     * @param notificationUpdateDTO
     * @return
     */
    @Update({
            "update notification",
            "set title = #{title},",
            "start_time = #{startTime},",
            "end_time = #{endTime},",
            "title = #{title},",
            "summary = #{summary}",
            "where id = #{id}"
    })
    int updateNotification(NotificationUpdateDTO notificationUpdateDTO);
}
