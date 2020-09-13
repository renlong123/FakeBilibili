package com.fakebilibili.listener;


import com.fakebilibili.util.Progress;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class FileUploadListener implements ProgressListener {

    private HttpSession session;

    public void setSession(HttpSession session){
        this.session = session;
        Progress status = new Progress();
        session.setAttribute("status",status);
    }

    @Override
    public void update(long l, long l1, int i) {
        Progress status = (Progress) session.getAttribute("status");
        status.setBytesRead(l);
        status.setContentLength(l1);
        status.setItems(i);
    }
}
