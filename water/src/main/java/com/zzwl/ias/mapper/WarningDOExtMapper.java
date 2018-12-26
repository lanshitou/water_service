package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.iasystem.IasObjectAddr;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-15
 * Time: 11:30
 */
@Component
@Mapper
public interface WarningDOExtMapper extends WarningDOMapper{
    @Insert({
            "insert into warning (id, type, ",
            "sub_type, level, ",
            "cleared, produce_time, ",
            "clear_time, clear_reason, ",
            "addr_type, addr_ias, ",
            "addr_irri_fer, addr_farmland, ",
            "addr_area, addr_parent_dev, ",
            "addr_dev, extension)",
            "values (#{id,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, ",
            "#{subType,jdbcType=INTEGER}, #{level,jdbcType=INTEGER}, ",
            "#{cleared,jdbcType=BIT}, #{produceTime,jdbcType=TIMESTAMP}, ",
            "#{clearTime,jdbcType=TIMESTAMP}, #{clearReason,jdbcType=INTEGER}, ",
            "#{addrType,jdbcType=INTEGER}, #{addrIas,jdbcType=INTEGER}, ",
            "#{addrIrriFer,jdbcType=INTEGER}, #{addrFarmland,jdbcType=INTEGER}, ",
            "#{addrArea,jdbcType=INTEGER}, #{addrParentDev,jdbcType=INTEGER}, ",
            "#{addrDev,jdbcType=INTEGER}, #{extension,jdbcType=OTHER})"
    })
    @Options(useGeneratedKeys = true)
    int insert(WarningDO record);

    @Select({
            "<script>",
            "select",
            "id, type, sub_type, level, cleared, produce_time, clear_time, clear_reason, addr_type, ",
            "addr_ias, addr_irri_fer, addr_farmland, addr_area, addr_parent_dev, addr_dev, ",
            "extension",
            "from warning",
            "where cleared = 0",
            "<if test='type != null'>",
            "and type = #{type,jdbcType=INTEGER}",
            "</if>",
            "<if test='subType != null'>",
            "and sub_type = #{subType,jdbcType=INTEGER}",
            "</if>",
            "and addr_type = #{addr.type,jdbcType=INTEGER}",
            "and addr_ias = #{addr.iasId,jdbcType=INTEGER}",

            "<if test='addr.irriFerId != null'>",
            "and addr_irri_fer = #{addr.irriFerId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.irriFerId == null'>",
            "and addr_irri_fer is null",
            "</if>",

            "<if test='addr.farmlandId != null'>",
            "and addr_farmland = #{addr.farmlandId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.farmlandId == null'>",
            "and addr_farmland is null",
            "</if>",

            "<if test='addr.irriAreaId != null'>",
            "and addr_area = #{addr.irriAreaId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.irriAreaId == null'>",
            "and addr_area is null",
            "</if>",

            "<if test='addr.parentDevId != null'>",
            "and addr_parent_dev = #{addr.parentDevId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.parentDevId == null'>",
            "and addr_parent_dev is null",
            "</if>",

            "<if test='addr.devId != null'>",
            "and addr_dev = #{addr.devId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.devId == null'>",
            "and addr_dev is null",
            "</if>",

            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="sub_type", property="subType", jdbcType=JdbcType.INTEGER),
            @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
            @Result(column="cleared", property="cleared", jdbcType=JdbcType.BIT),
            @Result(column="produce_time", property="produceTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_time", property="clearTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_reason", property="clearReason", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_type", property="addrType", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_ias", property="addrIas", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_irri_fer", property="addrIrriFer", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_farmland", property="addrFarmland", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_area", property="addrArea", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_parent_dev", property="addrParentDev", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_dev", property="addrDev", jdbcType=JdbcType.INTEGER),
            @Result(column="extension", property="extension", jdbcType=JdbcType.OTHER)
    })
    List<WarningDO> listCurrWarningByAddrAndType(@Param(value = "addr") IasObjectAddr addr, @Param(value = "type")Integer type, @Param(value = "subType") Integer subType);

