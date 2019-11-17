package com.service.impl;

import com.dao.SSQDao;
import com.entity.SSQ;
import com.service.IDownLoadService;
import com.util.PageDownLoadUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 这个类名取得失误了，不应该只交双色球
 */
@Component("sSQDownLoadImpl")
public class SSQDownLoadImpl implements IDownLoadService {


    @Override
    public Map<String,String> downloadSSQ(String pageContent,String periodNum) {

        //利用htmlCleaner来获取爬取的html本文的tagNode所有节点
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode tagNodeRoot = htmlCleaner.clean(pageContent);//获取根tagNode

        Map<String,String> map = new HashMap<String,String>(12);//用来存储双色球
        try {
            //evaluateXPath：最好是这种属性方式的，网页直接获取的xpath没有用：//表示从根节点开始查询
            Object[] objects = tagNodeRoot.evaluateXPath("//li[@class=\"ball_red\"]");
            if(objects!=null && objects.length>0){ //如果有红球，才解析红球后再解析篮球，否则无数据解析
                map.put("periodNum",periodNum);
                for (int i = 0; i <objects.length ; i++) {
                    TagNode tagNode = (TagNode)objects[i];
                    String red = tagNode.getText().toString();
                    map.put("red"+(i+1),red);
                }

                //然后解析篮球
                Object[] objectsBlue = tagNodeRoot.evaluateXPath("//li[@class=\"ball_blue\"]");
                TagNode tagNode = (TagNode)objectsBlue[0];
                String blue = tagNode.getText().toString();
                map.put("blue",blue);
                System.out.println("==========当期{"+periodNum+"}双色球为："+map);
            }else{
                System.out.println("==========xpath:该期{"+periodNum+"}没有双色球对应的数据,解析到的数据为空！==========");
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }

        return map;
    }



    @Override
    public Map<String, String> downloadDLT(String pageContent, String periodNum) {
        //利用htmlCleaner来获取爬取的html本文的tagNode所有节点
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode tagNodeRoot = htmlCleaner.clean(pageContent);//获取根tagNode

        Map<String,String> map = new HashMap<String,String>(12);//用来存储大乐透
        try {
            //evaluateXPath：最好是这种属性方式的，网页直接获取的xpath没有用：//表示从根节点开始查询
            Object[] objects = tagNodeRoot.evaluateXPath("//li[@class=\"ball_red\"]");
            if(objects!=null && objects.length>0){ //如果有红球，才解析红球后再解析篮球，否则无数据解析
                map.put("periodNum",periodNum);
                for (int i = 0; i <objects.length ; i++) {
                    TagNode tagNode = (TagNode)objects[i];
                    String red = tagNode.getText().toString();
                    map.put("red"+(i+1),red);
                }

                //然后解析篮球
                Object[] objectsBlue = tagNodeRoot.evaluateXPath("//li[@class=\"ball_blue\"]");
                for (int i = 0; i < objectsBlue.length; i++) {
                    TagNode tagNode = (TagNode)objectsBlue[i];
                    String blue = tagNode.getText().toString();
                    map.put("blue"+(i+1),blue);
                }
                System.out.println("==========当期{"+periodNum+"}大乐透为："+map);
            }else{
                System.out.println("==========xpath:该期{"+periodNum+"}没有大乐透对应的数据,解析到的数据为空！==========");
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }

        return map;
    }
}
