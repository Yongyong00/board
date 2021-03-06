package com.woori.myhome.admin.DIYBoard;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woori.myhome.comment.CommentDao;
import com.woori.myhome.comment.CommentDto;
 
@Service("adminDIYboardService")
public class AdminDIYBoardServiceImpl implements AdminDIYBoardService{

	@Resource(name="adminDIYboardDao")
	AdminDIYBoardDao dao;
	
	@Resource(name="commentDao")
	CommentDao commentDao;
	
	@Override
	public List<AdminDIYBoardDto> getList(AdminDIYBoardDto dto) {
		// TODO Auto-generated method stub
		return dao.getList(dto);
	}
	
	@Override
	public List<AdminDIYBoardDto> getListHit(AdminDIYBoardDto dto) {
		// TODO Auto-generated method stub
		return dao.getListHit(dto);
	}

	@Override
	public int getTotal(AdminDIYBoardDto dto) {
		// TODO Auto-generated method stub
		return dao.getTotal(dto);
	}

	@Override
	public void insert(AdminDIYBoardDto dto) {
		dao.insert(dto);
	}

	@Override
	public void update(AdminDIYBoardDto dto) {
		dao.update(dto);
	}

	@Override
	public void delete(AdminDIYBoardDto dto) {
		dao.delete(dto);
	}

	@Override
	public AdminDIYBoardDto getView(AdminDIYBoardDto dto) {
		// TODO Auto-generated method stub
		return dao.getView(dto);
	}

	@Override
	public void updateView(AdminDIYBoardDto dto) {
		dao.updateView(dto);
		
	}

	

	


}
