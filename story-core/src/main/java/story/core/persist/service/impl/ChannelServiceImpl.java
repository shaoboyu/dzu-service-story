/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package story.core.persist.service.impl;

import java.util.*;

import story.base.lang.Consts;
import story.core.data.Channel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import story.core.persist.dao.ChannelDao;
import story.core.persist.entity.ChannelPO;
import story.core.persist.service.ChannelService;
import story.core.persist.utils.BeanMapUtils;

/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private ChannelDao channelDao;

	@Override
	public List<Channel> findAll(int status) {
		List<ChannelPO> list;
		if (status > Consts.IGNORE) {
			list = channelDao.findAllByStatus(status);
		} else {
			list = channelDao.findAll();
		}
		List<Channel> rets = new ArrayList<>();
		list.forEach(po -> rets.add(BeanMapUtils.copy(po)));
		return rets;
	}

	/**
	 * 接力文章扫描
	 * */
	@Override
	public Map<Integer, Channel> findMapByIds(Collection<Integer> ids) {
		List<ChannelPO> list = channelDao.findAllByIdIn(ids);
		Map<Integer, Channel> rets = new HashMap<>();
		list.forEach(po -> rets.put(po.getId(), BeanMapUtils.copy(po)));
		return rets;
	}

	@Override
	@Cacheable(value = "groupsCaches", key = "'g_' + #id")
	public Channel getById(int id) {
		return BeanMapUtils.copy(channelDao.findOne(id));
	}

	@Override
	@Cacheable(value = "groupsCaches", key = "'g_' + #key")
	public Channel getByKey(String key) {
		return BeanMapUtils.copy(channelDao.findByKey(key));
	}

	@Override
	@Transactional
	public void update(Channel channel) {
		ChannelPO po = channelDao.findOne(channel.getId());
		if (po != null) {
			BeanUtils.copyProperties(channel, po);
		} else {
			po = new ChannelPO();
			BeanUtils.copyProperties(channel, po);
		}
		channelDao.save(po);
	}

	@Override
	@Transactional
	public void delete(int id) {
		channelDao.delete(id);
	}

}
