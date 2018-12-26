package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.VerificationRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by HuXin on 2018/1/4.
 */
@Component
@Mapper
public interface VerificationRecordExtMapper extends VerificationRecordMapper {
    @Insert({
            "insert into verification_record (mobile, type, ",
            "code, send_time)",
            "values (#{mobile,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, ",
            "#{code,jdbcType=VARCHAR}, #{sendTime,jdbcType=TIMESTAMP})",
            "on duplicate key update",
            "code = #{code,jdbcType=VARCHAR},",
            "send_time = #{sendTime,jdbcType=TIMESTAMP}"
    })
    int upsert(VerificationRecord record);
}
