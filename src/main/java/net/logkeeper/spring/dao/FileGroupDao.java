package net.logkeeper.spring.dao;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.logkeeper.spring.model.FileGroup;

@Repository
public class FileGroupDao {
	 @Autowired
	SessionFactory sessionFactory;
	private static final Logger LOGGER = Logger.getLogger(FileGroupDao.class.getName());



	public void save(FileGroup fileGroup) {
		getCurrentSession().save(fileGroup);
		
	}

	public void saveOrUpdate(FileGroup fileGroup) {
		getCurrentSession().saveOrUpdate(fileGroup);
	}

	public void update(FileGroup fileGroup) {
		getCurrentSession().update(fileGroup);
	}

	public void delete(FileGroup fileGroup) {
		try {
			getCurrentSession().delete(fileGroup);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("File group silindi. " + fileGroup.getName());
			}
		} catch (Exception ex) {
			if (LOGGER.isEnabledFor(Level.ERROR)) {
				LOGGER.error("File group silinemedi." + ex.toString());
			}
		}
	}

	public void delete(int id) {
		try {
			String hql = "DELETE FROM FileGroup " + "WHERE id = :id";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("id", id);
			int result = query.executeUpdate();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("File group silindi. ");
			}
		} catch (Exception ex) {
			if (LOGGER.isEnabledFor(Level.ERROR)) {
				LOGGER.error("File group silinemedi." + ex.toString());
			}
		}
	}

	public List listFileGroupUser() {
		List fileGroupList = getCurrentSession().createQuery("from FileGroup").list();
		return fileGroupList;
	}

	public List listNameFileGroup() {
		return getCurrentSession().createQuery("Select id,name from FileGroup").list();
	}

	public List listFileGroupMerge(String merge) {
		Query query = getCurrentSession().createQuery("from FileGroup where name in :merge");
		List fileGroupList = query.setParameter("merge", merge).list();
		return fileGroupList;
	}

	public List listTxtSearchFile(String languagePrefix) {
		Query query = getCurrentSession().createQuery("from FileGroup c where c.name like :fileGrp");
		List fileGrpList = query.setParameter("fileGrp", "%" + languagePrefix + "%").list();
		return fileGrpList;
	}
	
	public List listFileGroup(String filey) {
		Query query = getCurrentSession().createQuery("from FileGroup where name like :filey");
		List fileGroupList = query.setParameter("filey", filey + "%").list();
		return fileGroupList;
	}

	public List lisTagFileGroup(String tag) {
		Query query = getCurrentSession()
				.createQuery("select fg.id,fg.name,fg.user,fg.createDate from FileGroup fg join fg.tags t where t.name in (:name)");
		List fileGroupList = query.setParameter("name", tag).list();
		return fileGroupList;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public FileGroup findByFileGroup(int id) {
		String hql = "from FileGroup where id= :id";
		Query query = getCurrentSession().createQuery(hql);
		query.setInteger("id", id).setMaxResults(1);
		return (FileGroup) query.uniqueResult();
	}
	public FileGroup findByFileGroupName(String name) {
		String hql = "from FileGroup where name= :name";
		Query query = getCurrentSession().createQuery(hql);
		query.setString("name", name).setMaxResults(1);
		return (FileGroup) query.uniqueResult();
	}

	public List<Integer> getFilesByTag(List<Integer> tags) {
		String hql = "select fg.id from FileGroup fg join fg.tags t where t.id in (:tagg) group by fg.id having count(fg)=:listCount";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameterList("tagg", tags);
		query.setInteger("listCount", tags.size());
		List<Integer> fileGroups = query.list();
		// Criteria crit = getCurrentSession().createCriteria(Name.class);
		//
		// crit.createAlias("categories", "tagAlias", Criteria.INNER_JOIN);
		//
		// Conjunction conjunction = Restrictions.conjunction();
		//
		// for (String tag : tags) {
		// conjunction.add(Restrictions.eq("tagAlias.fileCategory",tag));
		// }
		// crit.add(conjunction);
		//
		return fileGroups;

	}
	

}
