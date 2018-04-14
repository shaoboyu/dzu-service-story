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

import story.core.persist.entity.UserPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *     <b>使用JpaRepository进行简单查询</b>
 *     <b>JpaSpecificationExecutor进行复杂查询你</b>
 * </p>
 * @author yushaobo
 */
public interface UserDao extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {
    UserPO findByUsername(String username);

    UserPO findByEmail(String email);
    
    List<UserPO> findTop12ByOrderByFansDesc();

    Page<UserPO> findAllByOrderByIdDesc(Pageable pageable);

    List<UserPO> findAllByIdIn(Set<Long> ids);

}
