package com.ymca.dao;

import com.ymca.model.TandC;

public interface TandCDao extends GenericDao<TandC, Long>  {
	TandC getByScId(Long ScId);
	TandC findByType(String type);
}
