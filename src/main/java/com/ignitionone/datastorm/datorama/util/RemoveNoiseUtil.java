package com.ignitionone.datastorm.datorama.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi.peddi on 1/8/2017.
 */
public class RemoveNoiseUtil {

    public List<String> removeNoiceBaseOnExcludeList(List<String> listData, List<String> excludeData) {
        for (String temp : listData) {
            for (String exucludeString : excludeData) {
                if (temp.contains(exucludeString)) {
                    listData.remove(temp);
                }
            }
        }
        return listData;
    }

    public List<String> removeTextUntilString(List<String> listData, String untilExcludeString) {
        boolean flag = false;
        List<String> newList = new ArrayList<String>();
        for (String temp : listData) {
            if (flag == true) {
                newList.add(temp);
            } else if (temp.contains(untilExcludeString)) {
                flag = true;
                newList.add(temp);
            }

        }
        return newList;
    }
}