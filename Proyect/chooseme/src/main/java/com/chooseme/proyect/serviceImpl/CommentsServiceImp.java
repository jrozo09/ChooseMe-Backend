package com.chooseme.proyect.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.chooseme.proyect.dto.CommentsDTO;
import com.chooseme.proyect.entities.Comments;
import com.chooseme.proyect.entities.Users;
import com.chooseme.proyect.repository.CommentsRepository;
import com.chooseme.proyect.repository.UsersRepository;
import com.chooseme.proyect.service.CommentsService;
import com.chooseme.proyect.util.CommentSorter;


@Service
public class CommentsServiceImp implements CommentsService{

	@Autowired
	UsersRepository userRepo;
	@Autowired
	CommentsRepository commentRepo;
	Comments comm;
	Users user;
	@Override
	public boolean newComment(Comments comment, String name) {
		
		int id;
		user = userRepo.getUserByUsername(name);
		
		try  {
			 id = user.getUser_id();
			 comment.setReviewerid(id);
			 comm = commentRepo.newReview(comment.getProduct_id(), comment.getReviewerid());
			 System.out.println(comm);
			 
		}
		catch (NullPointerException ne) {	
		}
		
		if(comm == null) {
			commentRepo.save(comment); 
			return true;
		 }else {
			 return false;
		 }
		
		
		
		
		
	}
	@Override
	public Iterable<Comments> findCommentsFiltered (CommentsDTO filter, int page) {
		Set<Comments> searchSet = new HashSet<Comments>();
		
		boolean firstFilter = true; 

		if(!(filter.getScore() == null)) {
			Set<Comments> tempSet = new HashSet<Comments>();
			findCommentByScore(filter).forEach((e) -> {
				tempSet.add(e);
			});
			if (firstFilter) {
				searchSet.addAll(tempSet);
				firstFilter = false;
			} else {
				searchSet.retainAll(tempSet);
			}
		} 
		/*
		if(!(filter.getPopularity== null)) {
			Set<Comments> tempSet = new HashSet<Comments>();
			CommentService.findCommentByScore(Double.parseDouble(filter.getStars_puntuation()), Double.parseDouble(filter.getStars_puntuation())+1).forEach((e) -> {
				tempSet.add(e);
			});
			if (firstFilter) {
				searchSet.addAll(tempSet);
				firstFilter = false;
			} else {
				searchSet.retainAll(tempSet);
			}
		} */
		
		if(!(filter.getCreated_at() == null)) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -(Integer.parseInt(filter.getCreated_at())));
			String nowStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			String fromStamp = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			
			Set<Comments> tempSet = new HashSet<Comments>();
			findByDate(fromStamp,nowStamp).forEach((e) -> {
				tempSet.add(e);
			});
			if (firstFilter) {
				searchSet.addAll(tempSet);
				firstFilter = false;
			} else {
				searchSet.retainAll(tempSet);
			}
		} if (filter.getComment_id() !=  0) {
			Set<Comments> tempSet = new HashSet<Comments>();
			commentRepo.findByIdx(filter.getComment_id(), PageRequest.of(page, 10)).forEach((e) -> {
				tempSet.add(e);
			});
			if (firstFilter) {
				searchSet.addAll(tempSet);
				firstFilter = false;
			} else {
				searchSet.retainAll(tempSet);
			}
		}
		
		if (filter.getReviewer_id() !=  0) {
			Set<Comments> tempSet = new HashSet<Comments>();
			commentRepo.findByIdy(filter.getComment_id(), PageRequest.of(page, 10)).forEach((e) -> {
				tempSet.add(e);
			});
			if (firstFilter) {
				searchSet.addAll(tempSet);
				firstFilter = false;
			} else {
				searchSet.retainAll(tempSet);
			}
		}
		
		if (searchSet.isEmpty()) {
			return null;
		}
		
		if (searchSet.isEmpty()) {
			return null;
		}
		
		List<Comments> retL = searchSet.stream().collect(Collectors.toList());
		Collections.sort(retL, new CommentSorter());
		return retL;
		
	}
	
	
	private Iterable<Comments> findByDate(String fromStamp, String nowStamp) {
		return commentRepo.getByDate(fromStamp,nowStamp);
	}
	
	private Iterable<Comments> findCommentByScore(CommentsDTO filter) {
		return commentRepo.findByScore(filter.getScore());
	}
	
	@Override
	public Iterable<Comments> findByProductId(int id, int page) {
		return  commentRepo.findByIdx(id, PageRequest.of(page, 10));
	}
	@Override
	public Iterable<Comments> findByReviewerId(int id, int page) {

		return commentRepo.findByIdy(id, PageRequest.of(page, 10));
	}
	
	Comments comment;
	
	public boolean deleteComent(int id) {
		comment = null;
		try {
			comment = (Comments) commentRepo.getById(id);
			comment.setComment("Borrado");
			comment.setScore(-1);
	        commentRepo.save(comment);
	        return true;
		}
		catch(NullPointerException np) {
			return false;
		}
		
	}
	
	

}