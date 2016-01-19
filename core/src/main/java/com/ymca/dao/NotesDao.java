package com.ymca.dao;

import com.ymca.model.Notes;


public interface NotesDao  extends GenericDao<Notes, Long>  {
	
	Notes findByNoteTypeAndOffenderId(String noteType, String offenderId);
}
