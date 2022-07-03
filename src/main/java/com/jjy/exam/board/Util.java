package com.jjy.exam.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    // 이 함수는 원본리스트를 훼손하지 않고, 새 리스트를 만듭니다. 즉 정렬이 반대인 복사본리스트를 만들어서 반환합니다.
    public static <T> List<T> reverseList(List<T> list) {
        List<T> reverse = new ArrayList<>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            reverse.add(list.get(i));
        }
        return reverse;
    }

    static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> params = new HashMap<>();
        String[] urlBits = url.split("\\?", 2);
        if (urlBits.length == 1) {
            return params;
        }
        String queryStr = urlBits[1];
        for (String bit : queryStr.split("&")) {
            String[] bits = bit.split("=", 2);
            if (bits.length == 1) {
                continue;
            }
            params.put(bits[0], bits[1]);
        }
        return params;
    }

    static String getUrlPathFromUrl(String url) {
        return url.split("\\?", 2)[0];
    }
}