package com.xts.im.model;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.TencentBean;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ThreadManager;
import com.xts.im.util.LogUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TencentModel extends BaseModel {
    private String mUrl = "https://new.qq.com/ch/games/";
    public void getData(final ResultCallBack<ArrayList<TencentBean>> callBack){
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<TencentBean> list = new ArrayList<>();
                            Document document = Jsoup.connect(mUrl).get();
                            //document.select("div.qq_content.cf.slide-wrap  div.main.fl div#List ")
                            Elements elements = document.select("ul.list li");
                            for (int i = 0; i < elements.size(); i++) {
                                Element element = elements.get(i);
                                Element a = element.select("a[href]").first();
                                if (a != null){
                                    TencentBean tencentBean = new TencentBean();
                                    String url = a.attr("href");
                                    String title = a.text();
                                    tencentBean.title = title;
                                    Elements imgs = a.select("img[src]");
                                    if (imgs != null && imgs.size()>0){
                                        Element first = imgs.first();
                                        String pic = first.attr("src");
                                        tencentBean.pic = pic;
                                    }
                                    tencentBean.url = url;

                                    list.add(tencentBean);
                                }
                            }

                            LogUtil.print(list.toString());
                            if (list.size()>0){
                                callBack.onSuccess(list);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
