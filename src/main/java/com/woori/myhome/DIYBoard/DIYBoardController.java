package com.woori.myhome.DIYBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.woori.myhome.common.FileUploadUtil;
import com.woori.myhome.comment.CommentDto;
 
@Controller
public class DIYBoardController {

	@Resource(name = "DIYboardService")
	DIYBoardService service;

	
	@RequestMapping("/gallery/list")
	String gallery_list(Model model, DIYBoardDto dto) {
		System.out.println("[controller] gallery_list");
		dto.setPageSize(12);
		dto.setStart(dto.getPg() * dto.getPageSize());

		List<DIYBoardDto> list = service.getList(dto);

		System.out.println("list: " + list);

		int cnt = service.getTotal(dto);

		model.addAttribute("galleryList", list);
		model.addAttribute("totalCnt", cnt);

		return "DIYBoard/gallery_list";
	}
	@RequestMapping("/gallery/list/hit")
	String gallery_list_Hit(Model model, DIYBoardDto dto) {
		System.out.println("[controller] gallery_list");
		dto.setPageSize(12);
		dto.setStart(dto.getPg() * dto.getPageSize());

		List<DIYBoardDto> list = service.getListHit(dto);

		System.out.println("fensfsnff list: " + list);

		int cnt = service.getTotal(dto);

		model.addAttribute("galleryList", list);
		model.addAttribute("totalCnt", cnt);

		return "DIYBoard/gallery_list";
	}
	@RequestMapping("/gallery/write")
	String gallery_write(Model model, DIYBoardDto paramDto) {
		System.out.println("gallery_write : " + paramDto);

		if (paramDto.getId().equals("")) {
			model.addAttribute("galleryDto", new DIYBoardDto());
		} else
			model.addAttribute("galleryDto", service.getView(paramDto));

		return "DIYBoard/gallery_write";
	}

	@RequestMapping("/gallery/save")
	String gallery_save(DIYBoardDto dto, String upload) {	
		
	//	System.out.println("--------------------"+ upload);
		
		if (!upload.isBlank()) {
			dto.setImage(upload);
	//		System.out.println( upload);
		} else
			dto.setImage("default.jpg");

		if (dto.getId().equals(""))
			service.insert(dto);
		else
			service.update(dto);
		return "redirect:/gallery/list";

	}

//	
	@RequestMapping("/gallery/view")
	String gallery_view(DIYBoardDto dto, Model model) {
		DIYBoardDto resultDto = service.getView(dto);
		service.updateView(dto);
		
		model.addAttribute("galleryDto", resultDto);

		return "DIYBoard/gallery_view";
	}

	@RequestMapping("/gallery/delete")
	String gallery_delete(DIYBoardDto dto) {

		service.delete(dto);

		return "redirect:/gallery/list";
	}

	@RequestMapping(value = "/gallery/modify")
	String gallery_modify(DIYBoardDto dto, Model model) {
	//	System.out.println("-------------------modify");

		DIYBoardDto resultDto = service.getView(dto);
		model.addAttribute("galleryDto", resultDto);

		return "DIYBoard/gallery_write";
	}

	@ResponseBody 
	@RequestMapping(value = "/ck/fileupload2", method = { RequestMethod.POST, RequestMethod.GET })
	public String fileUpload(DIYBoardDto dto, HttpServletRequest req, MultipartHttpServletRequest multi) {

		List<MultipartFile> multiList = new ArrayList<MultipartFile>();
		multiList.add(multi.getFile("upload"));
	//	System.out.println( multi.getFile("upload").getOriginalFilename());
		List<String> fileNameList = new ArrayList<String>();

		String path = req.getServletContext().getRealPath("/");
	//	System.out.println(path);

		
		FileUploadUtil.upload(path, multiList, fileNameList);
		
		System.out.println("{ \"uploaded\" : true, \"url\" : \"http://localhost:8080/myhome/upload/"
				+ fileNameList.get(0) + "\" }");
		String url = "{ \"uploaded\" : true, \"url\" : \"http://localhost:8080/myhome/upload/" + fileNameList.get(0) + "\" }";
	
		return url;
	}
	
	
//************** comment ************************
	
	@RequestMapping(value="/gallery/comment/write")
	@ResponseBody
	HashMap<String, String> comment_write(CommentDto dto)
	{
		dto.setComment_board_loc("2");
		System.out.println("comment_id : " + dto.getComment_id());
		if( dto.getComment_id().equals(""))
			service.comment_insert(dto);
		else
			service.comment_update(dto);
	
//		service.comment_insert(dto);
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("result", "success");
		return map; 
	}
	
	@RequestMapping(value="/gallery/comment/list")
	@ResponseBody
	List<CommentDto> comment_list(CommentDto dto)
	{
		dto.setComment_board_loc("2");
		System.out.println("board_id : " + dto.getComment_board_id());
		List<CommentDto> list = service.getCommentList(dto);
		return list; 
	}
	
	
	@RequestMapping(value="/gallery/comment/getView")
	@ResponseBody
	CommentDto comment_getView(CommentDto dto)
	{
		System.out.println("id : " + dto.getComment_id());
		CommentDto resultDto = service.getCommentView(dto);
		return resultDto; 
	}
	
	@RequestMapping(value="/gallery/comment/update")
	@ResponseBody
	HashMap<String, String> comment_update(CommentDto dto) {
		
		service.comment_update(dto);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("result", "success");
		
		return map;
	}
	
	@RequestMapping(value="/gallery/comment/delete")
	@ResponseBody
	HashMap<String, String> comment_delete(CommentDto dto)
	{
		service.comment_delete(dto);
		
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("result", "success");
		return map; 
	}	
	

}
