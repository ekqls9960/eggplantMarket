package teamB.market.domain.member.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import teamB.market.domain.member.Member;

@Mapper
public interface MemberMapper {
	// null 값이 들어갈 수 있는 경우 jdbcType 지정해주기
	@Insert("insert into member(" + "id,email,name,pwd,nickname,addr,route,phoneNum,emailAuthCode,isEmailAuth) "
			+ "values(member_seq.nextval,#{email},#{name},#{pwd},#{nickname},#{addr},#{route},#{phoneNum},#{emailAuthCode,jdbcType=VARCHAR},#{isEmailAuth})")
	void save(Member member);

	@Select("select * from member where id=#{id}")
	Member findById(long id);

	@Select("select * from member where email=#{email,jdbcType=VARCHAR}")
	Member findByEmail(String email);

	@Select("select * from member where phoneNum=#{phoneNum}")
	Member findByPhoneNum(String phoneNum);

	// parameter가 두개 이상인 경우 @Param 으로 접근 이름 지정해주기
	@Update("update member set isEmailAuth=#{mem.isEmailAuth} where id=#{id}")
	void updateIsEmailAuth(@Param("id") long id, @Param("mem") Member updateParam);

	@Update("update member set nickname=#{mem.nickname},phoneNum=#{mem.phoneNum},pwd=#{mem.pwd},addr=#{mem.addr}"
			+ " where id=#{id}")
	void update(@Param("id") long id, @Param("mem") Member updateParam);

	@Update("update member set role='SELLER' where id=#{id}")
	void updateRole(long id);

	@Update("update member set pwd=#{pwd} where id=#{id}")
	void updatePwd(@Param("id") long id, @Param("pwd") String pwd);

	@Update("update member set point=#{point} where id=#{id}")
	void updatePoint(@Param("point") int point, @Param("id") long id);

	@Update("update member set ratingAvg=#{ratingAvg} where id=#{id}")
	void updateRatingAvg(@Param("ratingAvg") float ratingAvg, @Param("id") long id);

	@Delete("delete from member where id=#{id}")
	void delete(long id);
}
