package com.yoyo.service;


import com.yoyo.entity.Communities;

import java.util.List;

public interface ICommunitiesService {



        /**
         * 获取列表
         *
         * @return
         */
        List<Communities> getList();

        /**
         * 通过id查询
         *
         * @param id
         * @return
         */
        Communities get(int id);
        Communities getByName(String name);

        /**
         * 添加
         *
         * @param communities
         * @return
         */
        Integer add(Communities communities);

        /**
         * 更新
         *
         * @param communities
         */
        boolean update(Communities communities);

        /**
         * 删除
         *
         * @param communities
         */
        boolean delete(Communities communities);

        List<Communities> getOtherList(int id);

}
