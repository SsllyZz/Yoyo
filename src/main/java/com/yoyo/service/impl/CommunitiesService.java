package com.yoyo.service.impl;

import com.yoyo.dao.CommunitiesDAO;
import com.yoyo.dao.UsersDao;
import com.yoyo.entity.Communities;
import com.yoyo.service.ICommunitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 类型服务
 */
@Service    // 注解为service层spring管理bean
@Transactional    // 注解此类所有方法加入spring事务, 具体设置默认
public class CommunitiesService implements ICommunitiesService {
    @Autowired
    private CommunitiesDAO communitiesDAO;
    @Autowired
    private UsersDao usersDao;
    /**
     * 获取列表
     * @return
     */
    public List<Communities> getList(){
        return communitiesDAO.getList();
    }



    /**
     * 通过id查询
     * @param id
     * @return
     */
    public Communities get(int id) {
        return communitiesDAO.selectById(id);
    }
    public Communities getByName(String name) {
        return communitiesDAO.getByName(name);
    }
    /**
     * 添加
     * @param communities
     * @return
     */
    public Integer add(Communities communities) {
        return communitiesDAO.insert(communities);
    }

    /**
     * 更新
     * @param communities
     */
    public boolean update(Communities communities) {
        return communitiesDAO.updateById(communities) > 0;
    }

    /**
     * 删除
     * @param communities
     */
    public boolean delete(Communities communities) {
        return communitiesDAO.deleteById(communities.getId()) > 0;
    }


    public List<Communities> getOtherList(int id) {
        List<Communities>communities=communitiesDAO.getList();
        String address= usersDao.selectById(id).getAddress();
        communities.removeIf(c-> Objects.equals(c.getName(), address));
        return communities;
    }


}
