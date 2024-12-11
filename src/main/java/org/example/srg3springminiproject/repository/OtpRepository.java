package org.example.srg3springminiproject.repository;

import org.apache.ibatis.annotations.*;
import org.example.srg3springminiproject.model.Otp;

import java.util.UUID;

@Mapper
public interface OtpRepository {
    @Results(id = "OtpMapper", value = {
            @Result(property = "id", column = "otp_id"),
            @Result(property = "otpCode", column = "otp_code"),
            @Result(property = "expirationTime", column = "expiration_time"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "verified", column = "verified"),
    })
    @Select("SELECT * FROM otp_tb WHERE user_id = CAST(#{userId} AS UUID) ORDER BY expiration_time DESC LIMIT 1 ")
    Otp getOtpByUserId(@Param("userId") UUID userId);

    @Select("INSERT INTO otp_tb (otp_code, expiration_time, user_id) " +
            "VALUES (#{otp.otpCode}, #{otp.expirationTime}, CAST(#{otp.userId} AS UUID) ) RETURNING *")
    @ResultMap("OtpMapper")
    Otp insertOtp(@Param("otp") Otp otp);

    @Select("UPDATE otp_tb SET verified = #{otpId.verified}, otp_code = #{otpId.otpCode}, expiration_time = #{otpId.expirationTime} WHERE otp_id = CAST(#{otpId.id} AS UUID)  RETURNING *")
    @ResultMap("OtpMapper")
    Otp updateOtp(@Param("otpId") Otp otpId);

    @Select("SELECT * FROM otp_tb WHERE otp_code = #{otpCode} AND verified = false ORDER BY expiration_time DESC LIMIT 1")
    @ResultMap("OtpMapper")
    Otp getLatestOtpByCode(String otpCode);

    @Select("SELECT * FROM otp_tb WHERE user_id = CAST((SELECT user_id FROM users_tb WHERE email = #{email}) AS UUID) AND verified = false ORDER BY expiration_time DESC LIMIT 1")
    @ResultMap("OtpMapper")
    Otp getLatestUnverifiedOtpByEmail(String email);
}
