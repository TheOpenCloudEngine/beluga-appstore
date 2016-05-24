package org.opencloudengine.garuda.belugaservice.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 제공하는 리소스의 모음.
 * Created by swsong on 2015. 12. 8..
 */
public class LibertyImageProvided {

    private static final Logger logger = LoggerFactory.getLogger(LibertyImageProvided.class);
    public static List<LibertyImageProvided> libertyImageProvidedList = new ArrayList<>();
    static {
        libertyImageProvidedList.add(new LibertyImageProvided("Ubuntu 14.04 LTS","sppark/liberty:ubuntu14.04"));
        libertyImageProvidedList.add(new LibertyImageProvided("Ubuntu 15.04","sppark/liberty:ubuntu14.04"));
        libertyImageProvidedList.add(new LibertyImageProvided("Cent OS 5.11","sppark/liberty:ubuntu14.04"));
        libertyImageProvidedList.add(new LibertyImageProvided("Cent OS 6.5","sppark/liberty:ubuntu14.04"));
        libertyImageProvidedList.add(new LibertyImageProvided("Cent OS 7.0","sppark/liberty:ubuntu14.04"));
    }

    private String name;
    private String image;

    public LibertyImageProvided(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
