package com.xy.wmall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.wmall.enums.ErrorCodeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.common.Assert;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.mapper.UserMapper;
import com.xy.wmall.model.User;
import com.xy.wmall.service.UserService;
import com.xy.wmall.common.utils.ListPageUtils;

/**
 * Service 实现
 * 
 * @author admin
 * @date 2017年12月01日 下午12:35:35
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
	private UserMapper userMapper;
	
	/**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public User selectByPrimaryKey(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
	    	return userMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     * @throws WmallException
     */
    @Override
    public User getUserById(Integer id) {
    	Assert.notNull(id, "id为空");
    	try {
    		Map<String, Object> map = new HashMap<>();
    		map.put("id", id);
    		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
	    	return userMapper.getUser(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + id + "】查询失败", e);
		}
    }
    
	/**
     * 保存数据
     *
     * @param user
     * @throws WmallException
     */
    @Override
    public void save(User user) {
    	Assert.notNull(user, "保存数据为空");
    	try {
			userMapper.insert(user);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_INSERT_ERROR, "【" + user.toString() + "】保存失败", e);
		}
    }

    /**
     * 修改数据
     *
     * @param user
     * @throws WmallException
     */
    @Override
    public void update(User user) {
    	Assert.notNull(user, "修改数据为空");
    	try {
    		userMapper.update(user);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_UPDATE_ERROR, "【" + user.toString() + "】修改失败", e);
		}
    }
    
    /**
     * 删除数据
     * 
     * @param user
     * @throws WmallException
     */
    @Override
    public void remove(User user) {
    	Assert.notNull(user, "删除数据为空");
		try {
    		User deleteUser = new User();
    		deleteUser.setId(user.getId());
    		deleteUser.setIsDelete(TrueFalseStatusEnum.TRUE.getValue());
    		userMapper.update(deleteUser);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_DELETE_ERROR, "【" + user.toString() + "】删除失败", e);
    	}
    }
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     * @throws WmallException
     */
    @Override
    public User getUser(Map<String, Object> map) {
    	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return userMapper.getUser(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询对象失败", e);
		}
    }
    
    /**
     * 根据map查询
     * 
     * @param map
     * @return
     * @throws WmallException
     */
    @Override
    public List<User> listUser(Map<String, Object> map) {
   	 	Assert.notEmpty(map, "查询数据为空");
    	try {
	    	return userMapper.listUser(map);
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_SELECT_ERROR, "【" + map + "】查询列表失败", e);
		}
    }
    
    /**
     * 批量保存
     * 
     * @param list
     * @throws WmallException
     */
    @Override
    public void batchSave(List<User> list) {
    	Assert.notEmpty(list, "批量保存数据为空");
    	try {
			List<List<User>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<User> page : pageList) {
				userMapper.batchInsert(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量保存失败", e);
		}
    }
    
    /**
     * 批量更新
     * 
     * @param list
     * @throws WmallException
     */
    @Override
    public void batchUpdate(List<User> list) {
    	Assert.notEmpty(list, "批量修改数据为空");
    	try {
			List<List<User>> pageList = ListPageUtils.listPage(list, 1000);
			for (List<User> page : pageList) {
				userMapper.batchUpdate(page);
			}
		} catch (Exception e) {
			throw new WmallException(ErrorCodeEnum.DB_BATCH_ERROR, "批量修改失败", e);
		}
    }
    
}
