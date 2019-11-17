package com;

import com.dao.DLTDao;
import com.dao.SSQDao;
import com.service.IDownLoadService;
import com.util.ConfigUtils;
import com.util.ContextUtils;
import com.util.PageDownLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Random;

@Component
public class StartDLT {

    private IDownLoadService dLTDownLoadImpl;
    @Autowired
    private DLTDao dltDao;

    public DLTDao getDltDao() { return dltDao;}
    public void setDltDao(DLTDao dltDao) { this.dltDao = dltDao;}


    public IDownLoadService getDLTDownLoadImpl() {
        return dLTDownLoadImpl;
    }
    @Autowired
    @Qualifier("sSQDownLoadImpl")
    //需要注入这个bean公用了
    public void setDLTDownLoadImpl(IDownLoadService dltDownLoadImpl) {
        this.dLTDownLoadImpl = dltDownLoadImpl;
    }


    /**
     * 后续优化成批量插入，瞬间完事
     * @param args
     */
    public static void main(String[] args) {

        String dlt19between = ConfigUtils.getProperty("DLT19between");

        /*解析：需要解析的大乐透年份及期数*/
        System.out.println("******************************开始爬取大乐透数据信息：【start】******************************");
        System.out.println("==========从配置文件获取需要解析的大乐透的期数为{"+dlt19between+"}==========");

        String[] split = StringUtils.split(dlt19between, "-");
        if(split!=null && split.length>0){

            //策略取值
            int prefix = Integer.parseInt(split[0].substring(0,2));
            int start = Integer.parseInt(split[0].substring(2));
            int end = Integer.parseInt(split[1].substring(2));

            //获取当前bean
            StartDLT startDLT = ContextUtils.getContext().getBean(StartDLT.class);

            //获取当前bean注入的子类bean
            IDownLoadService dLTDownLoadImpl = startDLT.getDLTDownLoadImpl();
            //获取Dao层代理对象
            DLTDao dltDao = startDLT.getDltDao();

            //保护不被封掉ip地址，线程每次睡一会儿
//            Random randow = new Random();
            for (int i = 0; i <= end-start; i++) {
                //期号循环计算使用periodNum
                String periodNum = "";//期号
                int period = Integer.parseInt(split[0])+i;
                periodNum = ""+period;
                if(prefix<10){//前面补位0
                    periodNum = "0"+period;
                }
                String url="http://kaijiang.500.com/shtml/dlt/"+periodNum+".shtml";
                System.out.println(url);
                try {
                    /**util类爬取页面所有信息*/
                    String pageContent = PageDownLoadUtils.getPageContent(url);
                    //----------------至少睡上6秒以上，不被封掉ip
                    //Thread.sleep(6000L+randow.nextInt(5000));

                    Map<String, String> dltMap = dLTDownLoadImpl.downloadDLT(pageContent, periodNum);

                    //如果没有数据就不要插入了
                    if(dltMap.isEmpty()){
                        System.out.println("==========获取到的当前期号{"+periodNum+"},没有数据，不再插入数据库==========");
                        continue;
                    }
                    dltDao.insertDLTNums(dltMap);
                    System.out.println("==========当期{"+periodNum+"}大乐透数据入库成功==========");

                } catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println("-----------获取当期{"+periodNum+"}大乐透数据出现【异常】，进行下一期数据获取 ----------");
                    continue;
                }
            }

        }

        System.out.println("******************************开始爬取大乐透数据信息：【End】******************************");
    }



}