    @Select({
            "<script>",
            "select",
            "id, type, sub_type, level, cleared, produce_time, clear_time, clear_reason, ",
            "addr_type, addr_ias, addr_irri_fer, addr_farmland, addr_area, addr_parent_dev, ",
            "addr_dev, extension",
            "from warning",
            "where cleared = #{cleared,jdbcType=INTEGER}",
            "and (",
            "<if test='allPermissionIas != null'>",
            "(",
            "addr_ias in",
            "<foreach collection='allPermissionIas' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            ")",
            "<if test='retrievePermissionIas != null || farmlands != null'>",
            " or ",
            "</if>",
            "</if>",

            "<if test='retrievePermissionIas != null'>",
            "(",
            "addr_ias in",
            "<foreach collection='retrievePermissionIas' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "and addr_farmland is null",
            ")",
            "<if test='farmlands != null'>",
            " or ",
            "</if>",
            "</if>",

            "<if test='farmlands != null'>",
            "addr_farmland in",
            "<foreach collection='farmlands' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</if>",
            ")",

            "order by produce_time DESC",
            "<if test='limit > 0'>",
            "LIMIT #{offset},#{limit}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="sub_type", property="subType", jdbcType=JdbcType.INTEGER),
            @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
            @Result(column="cleared", property="cleared", jdbcType=JdbcType.BIT),
            @Result(column="produce_time", property="produceTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_time", property="clearTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_reason", property="clearReason", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_type", property="addrType", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_ias", property="addrIas", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_irri_fer", property="addrIrriFer", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_farmland", property="addrFarmland", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_area", property="addrArea", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_parent_dev", property="addrParentDev", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_dev", property="addrDev", jdbcType=JdbcType.INTEGER),
            @Result(column="extension", property="extension", jdbcType=JdbcType.OTHER)
    })
    List<WarningDO> listThresholdWarning(@Param(value = "allPermissionIas") LinkedList<Integer> allPermissionIas,
                              @Param(value = "retrievePermissionIas")LinkedList<Integer> retrievePermissionIas,
                              @Param(value = "farmlands")LinkedList<Integer> farmlands,
                              @Param(value = "cleared") Integer cleared,
                              @Param(value = "limit")Integer limit,
                              @Param(value = "offset")Integer offset);


