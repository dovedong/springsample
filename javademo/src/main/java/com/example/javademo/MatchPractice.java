package com.example.javademo;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Dong Deng
 * @version 1.0
 * @time 2019/11/14
 */
public class MatchPractice {
    public static void main(String[] args) {
        match("/device/aaa/upward","/device/+/upward");
    }

    public static void match(String topic,String topicFilter) {
        if (StrUtil.split(topic, '/').size() >= StrUtil.split(topicFilter, '/').size()) {
            List<String> splitTopics = StrUtil.split(topic, '/');//a
            List<String> spliteTopicFilters = StrUtil.split(topicFilter, '/');//#
            String newTopicFilter = "";
            for (int i = 0; i < spliteTopicFilters.size(); i++) {
                String value = spliteTopicFilters.get(i);
                if (value.equals("+")) {
                    newTopicFilter = newTopicFilter + "+/";
                } else if (value.equals("#")) {
                    newTopicFilter = newTopicFilter + "#/";
                    break;
                } else {
                    newTopicFilter = newTopicFilter + splitTopics.get(i) + "/";
                }
            }
            newTopicFilter = StrUtil.removeSuffix(newTopicFilter, "/");
            if (topicFilter.equals(newTopicFilter)) {
                System.out.println("matched:topic is:" + topic + ",topicfilter is :" + topicFilter + ",newTopicFilter is:" + newTopicFilter);
//                Collection<SubscribeStore> collection = map.values();
//                List<SubscribeStore> list2 = new ArrayList<>(collection);
//                subscribeStores.addAll(list2);
            }
        }

    }
}
