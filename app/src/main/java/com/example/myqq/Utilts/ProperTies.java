package com.example.myqq.Utilts;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 97210 on 2018/3/20.
 */

public class ProperTies {

    public static Properties getProperties(Context context){
        Properties urlProps;
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            //注意这地方的参数appConfig在eclipse中应该是appConfig.properties才对,但在studio中不用写后缀
            //InputStream in = context.getAssets().open("appConfig.properties");
            InputStream in = context.getAssets().open("appConfig");
            //方法二：通过class获取setting.properties的FileInputStream
            //InputStream in = PropertiesUtill.class.getResourceAsStream("/assets/  setting.properties "));
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        urlProps = props;
        return urlProps;
    }
}