    @Select({
            "<script>",
            "select",
            "count(*) as count",
            "from warning",
            "where cleared = 0",
            "and (",
            "<if test='allPermissionIas != null'>",
            "(",
            "addr_ias in",
            "<foreach collection='allPermissionIas' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            ")",
            "<if test='retrievePermissionIas != null || farmlands != null'>",
            " or ",
            "</if>",
            "</if>",

            "<if test='retrievePermissionIas != null'>",
            "(",
            "addr_ias in",
            "<foreach collection='retrievePermissionIas' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "and addr_farmland is null",
            ")",
            "<if test='farmlands != null'>",
            " or ",
            "</if>",
            "</if>",

            "<if test='farmlands != null'>",
            "addr_farmland in",
            "<foreach collection='farmlands' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</if>",
            ")",

            "</script>"
    })
    @Results({
            @Result(column="count", property="count", jdbcType=JdbcType.INTEGER),
    })
    int countThresholdWarning(@Param(value = "allPermissionIas") LinkedList<Integer> allPermissionIas,
                               @Param(value = "retrievePermissionIas")LinkedList<Integer> retrievePermissionIas,
                               @Param(value = "farmlands")LinkedList<Integer> farmlands);


    @Select({
            "select",
            "id, type, sub_type, level, cleared, produce_time, clear_time, clear_reason, ",
            "addr_type, addr_ias, addr_irri_fer, addr_farmland, addr_area, addr_parent_dev, ",
            "addr_dev, extension",
            "from warning",
            "where addr_ias = #{iasId,jdbcType=INTEGER}",
            "and cleared = 0",
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="sub_type", property="subType", jdbcType=JdbcType.INTEGER),
            @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
            @Result(column="cleared", property="cleared", jdbcType=JdbcType.BIT),
            @Result(column="produce_time", property="produceTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_time", property="clearTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_reason", property="clearReason", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_type", property="addrType", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_ias", property="addrIas", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_irri_fer", property="addrIrriFer", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_farmland", property="addrFarmland", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_area", property="addrArea", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_parent_dev", property="addrParentDev", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_dev", property="addrDev", jdbcType=JdbcType.INTEGER),
            @Result(column="extension", property="extension", jdbcType=JdbcType.OTHER)
    })
    LinkedList<WarningDO> listCurrWarningByIasId(Integer iasId);

    @Update({
            "<script>",
            "update warning",
            "set cleared = 1,",
            "clear_time = #{clearTime,jdbcType=TIMESTAMP},",
            "clear_reason = #{clearReason,jdbcType=INTEGER}",
            "where cleared = 0",

            "<if test='type != null'>",
            "and type = #{type,jdbcType=INTEGER}",
            "</if>",
            "<if test='subType != null'>",
            "and sub_type = #{subType,jdbcType=INTEGER}",
            "</if>",
            "and addr_type = #{addr.type,jdbcType=INTEGER}",
            "and addr_ias = #{addr.iasId,jdbcType=INTEGER}",

            "<if test='addr.irriFerId != null'>",
            "and addr_irri_fer = #{addr.irriFerId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.irriFerId == null'>",
            "and addr_irri_fer is null",
            "</if>",

            "<if test='addr.farmlandId != null'>",
            "and addr_farmland = #{addr.farmlandId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.farmlandId == null'>",
            "and addr_farmland is null",
            "</if>",

            "<if test='addr.irriAreaId != null'>",
            "and addr_area = #{addr.irriAreaId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.irriAreaId == null'>",
            "and addr_area is null",
            "</if>",

            "<if test='addr.parentDevId != null'>",
            "and addr_parent_dev = #{addr.parentDevId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.parentDevId == null'>",
            "and addr_parent_dev is null",
            "</if>",

            "<if test='addr.devId != null'>",
            "and addr_dev = #{addr.devId,jdbcType=INTEGER}",
            "</if>",
            "<if test='addr.devId == null'>",
            "and addr_dev is null",
            "</if>",
            "</script>"
    })
    int clearWarning(@Param(value = "type") Integer type, @Param(value = "subType")Integer sub_type,
                     @Param(value = "clearTime")Date clearTime, @Param(value = "clearReason")Integer clearReason,
                     @Param(value = "addr")IasObjectAddr addr);


    @Update({
            "update warning",
            "set cleared = 1,",
            "clear_time = #{clearTime,jdbcType=TIMESTAMP},",
            "clear_reason = #{clearReason,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=BIGINT}",
            "and cleared = 0"
    })
    int clearWarningById(@Param(value = "id")Long id, @Param(value = "clearTime")Date clearTime, @Param(value = "clearReason")Integer clearReason);

    @Select({
            "<script>",
            "select",
            "id, type, sub_type, level, cleared, produce_time, clear_time, clear_reason, addr_type, ",
            "addr_ias, addr_irri_fer, addr_farmland, addr_area, addr_parent_dev, addr_dev, ",
            "extension",
            "from warning",
            "where cleared = #{cleared,jdbcType=INTEGER}",
            "and type in ",
            "<foreach item='item' index='index' collection='type' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "and addr_ias = #{iasId,jdbcType=INTEGER}",

            "<if test='farmlands != null'>",
            "<if test='farmlands.size() != 0'>",
            "and addr_farmland in ",
            "<foreach item='item' index='index' collection='farmlands' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</if>",
            "<if test='farmlands.size() == 0'>",
            "and addr_farmland is null ",
            "</if>",
            "</if>",
            "order by produce_time desc, id desc ",
            "<if test='offset != null'>",
            " LIMIT #{offset},#{limit}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="sub_type", property="subType", jdbcType=JdbcType.INTEGER),
            @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
            @Result(column="cleared", property="cleared", jdbcType=JdbcType.BIT),
            @Result(column="produce_time", property="produceTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_time", property="clearTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="clear_reason", property="clearReason", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_type", property="addrType", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_ias", property="addrIas", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_irri_fer", property="addrIrriFer", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_farmland", property="addrFarmland", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_area", property="addrArea", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_parent_dev", property="addrParentDev", jdbcType=JdbcType.INTEGER),
            @Result(column="addr_dev", property="addrDev", jdbcType=JdbcType.INTEGER),
            @Result(column="extension", property="extension", jdbcType=JdbcType.OTHER)
    })
    LinkedList<WarningDO> listIasWarningByTypeAndFarmland(
            @Param(value = "cleared") Integer cleared,
            @Param(value = "type") LinkedList<Integer> type,
            @Param(value = "iasId")Integer iasId,
            @Param(value = "farmlands")LinkedList<Integer> farmlands,
            @Param(value = "offset")Integer offset,
            @Param(value = "limit")Integer limit);
}
