/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package story.core.persist.dao;

import story.core.persist.dao.custom.FeedsDaoCustom;
import story.core.persist.entity.FeedsPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author langhsu
 *
 */
public interface FeedsDao extends JpaRepository<FeedsPO, Long>, JpaSpecificationExecutor<FeedsPO>, FeedsDaoCustom {
	Page<FeedsPO> findAllByOwnIdOrderByIdDesc(Pageable pageable, long ownId);
	int deleteAllByOwnIdAndAuthorId(long ownId, long authorId);
	void deleteAllByPostId(long postId);
}
