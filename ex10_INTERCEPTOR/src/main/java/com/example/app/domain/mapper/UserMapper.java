package com.example.app.domain.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.app.domain.dto.UserDto;

@Mapper

public interface UserMapper {

	@Insert(value = "insert into tbl_user2 values(#{userid}, #{password}, #{rePassword}, #{username}, #{phone}, #{zipcode}, #{addr1}, #{addr2}, #{birthday})")
	public int insert(UserDto userDto);

	@Update("update tbl_user2 set addr1=#{addr1} where userid=#{userid}")
	public int update(UserDto userDto);

	@Delete("delete from tbl_user2 where userid=#{userid}")
	public int delete(String userid);

	@Select("select * from tbl_user2 where userid=#{userid}")
	public UserDto selectAt(String userid);

	@Select("select * from tbl_user2")
	public List<UserDto> selectAll();
	
	// XML
	public List<Map<String, Object>> selectAllResultMapXml();

}
