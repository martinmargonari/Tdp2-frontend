package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.ForumThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 27/10/16.
 */

public class ForumThreadsDTO extends AbstractDTO {

        public ArrayList<ForumThread> getData() { return (ArrayList<ForumThread>) data; }

        public void setData(List<ForumThread> data) {
            this.data = data;
        }

}
