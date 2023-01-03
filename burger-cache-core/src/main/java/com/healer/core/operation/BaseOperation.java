package com.healer.core.operation;

import java.util.Map;

public interface BaseOperation {

    int push(String key, Object value);

    int pushAll(Map<String, Object> map);

    int update(String key, Object value);





}
