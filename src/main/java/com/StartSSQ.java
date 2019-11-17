package com;

import com.dao.SSQDao;
import com.entity.SSQ;
import com.service.IDownLoadService;
import com.service.impl.SSQDownLoadImpl;
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
public class StartSSQ {

    private IDownLoadService sSQDownLoadImpl;
    @Autowired
    private SSQDao ssqDao;



    public SSQDao getSsqDao() {
        return ssqDao;
    }
    public void setSsqDao(SSQDao ssqDao) {
        this.ssqDao = ssqDao;
    }

    public IDownLoadService getsSQDownLoadImpl() {
        return sSQDownLoadImpl;
    }
    @Autowired
    @Qualifier("sSQDownLoadImpl")
    public void setsSQDownLoadImpl(IDownLoadService sSQDownLoadImpl) {
        this.sSQDownLoadImpl = sSQDownLoadImpl;
    }


    /**
     * 后续优化成批量插入，瞬间完事
     * @param args
     */
    public static void main(String[] args) {

        String ssq19between = ConfigUtils.getProperty("SSQ19between");

        /*解析：需要解析的双色球年份及期数*/
        System.out.println("******************************开始爬取双色球数据信息：【start】******************************");
        System.out.println("==========从配置文件获取需要解析的双色球的期数为{"+ssq19between+"}==========");

        String[] split = StringUtils.split(ssq19between, "-");
        if(split!=null && split.length>0){

            //策略取值
            int prefix = Integer.parseInt(split[0].substring(0,2));
            int start = Integer.parseInt(split[0].substring(2));
            int end = Integer.parseInt(split[1].substring(2));

            //获取当前bean
            StartSSQ startSSQ = ContextUtils.getContext().getBean(StartSSQ.class);

            //获取当前bean注入的子类bean
            IDownLoadService sSQDownLoadImpl = startSSQ.getsSQDownLoadImpl();
            //获取Dao层代理对象
            SSQDao ssqDao = startSSQ.getSsqDao();

            //保护不被封掉ip地址，线程每次睡一会儿
            Random randow = new Random();
            for (int i = 0; i <= end-start; i++) {
                //期号循环计算使用periodNum
                String periodNum = "";//期号
                int period = Integer.parseInt(split[0])+i;
                periodNum = ""+period;
                if(prefix<10){//前面补位0
                    periodNum = "0"+period;
                }
                String url="http://kaijiang.500.com/shtml/ssq/"+periodNum+".shtml";
                System.out.println(url);
                try {
                    /**util类爬取页面所有信息*/
                    String pageContent = PageDownLoadUtils.getPageContent(url);
                    //----------------至少睡上6秒以上，不被封掉ip
                    //Thread.sleep(6000L+randow.nextInt(5000));

                    Map<String, String> ssqMap = sSQDownLoadImpl.downloadSSQ(pageContent, periodNum);

                    //如果没有数据就不要插入了
                    if(ssqMap.isEmpty()){
                        System.out.println("==========获取到的当前期号{"+periodNum+"},没有数据，不再插入数据库==========");
                        continue;
                    }
                    ssqDao.insertSSQNums(ssqMap);
                    System.out.println("==========当期{"+periodNum+"}双色球数据入库成功==========");

                } catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println("-----------获取当期{"+periodNum+"}双色球数据出现【异常】，进行下一期数据获取 ----------");
                    continue;
                }
            }

        }

        System.out.println("******************************开始爬取双色球数据信息：【End】******************************");
    }



}
