package com.maomi.mapper;

import com.maomi.domain.MbUser;

public interface MbUserMapper {
 
	/**
	 * 
	 * @描述:  用户注册
	 * @方法名: insertMbUser
	 * @param user
	 * @return
	 * @返回类型 int
	 * @创建人 zflu
	 * @创建时间 2018年4月12日 15:37:38
	 */
    int insertMbUser(MbUser user);
    
    /**
	 * 
	 * @描述:  使用帐号和密码查询用户信息
	 * @方法名: queryUserByLogin
	 * @param user
	 * @return	
	 * @返回类型 MbUser
	 * @创建人 zflu
	 * @创建时间 2018年4月12日 15:37:38
	 */
    MbUser queryUserByLogin(MbUser user);

    /**
	 * 
	 * @描述:  使用用户id查询用户信息
	 * @方法名: queryUserById
	 * @param id
	 * @return	
	 * @返回类型 MbUser
	 * @创建人 zflu
	 * @创建时间 2018年4月12日 15:37:38
	 */
    MbUser queryUserById(Long id);
    
    /**
	 * 
	 * @描述:  根据openId查询用户
	 * @方法名: queryUserByOpenId
	 * @param openId
	 * @return
	 * @返回类型 MbUser
	 * @创建人 zflu
	 * @创建时间 2018年4月14日下午5:20:46
	 */
    MbUser queryUserByOpenId(String openId);
    
    /**
     * 
     * @描述:  更新用户openId
     * @方法名: updateUserOpenId
     * @param user
     * @return
     * @返回类型 int
     * @创建人 zflu
     * @创建时间 2018年4月14日下午5:46:34
     */
    int updateUserOpenId(MbUser user);

}