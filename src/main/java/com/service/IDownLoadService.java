package com.service;

import com.entity.SSQ;

import java.util.Map;

public interface IDownLoadService {

    Map<String,String> downloadSSQ(String pageContent, String period);

    Map<String,String> downloadDLT(String pageContent, String period);

}
