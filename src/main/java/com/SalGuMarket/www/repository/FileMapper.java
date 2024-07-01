package com.SalGuMarket.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.PagingVO;

@Mapper
public interface FileMapper {

	int insertFile(FileVO fvo);

	List<FileVO> getFileList(long bno);

	int saveProductFile(FileVO fvo);

	List<FileVO> getCategoriesSliderImageList10Image();

	List<FileVO> get8MainImage(PagingVO pgvo);

	FileVO getMainImageByPno(Long pno);

	List<FileVO> getMinorIamgeListByPno(Long pno);

	void deleteFile(String email);

	String getFileName(String email);

	FileVO getFile(String email);

	int insertProfile(FileVO fvo);
	
	List<FileVO> selectProfile(String email);

	List<FileVO> getHelpFileList(long hbno);

	List<FileVO> getNoticeFileList(long noBno);

	List<FileVO> selectListAllFile();

	List<FileVO> selectBnoAllFile(long bno);
}
