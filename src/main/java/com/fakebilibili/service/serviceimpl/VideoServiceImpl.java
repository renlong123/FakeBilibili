package com.fakebilibili.service.serviceimpl;

import com.fakebilibili.dao.CategoryDAO;
import com.fakebilibili.dao.UserDAO;
import com.fakebilibili.dao.VideoDAO;
import com.fakebilibili.entity.Category;
import com.fakebilibili.entity.User;
import com.fakebilibili.entity.Video;
import com.fakebilibili.service.VideoService;
import com.fakebilibili.util.Progress;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDAO videoDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CategoryDAO categoryDAO;



    @Override
    public int insertVideo(HttpSession session, String name, String description,
                           String categoryId, MultipartFile[] uploadVideoInfo) throws IOException {

        String absolutePath = "D:\\gitRespository\\FakeBilibili\\uploadFiles\\";

        if(name == null || name.equals("") || uploadVideoInfo == null){
            return 0;
        }else{
            String userId = session.getAttribute("userId").toString();

            Video video = new Video();
            video.setName(name);
            video.setDescription(description);
            video.setCategoryId(Integer.parseInt(categoryId));
            video.setAuthorid(Integer.parseInt(userId));
            video.setDislikeNumber(0);
            video.setLikeNumber(0);
            video.setPlayTimes(0);
            video.setPublishedTime(new java.sql.Date(System.currentTimeMillis()));
            if(uploadVideoInfo.length>=2){
                if(uploadVideoInfo[0]!=null){
                    String orignalPicName = uploadVideoInfo[0].getOriginalFilename();
                    String newFileName = concatNewName(orignalPicName,userId);
                    File file = new File(absolutePath+newFileName);
                    String mappedCoverPath = "/upload/"+newFileName;
                    uploadVideoInfo[0].transferTo(file);
                    video.setVedioCoverPic(mappedCoverPath);
                }
                if(uploadVideoInfo[1]!=null){
                    String orignalPicName = uploadVideoInfo[1].getOriginalFilename();
                    String newFileName = concatNewName(orignalPicName,userId);
                    File file = new File(absolutePath+"video\\"+newFileName);
                    String videoAddr = "/upload/video/"+newFileName;

                    uploadVideoInfo[1].transferTo(file);
                    video.setVedioAddr(videoAddr);
                }
            }
            System.out.println(video);
            return videoDAO.insertVideo(video);
        }
    }

    /**
     * 获取一个用户上传的所有视频
     * @param userId
     * @return
     */
    @Override
    public String getVideoInfoByUserId(Integer userId,int page,int pageSize){
        PageHelper.startPage(page,pageSize);
        List<Video> videos = videoDAO.getVideoInfoByUserId(userId);
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        Gson gson = new Gson();
        String json = gson.toJson(pageInfo);
        return json;
    }

    /**
     * 获取视频信息并转换为JSON
     * @param id
     * @return
     */
    @Override
    public String getOneVideoById(Integer id) {
        Video oneVideoById = videoDAO.getOneVideoById(id);
        Gson gson = new Gson();
        String json = gson.toJson(oneVideoById);
        return json;
    }

    @Override
    public int deleteVideoById(Integer id) {
        Video video = videoDAO.getOneVideoById(id);
        String absolutePath = "D:\\gitRespository\\FakeBilibili\\uploadFiles\\";
        String path = video.getVedioCoverPic();
        /*得到服务器上的文件*/
        File file = new File(absolutePath+path.substring(8));
        file.delete();
        path = video.getVedioAddr();
        file = new File(absolutePath+"video\\"+path.substring(8));
        file.delete();

        int i = videoDAO.deleteVideoById(id);
        return i;
    }

    /**
     * 获取视频信息及相关的user、category、contents信息
     * @param id
     * @param model
     * @return
     */
    @Override
    public String getVideoAndOtherInfo(Integer id, Model model) {
        Video oneVideoById = videoDAO.getOneVideoById(id);
        User user = userDAO.selectUserById(oneVideoById.getAuthorid());
        Category categoryById = categoryDAO.getCategoryById(oneVideoById.getCategoryId());
        model.addAttribute("videoInfo",oneVideoById);
        model.addAttribute("userInfo",user);
        model.addAttribute("categoryInfo",categoryById);
        System.out.println(categoryById);
        System.out.println(user);
        return null;
    }


    /**
     下边这个是好方法，希望各位能用起来，虽然是个小方法，但我其实真不舍得贴出来，是JAVA自带的方法
     **/
    public File getTmpFile(String fileName) {
        File tmpDir = FileUtils.getTempDirectory();
        System.out.println("========"+tmpDir.getAbsolutePath());
        String tmpFileName = (Math.random() * 10000 + "").replace(".", "")+"_"+fileName;
        return new File(tmpDir, tmpFileName);
    }

    public String concatNewName(String orignalName,String userId){

        String[] nameParts = orignalName.split("\\.");
        System.out.println(nameParts.length);
        UUID uuid = UUID.randomUUID();
        return uuid+userId+"."+nameParts[nameParts.length-1];
    }
}
